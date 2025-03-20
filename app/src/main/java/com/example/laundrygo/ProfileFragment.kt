package com.example.laundrygo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private var orders: List<OrderDetails> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile2, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve order details from SharedPreferences
        val orderDetails = getLatestOrderDetails()

        if (orderDetails != "No order found") {
            // Split the order details by newline and create an OrderDetails object
            val order = parseOrderDetails(orderDetails)
            orders = listOf(order) // Wrap the order in a list
        }

        // Set up the adapter
        orderAdapter = OrderAdapter(orders)
        recyclerView.adapter = orderAdapter

        return view
    }

    // Retrieve order details from SharedPreferences
    private fun getLatestOrderDetails(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("LaundryGoPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("LATEST_ORDER", "No order found") ?: "No order found"
    }

    // Parse the order details string into an OrderDetails object
    private fun parseOrderDetails(orderDetails: String): OrderDetails {
        val detailsList = orderDetails.split("\n")
        return OrderDetails(
            orderId = detailsList[0].substringAfter(": ").trim(),
            fullName = detailsList[1].substringAfter(": ").trim(),
            contactNumber = detailsList[2].substringAfter(": ").trim(),
            address = detailsList[3].substringAfter(": ").trim(),
            deliveryDate = detailsList[4].substringAfter(": ").trim(),
            deliveryTime = detailsList[5].substringAfter(": ").trim(),
            paymentMethod = detailsList[6].substringAfter(": ").trim()
        )
    }
}
