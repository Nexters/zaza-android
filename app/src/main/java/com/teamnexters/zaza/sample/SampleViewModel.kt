package com.teamnexters.zaza.sample

import androidx.lifecycle.ViewModel

class SampleViewModel(sample: Sample) : ViewModel() {
    val nameTag = sample.name + "님"
    val jobTag = "(" + sample.job + ")"
}