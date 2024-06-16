package com.example.quizapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_QUESTION = "question_statement";
    private static final String COLUMN_OPTION1 = "option_1";
    private static final String COLUMN_OPTION2 = "option_2";
    private static final String COLUMN_OPTION3 = "option_3";
    private static final String COLUMN_OPTION4 = "option_4";
    private static final String COLUMN_ANSWER = "answer";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_QUESTION + " TEXT, "
            + COLUMN_OPTION1 + " TEXT, "
            + COLUMN_OPTION2 + " TEXT, "
            + COLUMN_OPTION3 + " TEXT, "
            + COLUMN_OPTION4 + " TEXT, "
            + COLUMN_ANSWER + " TEXT)";

    private static final String INSERT_MATHQUESTION_SQL = "INSERT INTO " + TABLE_NAME
            + " (" + COLUMN_QUESTION + ", " + COLUMN_OPTION1 + ", " + COLUMN_OPTION2 + ", "
            + COLUMN_OPTION3 + ", " + COLUMN_OPTION4 + ", " + COLUMN_ANSWER + ") VALUES "
            + "('1+1', '1', '2', '3', '4', '2')";

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
        questionPool.execSQL(INSERT_MATHQUESTION_SQL);

        etQuestionStatement = findViewById(R.id.et_question_statement);
        etOption1 = findViewById(R.id.et_option_1);
        etOption2 = findViewById(R.id.et_option_2);
        etOption3 = findViewById(R.id.et_option_3);
        etOption4 = findViewById(R.id.et_option_4);
        etAnswer = findViewById(R.id.et_answer);
        btnEditSubmit = findViewById(R.id.btn_edit_question_submit);
        lvQuestions = findViewById(R.id.lv_questions);

        btnEditSubmit.setOnClickListener(v -> {
            String questionStatement = etQuestionStatement.getText().toString().trim();
            String option1 = etOption1.getText().toString().trim();
            String option2 = etOption2.getText().toString().trim();
            String option3 = etOption3.getText().toString().trim();
            String option4 = etOption4.getText().toString().trim();
            String answer = etAnswer.getText().toString().trim();

            String insertSQL = "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_QUESTION + ", " + COLUMN_OPTION1 + ", " + COLUMN_OPTION2 + ", "
                    + COLUMN_OPTION3 + ", " + COLUMN_OPTION4 + ", " + COLUMN_ANSWER + ") VALUES (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = questionPool.compileStatement(insertSQL);
            statement.bindString(1, questionStatement);
            statement.bindString(2, option1);
            statement.bindString(3, option2);
            statement.bindString(4, option3);
            statement.bindString(5, option4);
            statement.bindString(6, answer);
            statement.executeInsert();

            listAllQuestions();
        });

        listAllQuestions();
    }

    private void listAllQuestions() {
        Cursor cursor = questionPool.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_QUESTION, COLUMN_OPTION1, COLUMN_OPTION2, COLUMN_OPTION3, COLUMN_OPTION4, COLUMN_ANSWER},
                null, null, null, null, null);

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item_6, // Custom layout
                cursor,
                new String[]{COLUMN_QUESTION, COLUMN_OPTION1, COLUMN_OPTION2, COLUMN_OPTION3, COLUMN_OPTION4, COLUMN_ANSWER},
                new int[]{R.id.et_question_statement, R.id.et_option_1, R.id.et_option_2, R.id.et_option_3, R.id.et_option_4, R.id.et_answer},
                0);

        lvQuestions.setAdapter(simpleCursorAdapter);
    }
}
