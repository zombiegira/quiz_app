package com.example.quizapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class answerActivity extends AppCompatActivity {
    private TextView tvQuestionNumber, tvQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnSubmit;
    int score=0;
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

        tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
        tvQuestion.setText(questionSource.question[currentQuestionIndex]);
        btnOption1.setText(questionSource.choices[currentQuestionIndex][0]);
        btnOption2.setText(questionSource.choices[currentQuestionIndex][1]);
        btnOption3.setText(questionSource.choices[currentQuestionIndex][2]);
        btnOption4.setText(questionSource.choices[currentQuestionIndex][3]);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                if(clickedButton.getId() == R.id.btn_submit) {
                    if(selectedAnswer.equals(questionSource.correctAnswers[currentQuestionIndex])) {
                        score++;
                    }
                    currentQuestionIndex++;
                    loadNewQuestion();
                }
                else {
                    selectedAnswer = clickedButton.getText().toString();
                }
                if(clickedButton.getId() == R.id.btn_option_1) {
                    btnOption1.setBackgroundResource(R.drawable.bgalt);
                    btnOption2.setBackgroundResource(R.drawable.bgnorm);
                    btnOption3.setBackgroundResource(R.drawable.bgnorm);
                    btnOption4.setBackgroundResource(R.drawable.bgnorm);
                }
                else if(clickedButton.getId() == R.id.btn_option_2) {
                    btnOption1.setBackgroundResource(R.drawable.bgnorm);
                    btnOption2.setBackgroundResource(R.drawable.bgalt);
                    btnOption3.setBackgroundResource(R.drawable.bgnorm);
                    btnOption4.setBackgroundResource(R.drawable.bgnorm);
                }
                else if(clickedButton.getId() == R.id.btn_option_3) {
                    btnOption1.setBackgroundResource(R.drawable.bgnorm);
                    btnOption2.setBackgroundResource(R.drawable.bgnorm);
                    btnOption3.setBackgroundResource(R.drawable.bgalt);
                    btnOption4.setBackgroundResource(R.drawable.bgnorm);
                }
                else if(clickedButton.getId() == R.id.btn_option_4) {
                    btnOption1.setBackgroundResource(R.drawable.bgnorm);
                    btnOption2.setBackgroundResource(R.drawable.bgnorm);
                    btnOption3.setBackgroundResource(R.drawable.bgnorm);
                    btnOption4.setBackgroundResource(R.drawable.bgalt);
                }
                //clickedButton.setBackgroundResource(R.color.main);
            }

            void loadNewQuestion() {
                if(currentQuestionIndex + 1 == 5) {
                    tvQuestionNumber.setText(String.valueOf(4));
                }
                else {
                    tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
                }
                btnOption1.setBackgroundResource(R.drawable.bgnorm);
                btnOption2.setBackgroundResource(R.drawable.bgnorm);
                btnOption3.setBackgroundResource(R.drawable.bgnorm);
                btnOption4.setBackgroundResource(R.drawable.bgnorm);
                if(currentQuestionIndex == totalQuestion) {
                    finishQuiz();
                    return;
                }

//            tvQuestion.setText(String.valueOf(questionSource.question[currentQuestionIndex]));
//            btnOption1.setText(String.valueOf(questionSource.choices[currentQuestionIndex][0]));
//            btnOption2.setText(String.valueOf(questionSource.choices[currentQuestionIndex][1]));
//            btnOption3.setText(String.valueOf(questionSource.choices[currentQuestionIndex][2]));
//            btnOption4.setText(String.valueOf(questionSource.choices[currentQuestionIndex][3]));

                tvQuestion.setText(questionSource.question[currentQuestionIndex]);
                btnOption1.setText(questionSource.choices[currentQuestionIndex][0]);
                btnOption2.setText(questionSource.choices[currentQuestionIndex][1]);
                btnOption3.setText(questionSource.choices[currentQuestionIndex][2]);
                btnOption4.setText(questionSource.choices[currentQuestionIndex][3]);
            }

            void finishQuiz() {
                String passStatus = "";
                if(score > totalQuestion * 0.6) {
                    passStatus = "Passed";
                }
                else {
                    passStatus = "Failed";
                }

                new AlertDialog.Builder(answerActivity.this)
                        .setTitle(passStatus)
                        .setMessage("Score is "+ score+" out of "+ totalQuestion)
                        .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                        .setCancelable(false)
                        .show();
            }
            void restartQuiz(){
                btnOption1.setBackgroundResource(R.color.white);
                btnOption2.setBackgroundResource(R.color.white);
                btnOption3.setBackgroundResource(R.color.white);
                btnOption4.setBackgroundResource(R.color.white);
                score = 0;
                currentQuestionIndex =0;
                loadNewQuestion();
            }
        };
        btnOption1.setOnClickListener(onClickListener);
        btnOption2.setOnClickListener(onClickListener);
        btnOption3.setOnClickListener(onClickListener);
        btnOption4.setOnClickListener(onClickListener);
        btnSubmit.setOnClickListener(onClickListener);

    }
}