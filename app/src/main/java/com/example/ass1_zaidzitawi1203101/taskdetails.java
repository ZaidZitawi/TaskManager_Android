package com.example.ass1_zaidzitawi1203101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class taskdetails extends AppCompatActivity {

    EditText taskTitle;
    EditText taskDiscription;
    CheckBox chk;
    Button btnupdate;
    Task selectedTask;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskdetails);
        taskTitle=findViewById(R.id.tasktitle);
        taskDiscription=findViewById(R.id.taskdiscription);
        chk=findViewById(R.id.chk);
        btnupdate=findViewById(R.id.btnupdate);


        Intent intent = getIntent();
        selectedTask = intent.getParcelableExtra("selectedTask");

        if (selectedTask != null) {
            // Log the selected task details
            Log.d("TaskDetailsActivity", "Selected Task: " + selectedTask.toString());

            taskTitle.setText(selectedTask.getTitle());
            taskDiscription.setText(selectedTask.getDescription());
            chk.setChecked(selectedTask.getStatus());
        }
        prefs = getSharedPreferences(MainActivity.DATA, MODE_PRIVATE);
        editor = prefs.edit();



    }

    public void onUpdateClick(View view) {
        // Update the selected task with new values
        selectedTask.setTitle(taskTitle.getText().toString());
        selectedTask.setDescription(taskDiscription.getText().toString());
        selectedTask.setStatus(chk.isChecked());
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedTask", selectedTask);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    // Method to save the updated task to SharedPreferences
    private void saveTask(Task updatedTask) {
        Gson gson = new Gson();
        String taskString = gson.toJson(updatedTask);
        editor.putString(updatedTask.getTitle(), taskString);
        editor.apply();
    }

}