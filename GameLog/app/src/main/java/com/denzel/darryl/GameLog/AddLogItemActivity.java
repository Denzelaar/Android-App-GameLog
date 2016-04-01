package com.denzel.darryl.GameLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddLogItemActivity extends AppCompatActivity {

    private EditText logitemEditText, logitemEditDescription, logitemEditSteamsite;
    private DataSource datasource;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logitemEditText = (EditText) findViewById(R.id.new_item_title);
        logitemEditDescription = (EditText) findViewById(R.id.new_item_description);
        logitemEditSteamsite = (EditText) findViewById(R.id.new_item_steamSite);

        datasource = new DataSource(this);

        addButton = (Button) findViewById(R.id.new_item_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long logitemId = datasource.createLogitem(logitemEditText.getText().toString(), logitemEditDescription.getText().toString(), logitemEditSteamsite.getText().toString());
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_LOGITEM_ID, logitemId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
    }

}
