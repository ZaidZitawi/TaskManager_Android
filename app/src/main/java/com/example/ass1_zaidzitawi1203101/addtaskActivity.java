package com.example.ass1_zaidzitawi1203101;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


public class addtaskActivity extends AppCompatActivity {

    private TextView title;
    private TextView discription;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        title= findViewById(R.id.title);
        discription=findViewById(R.id.discription);
        btn=findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user-entered data
                String t = title.getText().toString();
                String description = discription.getText().toString();

                // Create a Task object
                Task newTask = new Task(t, description, false);

                // Pass the newTask data back to MainActivity
                Intent intent = new Intent(addtaskActivity.this, MainActivity.class);
                intent.putExtra("newtask", newTask);
                setResult(1, intent);
                finish();
            }
        });


    }
}