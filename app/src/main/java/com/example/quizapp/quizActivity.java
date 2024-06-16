package com.example.quizapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private SQLiteDatabase questionPool;

    private int score = 0;
    private int corrects = 0;
    private int wrongs = 0;
    private String completionStr = "";
    private int totalQuestion = 0;
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";

    private static final String DATABASE_NAME = "question_pool";
    private static final String TABLE_NAME = "question";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_QUESTION = "question_statement";
    private static final String COLUMN_OPTION1 = "option_1";
    private static final String COLUMN_OPTION2 = "option_2";
    private static final String COLUMN_OPTION3 = "option_3";
    private static final String COLUMN_OPTION4 = "option_4";
    private static final String COLUMN_ANSWER = "answer";

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

        // Initialize the database
        questionPool = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Fetch the total number of questions from the database
        Cursor countCursor = questionPool.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (countCursor != null && countCursor.moveToFirst()) {
            totalQuestion = countCursor.getInt(0);
            countCursor.close();
        }

        // Load the first question
        loadNewQuestion();

        // Set up onClick listeners
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                if (clickedButton.getId() == R.id.btn_submit) {
                    Cursor cursor = getQuestion(currentQuestionIndex);
                    if (cursor != null && cursor.moveToFirst()) {
                        int answerColumnIndex = cursor.getColumnIndex(COLUMN_ANSWER);
                        if (answerColumnIndex >= 0) {
                            String correctAnswer = cursor.getString(answerColumnIndex);
                            if (selectedAnswer.equals(correctAnswer)) {
                                score++;
                                corrects++;
                            } else {
                                wrongs++;
                            }
                        }
                        cursor.close();
                    }
                    currentQuestionIndex++;
                    loadNewQuestion();
                } else {
                    selectedAnswer = clickedButton.getText().toString();
                }

                resetButtonBackgrounds();
                clickedButton.setBackgroundResource(R.drawable.bgalt);
                btnSubmit.setBackgroundResource(R.drawable.bgnorm);
            }
        };

        btnOption1.setOnClickListener(onClickListener);
        btnOption2.setOnClickListener(onClickListener);
        btnOption3.setOnClickListener(onClickListener);
        btnOption4.setOnClickListener(onClickListener);
        btnSubmit.setOnClickListener(onClickListener);
    }

    private Cursor getQuestion(int index) {
        String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1 OFFSET ?";
        String[] selectionArgs = {String.valueOf(index)};
        return questionPool.rawQuery(query, selectionArgs);
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex >= totalQuestion) {
            finishQuiz();
            return;
        }

        Cursor cursor = getQuestion(currentQuestionIndex);
        if (cursor != null && cursor.moveToFirst()) {
            // Get the question and options from the cursor
            int questionIndex = cursor.getColumnIndex(COLUMN_QUESTION);
            int option1Index = cursor.getColumnIndex(COLUMN_OPTION1);
            int option2Index = cursor.getColumnIndex(COLUMN_OPTION2);
            int option3Index = cursor.getColumnIndex(COLUMN_OPTION3);
            int option4Index = cursor.getColumnIndex(COLUMN_OPTION4);

            if (questionIndex >= 0 && option1Index >= 0 && option2Index >= 0 && option3Index >= 0 && option4Index >= 0) {
                String question = cursor.getString(questionIndex);
                String option1 = cursor.getString(option1Index);
                String option2 = cursor.getString(option2Index);
                String option3 = cursor.getString(option3Index);
                String option4 = cursor.getString(option4Index);

                // Set the text for the question and options
                tvQuestion.setText(question);
                btnOption1.setText(option1);
                btnOption2.setText(option2);
                btnOption3.setText(option3);
                btnOption4.setText(option4);
            }

            cursor.close(); // Close the cursor when done
        }

        tvQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
        completionStr = String.format("Question %d/%d", currentQuestionIndex + 1, totalQuestion);
        tvCompletion.setText(completionStr);
        tvCorrectNum.setText(String.valueOf(corrects));
        tvWrongNum.setText(String.valueOf(wrongs));
        resetButtonBackgrounds();
    }

    private void resetButtonBackgrounds() {
        btnOption1.setBackgroundResource(R.drawable.bgnorm);
        btnOption2.setBackgroundResource(R.drawable.bgnorm);
        btnOption3.setBackgroundResource(R.drawable.bgnorm);
        btnOption4.setBackgroundResource(R.drawable.bgnorm);
        btnSubmit.setBackgroundResource(R.drawable.bgnorm);
    }

    private void finishQuiz() {
        Intent intent = new Intent(quizActivity.this, resultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", totalQuestion);
        startActivity(intent);
        finish();
    }

    private void restartQuiz() {
        btnOption1.setBackgroundResource(R.drawable.bgnorm);
        btnOption2.setBackgroundResource(R.drawable.bgnorm);
        btnOption3.setBackgroundResource(R.drawable.bgnorm);
        btnOption4.setBackgroundResource(R.drawable.bgnorm);
        score = 0;
        corrects = 0;
        wrongs = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();

        Intent intent = new Intent(quizActivity.this, questionSelectUserActivity.class);
        quizActivity.this.startActivity(intent);
    }
}
