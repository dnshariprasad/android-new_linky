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
import hari.new_linky.model.SignUpInput;
import hari.new_linky.model.User;
import hari.new_linky.network.NetworkHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";
    private EditText etFirstName, etLastName, etEmail, etMobile, etPassword, etConfirmPassword;
    private Button btnSignup;
    private TextView tvToSignIn;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignupActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(this);
        tvToSignIn = (TextView) findViewById(R.id.tv_to_sign_in);
        tvToSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_sign_in:
                SigninActivity.start(this);
                finish();
                break;
            case R.id.btn_signup:
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String mobile = etMobile.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (firstName.length() == 0) {
                    etFirstName.setError("Should not be empty.");
                    return;
                }

                if (lastName.length() == 0) {
                    etLastName.setError("Should not be empty.");
                    return;
                }

                if (email.length() == 0) {
                    etEmail.setText("Should not be empty.");
                    return;
                }
                if (mobile.length() == 0) {
                    etMobile.setError("Should not be empty.");
                    return;
                }
                if (password.length() == 0) {
                    etPassword.setError("Should not be empty.");
                    return;
                }
                if (confirmPassword.length() == 0) {
                    etConfirmPassword.setError("Should not be empty.");
                    return;
                }

                if (!confirmPassword.equals(password)) {
                    etConfirmPassword.setError("Passwords doesn't match.");
                    return;
                }
                NetworkHandler.getInstance().signup(new SignUpInput(new User(firstName, lastName, email, mobile, password))).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.raw());
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                if (jsonObject.optInt("status") == 400) {
                                    Toast.makeText(SignupActivity.this, jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
                                } else {
                                    MainActivity.start(SignupActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(TAG, "onResponse: " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });


                break;
        }
    }
}
