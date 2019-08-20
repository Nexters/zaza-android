package com.teamnexters.zaza.sample.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Dream(
    var datetime: Long? = 0,
    var during: Double? = 0.0,
    var background_image: String? = "",
    var button_image: String? = "",
    val remark: String? = null
)