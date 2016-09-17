package hari.new_linky.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import hari.new_linky.R;
import hari.new_linky.adapter.LinksAdapter;
import hari.new_linky.model.Link;
import hari.new_linky.network.NetworkHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private RecyclerView rv_links;
    private LinksAdapter linksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_links = (RecyclerView) findViewById(R.id.rv_links);


        showProgressDialog("Please wait...");
        NetworkHandler.getInstance().links().enqueue(new Callback<List<Link>>() {
            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.raw());
                    buildList(response.body());
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t) {
                dismissProgressDialog();
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    private void buildList(List<Link> links) {
        if (rv_links.getAdapter() == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_links.setLayoutManager(linearLayoutManager);
            linksAdapter = new LinksAdapter(this, links);
            rv_links.setAdapter(linksAdapter);
        } else {
            linksAdapter.notifyData(links);
        }
    }

}
