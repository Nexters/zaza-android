package com.nexters.zaza.sample

import androidx.lifecycle.MutableLiveData
import com.nexters.zaza.base.BaseViewModel

class SampleViewModel : BaseViewModel() {

    var nameTag = MutableLiveData<String>()
    var jobTag = MutableLiveData<String>()

    init {
        nameTag.value = "WATING"
    }


}