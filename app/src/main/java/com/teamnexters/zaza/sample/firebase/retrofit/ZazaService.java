package com.teamnexters.zaza.sample.firebase.retrofit;

import com.teamnexters.zaza.sample.firebase.models.Image;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZazaService {
    @GET("getImage")
    Call<Image> getImage();
}
