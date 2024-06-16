package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signinActivity extends AppCompatActivity {
  private EditText tvSigninAccount;
  private EditText tvSigninPassword;
  private Button btnSigninConfirm;
  private TextView tvNoAccount;
  private FirebaseAuth mAuth;
  private static final String ADMIN_ACCOUNT = "admin@email.com";
  private static final String ADMIN_PASSWORD = "123123";

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
    tvSigninAccount = findViewById(R.id.et_signin_account);
    tvSigninPassword = findViewById(R.id.et_signin_password);
    btnSigninConfirm = findViewById(R.id.btn_signin_confirm);
    tvNoAccount = findViewById(R.id.tv_no_account);
    mAuth=FirebaseAuth.getInstance();
    View.OnClickListener listener=new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(view.getId()==R.id.btn_signin_confirm) {
          String email = tvSigninAccount.getText().toString();
          String password = tvSigninPassword.getText().toString();
          signIn(email, password);
        } else if (view.getId() == R.id.tv_no_account) {
            Intent intent = new Intent();
            intent.setClass(signinActivity.this, signupActivity.class);
            signinActivity.this.startActivity(intent);
        }
      }
    };
    btnSigninConfirm.setOnClickListener(listener);
    tvNoAccount.setOnClickListener(listener);
  }
  private void signIn(String email, String password) {
    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  FirebaseUser user = mAuth.getCurrentUser();
                  Intent intent = new Intent();
                  if(email.equals(ADMIN_ACCOUNT) && password.equals(ADMIN_PASSWORD)) {
                    intent.setClass(signinActivity.this, questionSelectAdminActivity.class);
                    signinActivity.this.startActivity(intent);
                  }
                  else {
                    intent.setClass(signinActivity.this, questionSelectUserActivity.class);
                    signinActivity.this.startActivity(intent);
                  }
                } else {
                  // 登入失敗，顯示錯誤訊息
                  Toast.makeText(signinActivity.this, "登入失敗,請檢查帳號與密碼。",
                          Toast.LENGTH_SHORT).show();
                }
              }
            });
  }
}