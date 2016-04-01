package com.denzel.darryl.GameLog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DetailsActivity extends AppCompatActivity {

    private DataSource datasource;
    private LogItem logItem;
    private EditText editText, editDescription, editSteamsite;
    private Button editButton, updateButton;
    private TextView textViewLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        datasource = new DataSource(this);
        long logitemId = getIntent().getLongExtra(MainActivity.EXTRA_LOGITEM_ID, -1);
        logItem = datasource.getLogitem(logitemId);

        editText = (EditText) findViewById(R.id.update_item_title);
        editText.setText(logItem.getLogitem());
        editText.setEnabled(false);

        editDescription = (EditText) findViewById(R.id.update_item_description);
        editDescription.setText(logItem.getDescription());
        editDescription.setEnabled(false);

        editSteamsite = (EditText) findViewById(R.id.update_item_steamSite);
        editSteamsite.setText(logItem.getSteamSite());
        editSteamsite.setEnabled(false);
        editSteamsite.setVisibility(View.GONE);

        //text for the link
        textViewLink = (TextView) findViewById(R.id.update_item_link);
        textViewLink.setText(logItem.getSteamSite());


        editButton = (Button) findViewById(R.id.edit_button);
        updateButton = (Button) findViewById(R.id.new_item_update);
        updateButton.setVisibility(View.GONE);


        editButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              editButton.setVisibility(View.GONE);
                                              updateButton.setVisibility(View.VISIBLE);
                                              editSteamsite.setVisibility(View.VISIBLE);
                                              textViewLink.setVisibility(View.GONE);

                                              editText.setEnabled(true);
                                              editDescription.setEnabled(true);
                                              editSteamsite.setEnabled(true);
                                          }
                                      }

        );

        updateButton.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                logItem.setLogitem(editText.getText().toString());
                                                logItem.setDescription(editDescription.getText().toString());
                                                logItem.setSteamSite(editSteamsite.getText().toString());

                                                datasource.updateLogitem(logItem);
                                                Toast.makeText(DetailsActivity.this, "LogItem Updated", Toast.LENGTH_SHORT).show();

                                                Intent resultIntent = new Intent();
                                                setResult(Activity.RESULT_OK, resultIntent);
                                                finish();
                                            }
                                        }

        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
