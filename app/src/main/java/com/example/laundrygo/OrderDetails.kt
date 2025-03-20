package com.example.laundrygo

data class OrderDetails(
    val orderId: String,
    val fullName: String,
    val contactNumber: String,
    val address: String,
    val deliveryDate: String,
    val deliveryTime: String,
    val paymentMethod: String
)
