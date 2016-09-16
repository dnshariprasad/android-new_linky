package hari.new_linky.network;

import hari.new_linky.Constant;
import hari.new_linky.model.SignUpInput;
import hari.new_linky.util.StringConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hari Prasad on 9/16/16.
 */
public class NetworkHandler {
    private static NetworkHandler ourInstance = new NetworkHandler();

    public static NetworkHandler getInstance() {
        return ourInstance;
    }

    private NetworkHandler() {
    }

    private Retrofit linkyRetrofit = new Retrofit.Builder()
            .baseUrl(Constant.url.LINKY)
            .addConverterFactory(StringConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private LinkyApi linkyApi = linkyRetrofit.create(LinkyApi.class);

    public Call<String> signin(String email, String password) {
        return linkyApi.signin(email, password);
    }

    public Call<String> signup(SignUpInput signUpInput) {
        return linkyApi.signup(signUpInput);
    }

}
