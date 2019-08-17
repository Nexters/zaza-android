package com.teamnexters.zaza.sample.firebase

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityDatabaseBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.teamnexters.zaza.sample.firebase.models.Image
import com.teamnexters.zaza.sample.firebase.retrofit.ZazaService
import kotlinx.android.synthetic.main.activity_image.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class ImageActivity : BaseActivity<ActivityDatabaseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://us-central1-zaza-249404.cloudfunctions.net/")
            .build()

        val service = retrofit.create(ZazaService::class.java!!)
        val call = service.image.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                if(response.code() == 200){
                    val response_msg = response.body()
                    Log.d("image_test", response_msg?.background_img)
                    Log.d("image_test", response_msg?.button_img)
                    Picasso.get().load(response_msg?.background_img).into(gradient_image1)
                    Picasso.get().load(response_msg?.button_img).into(gradient_image2)
                }
            }

            override fun onFailure(call: Call<Image>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
