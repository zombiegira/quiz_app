package com.example.quizapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class answerActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvQuestionNumber, tvQuestion;
    Button btnOption1, btnOption2, btnOption3, btnOption4, btnSubmit;
    int score = 0;
    int totalQuestion = questionSource.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestion = findViewById(R.id.tv_question);
        btnOption1 = findViewById(R.id.btn_option_1);
        btnOption2 = findViewById(R.id.btn_option_2);
        btnOption3 = findViewById(R.id.btn_option_3);
        btnOption4 = findViewById(R.id.btn_option_4);
        btnSubmit = findViewById(R.id.btn_submit);

        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));

        loadNewQuestion();
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        if (clickedButton.getId() == R.id.btn_submit) {
            if (selectedAnswer != null && selectedAnswer.equals(questionSource.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            selectedAnswer = clickedButton.getText().toString();

            resetButtonBackgrounds();

            clickedButton.setBackgroundResource(R.color.main);  // Change to the desired color when clicked
        }
    }

    private void resetButtonBackgrounds() {
        btnOption1.setBackgroundResource(android.R.drawable.btn_default);
        btnOption2.setBackgroundResource(android.R.drawable.btn_default);
        btnOption3.setBackgroundResource(android.R.drawable.btn_default);
        btnOption4.setBackgroundResource(android.R.drawable.btn_default);
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        resetButtonBackgrounds();

        tvQuestion.setText(questionSource.question[currentQuestionIndex]);
        btnOption1.setText(questionSource.choices[currentQuestionIndex][0]);
        btnOption2.setText(questionSource.choices[currentQuestionIndex][1]);
        btnOption3.setText(questionSource.choices[currentQuestionIndex][2]);
        btnOption4.setText(questionSource.choices[currentQuestionIndex][3]);

        tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
    }

    void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestion * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(answerActivity.this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        resetButtonBackgrounds();
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
