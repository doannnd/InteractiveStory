package com.nguyendinhdoan.interactivestory.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyendinhdoan.interactivestory.R;
import com.nguyendinhdoan.interactivestory.model.Page;
import com.nguyendinhdoan.interactivestory.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;

    private Page page;
    private Story story;
    private Stack<Integer> pageStack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        initViews();
        showName();
        initPage();
    }

    private void initViews() {
        storyImageView = findViewById(R.id.story_image_view);
        storyTextView = findViewById(R.id.story_text_view);
        choice1Button = findViewById(R.id.choice1_button);
        choice2Button = findViewById(R.id.choice2_button);
    }

    private void showName() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.NAME_KEY);
        if (name == null || name.isEmpty()) {
            name = String.valueOf(getString(R.string.default_name));
        }
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    private void initPage() {
        story = new Story();
        pageStack = new Stack<>();
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);
        page = story.getPage(pageNumber);

        storyImageView.setImageResource(page.getImageId());
        storyTextView.setText(getString(page.getTextId()));

        if (page.isFinalPage()) {
            choice1Button.setVisibility(View.GONE);
            choice2Button.setText(getString(R.string.play_again_button));
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPage(0);
                }
            });
        } else {
            loadButtons();
        }

    }

    private void loadButtons() {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(this);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice1_button: {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
                break;
            }
            case R.id.choice2_button: {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            startMainActivity();
        } else {
            loadPage(pageStack.pop());
        }

    }

    private void startMainActivity() {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           onBackPressed();
        }
        return true;
    }
}
