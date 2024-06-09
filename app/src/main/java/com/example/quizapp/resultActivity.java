package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
        result = findViewById(R.id.tv_score);
        correct = findViewById(R.id.tv_correct);
        wrong = findViewById(R.id.tv_wrong);
        home = findViewById(R.id.btn_home);
        restart = findViewById(R.id.btn_restart);

        // Get the intent and retrieve the data
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        int correctAnswers = score;
        int wrongAnswers = totalQuestions - score;

        // Update the TextViews
        result.setText("Your Score: " + score + "/" + totalQuestions);
        correct.setText("Correct: " + correctAnswers);
        wrong.setText("Wrong: " + wrongAnswers);

        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(resultActivity.this, question.class);
            startActivity(homeIntent);
            finish();
        });
        restart.setOnClickListener(v -> {
            Intent restartIntent = new Intent(resultActivity.this, answerActivity.class);
            startActivity(restartIntent);
            finish();
        });
    }
}