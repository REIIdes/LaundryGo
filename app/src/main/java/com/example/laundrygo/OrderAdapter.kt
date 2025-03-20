package com.example.laundrygo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val orders: List<OrderDetails>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    // ViewHolder to bind the data to the RecyclerView items
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextView)
        val fullNameTextView: TextView = itemView.findViewById(R.id.fullNameTextView)
        val contactTextView: TextView = itemView.findViewById(R.id.contactTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val deliveryDateTextView: TextView = itemView.findViewById(R.id.deliveryDateTextView)
        val deliveryTimeTextView: TextView = itemView.findViewById(R.id.deliveryTimeTextView)
        val paymentMethodTextView: TextView = itemView.findViewById(R.id.paymentMethodTextView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        // Bind order data to the views
        holder.orderIdTextView.text = "Order ID: ${order.orderId}"
        holder.fullNameTextView.text = "Name: ${order.fullName}"
        holder.contactTextView.text = "Contact: ${order.contactNumber}"
        holder.addressTextView.text = "Address: ${order.address}"
        holder.deliveryDateTextView.text = "Delivery Date: ${order.deliveryDate}"
        holder.deliveryTimeTextView.text = "Delivery Time: ${order.deliveryTime}"
        holder.paymentMethodTextView.text = "Payment Method: ${order.paymentMethod}"
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount() = orders.size
}
