package com.example.clicker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Calendar calendar;
    private String date;
    private String time;

    private Button Add;
    private Button Reset;
    private Button Clear;
    private ListView CountListView;
    private Integer CountInt;
    private TextView text;
    private EditText StopName;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Add = findViewById(R.id.AddBtn);
        Reset = findViewById(R.id.ResetBtn);
        Clear = findViewById(R.id.ClearBtn);
        StopName = findViewById(R.id.StopNameInput);
        CountListView = findViewById(R.id.CountList);
        text = findViewById(R.id.textView);

        CountInt = 0;

        items = SaveList.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        CountListView.setAdapter(adapter);

        /* final MediaPlayer ClickSound = MediaPlayer.create(this, R.raw.click); */

        Add.setOnClickListener(this);
        Clear.setOnClickListener(this);
        Reset.setOnClickListener(this);
        CountListView.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        CountInt = CountInt +1;
        calendar = Calendar.getInstance();
        time = DateFormat.getTimeInstance().format(calendar.getTime());
        date = DateFormat.getDateInstance().format((calendar.getTime()));
        switch(v.getId()) {
            case R.id.AddBtn:
                String Input = StopName.getText().toString();
                adapter.add((String.valueOf(CountInt)) +", " + Input + ",    " +  time + ",    " + date + "    ");
                text.setText(String.valueOf(CountInt));
                SaveList.writeData(items, this);
                break;
            case R.id.ResetBtn:
                StopName.setText("");
                CountInt = 0;
                text.setText(String.valueOf(CountInt));
                Toast.makeText(this, "Count Reset, Please enter a new Stop Name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ClearBtn:
                StopName.setText("");
                CountInt = 0;
                text.setText(String.valueOf(CountInt));
                adapter.clear();
                Toast.makeText(this, "List Cleared", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        CountInt = CountInt -1;
        if(CountInt < 0 ){
            CountInt = 0;
        }

        text.setText(String.valueOf(CountInt));
        adapter.notifyDataSetChanged();
        SaveList.writeData(items, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);




    }
}
