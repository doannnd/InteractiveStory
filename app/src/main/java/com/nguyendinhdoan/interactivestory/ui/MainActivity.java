package com.nguyendinhdoan.interactivestory.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nguyendinhdoan.interactivestory.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NAME_KEY = "NAME_KEY";

    private EditText nameEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        addEvents();
    }

    private void initViews() {
        nameEditText = findViewById(R.id.name_edit_text);
        startButton = findViewById(R.id.start_button);
    }

    private void addEvents() {
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            String name = nameEditText.getText().toString();
            startStoryActivity(name);
        }
    }

    private void startStoryActivity(String name) {
        Intent intentStory = new Intent(this, StoryActivity.class);
        intentStory.putExtra(NAME_KEY, name);
        startActivity(intentStory);
        finish();
    }
}
