package com.example.quizapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class editQuestionActivity extends AppCompatActivity {
    private SQLiteDatabase questionPool;
    private static final String DATABASE_NAME = "question_pool";
    private static final String TABLE_NAME = "question";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY, question_statement TEXT, option_1 TEXT, option_2 TEXT, option_3 TEXT, option_4 TEXT, answer TEXT)";
    private EditText etQuestionStatement;
    private EditText etOption1;
    private EditText etOption2;
    private EditText etOption3;
    private EditText etOption4;
    private EditText etAnswer;
    private Button btnEditSubmit;
    private ListView lvQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        questionPool = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        questionPool.execSQL(CREATE_TABLE_SQL);

        etQuestionStatement = findViewById(R.id.et_question_statement);
        etOption1 = findViewById(R.id.et_option_1);
        etOption2 = findViewById(R.id.et_option_2);
        etOption3 = findViewById(R.id.et_option_3);
        etOption4 = findViewById(R.id.et_option_4);
        etAnswer = findViewById(R.id.et_answer);
        btnEditSubmit = findViewById(R.id.btn_edit_question_submit);
        lvQuestions = findViewById(R.id.lv_questions);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_edit_question_submit) {
                    String questionStatement = etQuestionStatement.getText().toString();
                    String option1 = etOption1.getText().toString();
                    String option2 = etOption2.getText().toString();
                    String option3 = etOption3.getText().toString();
                    String option4 = etOption4.getText().toString();
                    String answer = etAnswer.getText().toString();
                    String insertSQL = "INSERT INTO " + TABLE_NAME + "(question_statement, option_1, option_2, option_3, option_4, answer) VALUES ('" + questionStatement + "'," + option1 + "'," + option2 + "'," + option3 + "'," + option4 + "'," + answer + " )";
                    questionPool.execSQL(insertSQL);
                }
                listAllQuestions();
            }
        };
        btnEditSubmit.setOnClickListener(onClickListener);
    }

    private void listAllQuestions() {
        Cursor cursor = questionPool.query(TABLE_NAME, new String[] {"_id", "question_statement", "option_1", "option_2", "option_3", "option_4", "answer"}, null, null, null, null, null, null);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(editQuestionActivity.this,
                R.layout.list_item_6,
                cursor,
                new String[]{"question_statement", "option_1", "option_2", "option_3", "option_4", "answer"},
                new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6},
                0);
        lvQuestions.setAdapter(simpleCursorAdapter);
    }
}