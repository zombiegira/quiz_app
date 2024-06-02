package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signin extends AppCompatActivity {
  private EditText signin_user;
  private EditText signin_pass;
  private Button btn_login;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_signin);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    signin_user = findViewById(R.id.signin_user);
    signin_pass = findViewById(R.id.signin_pass);
    btn_login = findViewById(R.id.login_btn);
    mAuth=FirebaseAuth.getInstance();
    View.OnClickListener listener=new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(view.getId()==R.id.login_btn) {
          String email = signin_user.getText().toString();
          String password = signin_pass.getText().toString();
          signIn(email, password);
        }

      }
    };

    btn_login.setOnClickListener(listener);
  }
  private void signIn(String email, String password) {
    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  // 登入成功，更新 UI
                  FirebaseUser user = mAuth.getCurrentUser();
                  Intent intent = new Intent();
                  intent.setClass(signin.this, question.class);
                  signin.this.startActivity(intent);
                } else {
                  // 登入失敗，顯示錯誤訊息
                  Toast.makeText(signin.this, "登入失敗,請檢察帳號與密碼.",
                          Toast.LENGTH_SHORT).show();
                }
              }
            });
  }
}