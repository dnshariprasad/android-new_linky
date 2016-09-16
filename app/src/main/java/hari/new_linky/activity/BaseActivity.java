package hari.new_linky.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Hari Prasad on 9/16/16.
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private ProgressDialog dialog = null;

    public void showProgressDialog(String message) {
        if (dialog == null) dialog = new ProgressDialog(this);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        try {
            if (!dialog.isShowing()) dialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void dismissProgressDialog() {
        dialog.dismiss();
    }
}
