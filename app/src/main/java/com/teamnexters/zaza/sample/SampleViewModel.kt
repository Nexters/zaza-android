package com.teamnexters.zaza.sample

import androidx.lifecycle.MutableLiveData
import com.teamnexters.zaza.base.BaseViewModel

class SampleViewModel : BaseViewModel() {
    val name: String = String()
    val job: String = String()

    val nameTag: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    lateinit var jobTag: MutableLiveData<String>

    init {
        nameTag.value = "WATING"
    }

    fun getName(): MutableLiveData<String> {
        if (nameTag == null) {
//            nameTag = MutableLiveData()
        }
        return nameTag
    }
}