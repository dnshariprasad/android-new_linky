package hari.new_linky.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hari.new_linky.R;
import hari.new_linky.network.NetworkHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btnSignin;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.length() == 0) {
            etEmail.setText("Should not be empty.");
            return;
        }
        if (password.length() == 0) {
            etPassword.setText("Should not be empty.");
            return;
        }

        switch (view.getId()) {
            case R.id.btn_signin:
                NetworkHandler.getInstance().signin(email, password).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.raw());
                            MainActivity.start(SigninActivity.this);
                        } else {
                            Log.e(TAG, "onResponse: " + response.raw());
                            Toast.makeText(SigninActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                        Toast.makeText(SigninActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
