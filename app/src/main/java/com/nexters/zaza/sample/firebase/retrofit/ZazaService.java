package com.nexters.zaza.sample.firebase.retrofit;

import com.nexters.zaza.sample.firebase.models.Image;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZazaService {
    @GET("getImage")
    Call<Image> getImage();
}
