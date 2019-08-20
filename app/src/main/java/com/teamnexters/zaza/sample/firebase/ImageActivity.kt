package com.teamnexters.zaza.sample.firebase

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityDatabaseBinding
import com.teamnexters.zaza.sample.firebase.models.Image
import com.teamnexters.zaza.sample.firebase.retrofit.ZazaService
import kotlinx.android.synthetic.main.activity_image.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import android.graphics.drawable.BitmapDrawable


class ImageActivity : BaseActivity<ActivityDatabaseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_image
    private val imageLoadTarget: Target = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            Log.d("image", "Prepare Load")
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Log.d("image", "Failed")
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            Log.d("image", "Imaged Loaded")
            background_image.background = BitmapDrawable(resources, bitmap)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://us-central1-zaza-249404.cloudfunctions.net/")
            .build()

        val service = retrofit.create(ZazaService::class.java)
        service.image.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                if (response.code() == 200) {
                    val responseMsg = response.body()
                    Log.d("image_test", responseMsg?.background_img)
                    Log.d("image_test", responseMsg?.button_img)
                    Picasso.get().load(responseMsg?.background_img).into(imageLoadTarget)
                    Picasso.get().load(responseMsg?.button_img).into(gradient_image)
                }
            }

            override fun onFailure(call: Call<Image>, t: Throwable) {
                Log.e("image", t.toString())
            }
        })
    }
}
