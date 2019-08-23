package com.nexters.zaza.sample.firebase.models

import com.google.gson.annotations.Expose

data class Image(
    @Expose
    val button_img: String = "",

    @Expose
    val background_img: String = ""
)