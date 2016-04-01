package com.denzel.darryl.GameLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LOGITEM_ID = "extraLogitemId";
    private ListView listView;
    private Adapter adapter;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.main_list);

        TextView emptyView = (TextView) findViewById(R.id.main_list_empty);
        listView.setEmptyView(emptyView);

        datasource = new DataSource(this);
        List<LogItem> logItems = datasource.getAllLogitem();
        adapter = new Adapter(this, R.layout.row_detail, logItems);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_LOGITEM_ID, adapter.getItem(position).getId());
                startActivityForResult(intent, 2);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddLogItemActivity.class), 1);
            }
        });

        LogItem ass = new LogItem();
        System.out.println(ass.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //intent to go to about page
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void updateLogitemListView() {
        List<LogItem> logItems = datasource.getAllLogitem();
        adapter = new Adapter(this, R.layout.row_detail, logItems);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            long logitemId = data.getLongExtra(EXTRA_LOGITEM_ID, -1);
            if (logitemId != -1) {
                LogItem logItem = datasource.getLogitem(logitemId);
                adapter.add(logItem);
                updateLogitemListView();
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateLogitemListView();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            Toast.makeText(getApplicationContext(), "LogItem deleted", Toast.LENGTH_LONG).show();
            LogItem logItem = adapter.getItem(info.position);
            adapter.remove(logItem);
            datasource.deleteLogitem(logItem);

            updateLogitemListView();
        } else {
            return false;
        }
        return true;
    }

}
