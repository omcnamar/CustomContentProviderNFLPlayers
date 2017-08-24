package com.olegsagenadatrytwo.customcontentprovidernflplayers;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by omcna on 8/23/2017.
 */

public class NFLPlayerProvider extends ContentProvider {
    
    private static final String TAG = "NFLPlayerProvider";
    
    static final String PROVIDER_NAME = "com.olegsagenadatrytwo.customcontentprovidernflplayers.NFLPlayerProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/players";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final String AGE = "age";
    static final String TEAM = "team";
    static final String POSITION = "position";
    static final String RATING = "rating";

    private static HashMap<String, String> PLAYERS_PROJECTION_MAP;

    static final int PLAYERS = 1;
    static final int PLAYER_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "players", PLAYERS);
        uriMatcher.addURI(PROVIDER_NAME, "players/#", PLAYER_ID);
    }

    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "NflPlayers";
    static final String PLAYER_TABLE_NAME = "players";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + PLAYER_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL, " +
                    AGE  + " TEXT NOT NULL, " +
                    TEAM + " TEXT NOT NULL, " +
                    POSITION + " TEXT NOT NULL, " +
                    RATING   + " TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "onCreate: " + " database");
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  PLAYER_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        Log.d(TAG, "onCreate: ");
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: ");
        /**
         * Add a new NFL Player record
         */
        long rowID = db.insert(	PLAYER_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: ");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PLAYER_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case PLAYERS:
                qb.setProjectionMap(PLAYERS_PROJECTION_MAP);
                break;

            case PLAYER_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on players names
             */
            sortOrder = NAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case PLAYERS:
                count = db.delete(PLAYER_TABLE_NAME, selection, selectionArgs);
                break;

            case PLAYER_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( PLAYER_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PLAYERS:
                count = db.update(PLAYER_TABLE_NAME, values, selection, selectionArgs);
                break;

            case PLAYER_ID:
                count = db.update(PLAYER_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: ");
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case PLAYERS:
                return "vnd.android.cursor.dir/vnd.olegsagenadatrytwo.players";
            /**
             * Get a particular student
             */
            case PLAYER_ID:
                return "vnd.android.cursor.item/vnd.olegsagenadatrytwo.players";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}