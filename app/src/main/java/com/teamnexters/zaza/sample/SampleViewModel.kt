package com.teamnexters.zaza.sample

import com.teamnexters.zaza.base.BaseViewModel

class SampleViewModel(sample: Sample) : BaseViewModel() {
    val nameTag = sample.name + "님"
    val jobTag = "(" + sample.job + ")"
}