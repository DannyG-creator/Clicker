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

    private Button button;
    private Button button2;
    private Button button3;
    private ListView list;
    private Integer count;
    private TextView text;
    private EditText TextInput;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        TextInput = findViewById(R.id.textInput);
        list = findViewById(R.id.list);
        text = findViewById(R.id.textView);

        count = 0;

        items = SaveList.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        /* final MediaPlayer ClickSound = MediaPlayer.create(this, R.raw.click); */

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        count = count +1;
        calendar = Calendar.getInstance();
        time = DateFormat.getTimeInstance().format(calendar.getTime());
        date = DateFormat.getDateInstance().format((calendar.getTime()));
        switch(v.getId()) {
            case R.id.button:
                String Input = TextInput.getText().toString();
                adapter.add((String.valueOf(count)) +", " + Input + ",    " +  time + ",    " + date + "    ");
                text.setText(String.valueOf(count));
                SaveList.writeData(items, this);
                break;
            case R.id.button2:
                TextInput.setText("");
                count = 0;
                text.setText(String.valueOf(count));
                Toast.makeText(this, "Count Reset, Please enter a new Stop Name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                TextInput.setText("");
                count = 0;
                text.setText(String.valueOf(count));
                adapter.clear();
                Toast.makeText(this, "List Cleared", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        count = count -1;
        if(count < 0 ){
            count = 0;
        }

        text.setText(String.valueOf(count));
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
