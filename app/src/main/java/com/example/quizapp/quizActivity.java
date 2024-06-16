package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class quizActivity extends AppCompatActivity {
    private TextView tvQuestionNumber, tvQuestion;
    private TextView tvCorrectNum, tvWrongNum, tvCompletion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnSubmit;
    int score = 0;
    int corrects;
    int wrongs;
    String completionStr = "";
    int totalQuestion = questionSource.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvCorrectNum = findViewById(R.id.tv_correct_num);
        tvWrongNum = findViewById(R.id.tv_wrong_num);
        tvCompletion = findViewById(R.id.tv_completion);
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

        corrects = score;

        completionStr = String.format("Question %d/%d", currentQuestionIndex + 1, totalQuestion);
        tvCompletion.setText(completionStr);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                if(clickedButton.getId() == R.id.btn_submit) {
                    if(selectedAnswer.equals(questionSource.correctAnswers[currentQuestionIndex])) {
                        score++;
                        corrects++;
                    }
                    else {
                        wrongs++;
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

            }

            void loadNewQuestion() {

                if(currentQuestionIndex + 1 == 5) {
                    tvQuestionNumber.setText(String.valueOf(4));
                    completionStr = String.format("Question %d/%d", 4, totalQuestion);
                }
                else {
                    tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
                    completionStr = String.format("Question %d/%d", currentQuestionIndex + 1, totalQuestion);
                }
                tvCorrectNum.setText(String.valueOf(corrects));
                tvWrongNum.setText(String.valueOf(wrongs));
                tvCompletion.setText(completionStr);
                btnOption1.setBackgroundResource(R.drawable.bgnorm);
                btnOption2.setBackgroundResource(R.drawable.bgnorm);
                btnOption3.setBackgroundResource(R.drawable.bgnorm);
                btnOption4.setBackgroundResource(R.drawable.bgnorm);
                if(currentQuestionIndex == totalQuestion) {
                    finishQuiz();
                    return;
                }

                tvQuestion.setText(questionSource.question[currentQuestionIndex]);
                btnOption1.setText(questionSource.choices[currentQuestionIndex][0]);
                btnOption2.setText(questionSource.choices[currentQuestionIndex][1]);
                btnOption3.setText(questionSource.choices[currentQuestionIndex][2]);
                btnOption4.setText(questionSource.choices[currentQuestionIndex][3]);
            }


            void finishQuiz() {
                Intent intent = new Intent(quizActivity.this, resultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("totalQuestions", totalQuestion);
                startActivity(intent);
                finish();
            }
            void restartQuiz(){
                btnOption1.setBackgroundResource(R.color.white);
                btnOption2.setBackgroundResource(R.color.white);
                btnOption3.setBackgroundResource(R.color.white);
                btnOption4.setBackgroundResource(R.color.white);
                score = 0;
                corrects = 0;
                wrongs = 0;
                currentQuestionIndex =0;
                loadNewQuestion();

                Intent intent = new Intent();
                intent.setClass(quizActivity.this, questionSelectUserActivity.class);
                quizActivity.this.startActivity(intent);

            }
        };
        btnOption1.setOnClickListener(onClickListener);
        btnOption2.setOnClickListener(onClickListener);
        btnOption3.setOnClickListener(onClickListener);
        btnOption4.setOnClickListener(onClickListener);
        btnSubmit.setOnClickListener(onClickListener);

    }
}