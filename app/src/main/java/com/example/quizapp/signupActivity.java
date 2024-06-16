package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signupActivity extends AppCompatActivity {

  private EditText etEmail;
  private EditText etPassword;
  private Button btnSignUp;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_signup);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    mAuth = FirebaseAuth.getInstance();
    etEmail = findViewById(R.id.et_signin_account);
    etPassword = findViewById(R.id.et_signin_password);
    btnSignUp = findViewById(R.id.signup);

    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        signUp(email, password);
      }
    };
    btnSignUp.setOnClickListener(listener);
  }

  private void signUp(String email, String password) {
    Task task = mAuth.createUserWithEmailAndPassword(email, password);
    OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          // 註冊成功，更新 UI
          FirebaseUser user = mAuth.getCurrentUser();
          Toast.makeText(signupActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();;
          signupActivity.this.finish();
        } else {
          // 註冊失敗，顯示錯誤訊息
          Toast.makeText(signupActivity.this, "註冊失敗",
            Toast.LENGTH_SHORT).show();
        }
      }
    };
    task.addOnCompleteListener(listener);
  }
}