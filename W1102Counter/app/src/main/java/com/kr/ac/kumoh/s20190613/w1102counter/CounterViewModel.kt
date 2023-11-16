package com.kr.ac.kumoh.s20190613.w1102counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    fun onAdd() {
        _count.value = _count.value?.plus(1)
    }

    fun onSub() {
        if ((_count.value ?: 0) > 0)
            _count.value = _count.value?.minus(1)
    }

}