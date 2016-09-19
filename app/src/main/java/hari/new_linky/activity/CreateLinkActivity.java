package hari.new_linky.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hari.new_linky.Constant;
import hari.new_linky.R;
import hari.new_linky.model.CreateLinkInput;
import hari.new_linky.model.Link;
import hari.new_linky.network.NetworkHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateLinkActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CreateLinkActivity";
    private EditText etTitle, etTargetInfo, etDescription, etTag;
    private Button btnCreate;
    private ImageView ivClose;
    private TextView tv_create_link;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateLinkActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_link);

        etTitle = (EditText) findViewById(R.id.et_title);
        etTargetInfo = (EditText) findViewById(R.id.et_targetInfo);
        etDescription = (EditText) findViewById(R.id.et_description);
        etTag = (EditText) findViewById(R.id.et_tag);
        tv_create_link= (TextView) findViewById(R.id.tv_create_link);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);

        btnCreate = (Button) findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                start(this);
                finish();
                break;
            case R.id.btn_create:
                String title = etTitle.getText().toString();
                String targetInfo = etTargetInfo.getText().toString();
                String description = etDescription.getText().toString();
                String tag = etTag.getText().toString();
                if (title.length() == 0) {
                    etTitle.setError(Constant.SHOW_ERROR);
                    return;
                }
                if (targetInfo.length() == 0) {
                    etTargetInfo.setError(Constant.SHOW_ERROR);
                    return;
                }
                if (description.length() == 0) {
                    etTitle.setError(Constant.SHOW_ERROR);
                    return;
                }
                if (tag.length() == 0) {
                    etTitle.setError(Constant.SHOW_ERROR);
                    return;
                }
                showProgressDialog(Constant.PLEASE_WAIT);
                NetworkHandler.getInstance().createLink(new CreateLinkInput(new Link(title, "text", description, targetInfo, tag))).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dismissProgressDialog();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.raw());
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                if (jsonObject.optInt("status") == 400) {
                                    Toast.makeText(CreateLinkActivity.this, jsonObject.optString("error"), Toast.LENGTH_SHORT).show();
                                } else {
                                    MainActivity.start(CreateLinkActivity.this);
                                    finish();
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
                        dismissProgressDialog();
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
                break;
        }
    }


}
