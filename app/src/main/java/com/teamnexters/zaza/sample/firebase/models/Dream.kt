package com.teamnexters.zaza.sample.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Dream(
    var datetime: Long? = 0,
    var during: Double? = 0.0,
    val remark: String? = null
)