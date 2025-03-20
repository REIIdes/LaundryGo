package com.example.laundrygo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _orderDetails = MutableLiveData<OrderDetails>()
    val orderDetails: LiveData<OrderDetails> get() = _orderDetails

    fun setOrderDetails(order: OrderDetails) {
        _orderDetails.value = order
    }
}
