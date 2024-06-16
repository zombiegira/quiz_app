package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class questionSelectUserActivity extends AppCompatActivity {
    private ImageButton ibMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_select_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibMath = findViewById(R.id.ib_math_user);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton clickedButton = (ImageButton) v;
                if(clickedButton.getId() == R.id.ib_math_user) {
                    Intent intent = new Intent();
                    intent.setClass(questionSelectUserActivity.this, quizActivity.class);
                    questionSelectUserActivity.this.startActivity(intent);

                }
            }
        };
        ibMath.setOnClickListener(onClickListener);
    }
}