package com.example.ass1_zaidzitawi1203101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    ListView listView;
    ArrayList<Task> task;
    ArrayAdapter<Task> adapter;
    Button btnadd;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listv);
        btnadd = findViewById(R.id.btnadd);
        setupSharedPrefs();

        // Load tasks from SharedPreferences nad view them to the listview
        task = loadTasks();

        // If tasks are null, initialize the ArrayList
        if (task == null) {
            task = new ArrayList<>();
        }
//        task.add(new Task("Task1", "Gy", false));
//        task.add(new Task("Task2", "Zoo Meeting", true));
//        task.add(new Task("Task3", "Breakfast", false));
//        task.add(new Task("Task 4", "Meet Friends", true));
//        task.add(new Task("Task 5", "Studying", false));

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, task);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected task
                selectedTask = task.get(position);
                // Start the TaskDetails activity and pass the selected task as an extra
                Intent intent = new Intent(MainActivity.this, taskdetails.class);
                intent.putExtra("selectedTask", selectedTask);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            Task object = data.getParcelableExtra("newtask");
            task.add(object);
            adapter.notifyDataSetChanged();

            // Save the updated task list to SharedPreferences
            saveTasks(task);
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private ArrayList<Task> loadTasks() {//بدنا نعمل check pref هون لبكرا
        Gson gson = new Gson();
        String taskString = prefs.getString(DATA, null);
        Type type = new TypeToken<ArrayList<Task>>(){}.getType();
        return gson.fromJson(taskString, type);
    }

    private void saveTasks(ArrayList<Task> tasks) {
        Gson gson = new Gson();
        String taskString = gson.toJson(tasks);
        editor.putString(DATA, taskString);
        editor.apply();
    }

    public void onClickSend(View view) {
        Intent intent = new Intent(this, addtaskActivity.class);
        startActivityForResult(intent, 1);
    }
}