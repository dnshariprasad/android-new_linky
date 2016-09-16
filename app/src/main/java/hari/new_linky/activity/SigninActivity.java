package hari.new_linky.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hari.new_linky.R;
import hari.new_linky.network.NetworkHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btnSignin;
    private EditText etEmail, etPassword;
    private TextView tvToSignUp;

    public static void start(Context context) {
        Intent starter = new Intent(context, SigninActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(this);

        tvToSignUp = (TextView) findViewById(R.id.tv_to_sign_up);
        tvToSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_sign_up:
                SignupActivity.start(this);
                finish();
                break;
            case R.id.btn_signin:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.length() == 0) {
                    etEmail.setError("Should not be empty.");
                    return;
                }
                if (password.length() == 0) {
                    etPassword.setError("Should not be empty.");
                    return;
                }
                NetworkHandler.getInstance().signin(email, password).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.raw());
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                if (jsonObject.optInt("status") == 200) {
                                    MainActivity.start(SigninActivity.this);
                                    finish();
                                } else {
                                    Toast.makeText(SigninActivity.this, jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
