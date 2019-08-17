package com.teamnexters.zaza.sample.firebase.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("button_img")
    @Expose
    val button_img: String = "",
    @SerializedName("background_img")
    @Expose
    val background_img: String = ""
)