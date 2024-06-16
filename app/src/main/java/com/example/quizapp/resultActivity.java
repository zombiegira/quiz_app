package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class resultActivity extends AppCompatActivity {
    private TextView result;
    private TextView correct;
    private TextView wrong;
    private TextView total;
    private TextView complete;
    private ImageButton home;
    private ImageButton restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        result = findViewById(R.id.tv_score);
        correct = findViewById(R.id.tv_correct);
        wrong = findViewById(R.id.tv_wrong);
        total = findViewById(R.id.tv_total);
        complete = findViewById(R.id.tv_complete);
        home = findViewById(R.id.btn_home);
        restart = findViewById(R.id.btn_restart);

        // Get the intent and retrieve the data
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        int correctAnswers = score;
        int wrongAnswers = totalQuestions - score;

        // Update the TextViews
        double percentage = ((double) score / totalQuestions) * 100;
        result.setText(String.format("%.0f", percentage));
       // correct.setText("Correct: " + correctAnswers);
        correct.setText(String.valueOf(correctAnswers));
        //wrong.setText("Wrong: " + wrongAnswers);
        wrong.setText(String.valueOf(wrongAnswers));
        //complete.setText("100%\nCompletion");
        complete.setText("100%");
        //total.setText(String.format("%d\nTotal", totalQuestions));
        total.setText(String.format("%d", totalQuestions));

        // Set colors for correct and wrong answers
        correct.setTextColor(getResources().getColor(android.R.color.holo_green_dark)); // Green color
        wrong.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Red color
        complete.setTextColor(getResources().getColor(android.R.color.holo_purple)); // Purple color
        total.setTextColor(getResources().getColor(android.R.color.holo_purple)); // Purple color

        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(resultActivity.this, questionSelectUserActivity.class);
            startActivity(homeIntent);
            finish();
        });

        restart.setOnClickListener(v -> {
            Intent restartIntent = new Intent(resultActivity.this, quizActivity.class);
            startActivity(restartIntent);
            finish();
        });
    }

}