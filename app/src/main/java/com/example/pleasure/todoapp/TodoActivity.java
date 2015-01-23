package com.example.pleasure.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TodoActivity extends ActionBarActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        lvItems=(ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void readItems(){
        // IO based on Apache commons-io-2.4 library
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupListViewListener() {
        // Remove an item on long click
        // kind like a callback function
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                               @Override
                                               public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                                   items.remove(pos);
                                                   itemsAdapter.notifyDataSetChanged();
                                                   writeItems();
                                                   return true;
                                               }
                                           }
        );
    }

    public void onAddedItem(View v) {
        // Add the item to the array, then clear the text field
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
