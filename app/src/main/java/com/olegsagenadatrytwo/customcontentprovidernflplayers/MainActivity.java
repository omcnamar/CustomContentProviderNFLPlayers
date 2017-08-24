package com.olegsagenadatrytwo.customcontentprovidernflplayers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAge;
    private EditText etTeam;
    private EditText etPosition;
    private EditText etRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText)findViewById(R.id.etName);
        etAge = (EditText)findViewById(R.id.etAge);
        etTeam = (EditText)findViewById(R.id.etTeam);
        etPosition = (EditText)findViewById(R.id.etPosition);
        etRating = (EditText)findViewById(R.id.etRating);
    }

    public void submitPlayer(View view) {

        // Add a new student record
        ContentValues values = new ContentValues();
        values.put(NFLPlayerProvider.NAME, etName.getText().toString());
        values.put(NFLPlayerProvider.AGE, etAge.getText().toString());
        values.put(NFLPlayerProvider.TEAM, etTeam.getText().toString());
        values.put(NFLPlayerProvider.POSITION, etPosition.getText().toString());
        values.put(NFLPlayerProvider.RATING, etRating.getText().toString());

        Uri uri = getContentResolver().insert(
                NFLPlayerProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();

    }

    public void goToAllPlayers(View view) {
        Intent intent = new Intent(this, AllPlayersActivity.class);
        startActivity(intent);
    }
}
