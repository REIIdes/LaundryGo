package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DeliveryActivity : AppCompatActivity() {

    // Define your other views and properties here
    private lateinit var etFullName: EditText
    private lateinit var etContact: EditText
    private lateinit var etAddress: EditText
    private lateinit var etDate: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var btnConfirm: Button
    private lateinit var ivGcash: ImageView
    private lateinit var ivCash: ImageView
    private lateinit var backButton: ImageView  // Back button reference

    private var selectedPaymentMethod: String = ""

    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        // Initialize Views
        etFullName = findViewById(R.id.etfullname)
        etContact = findViewById(R.id.etnum)
        etAddress = findViewById(R.id.etaddress)
        etDate = findViewById(R.id.editTextEmail)
        timePicker = findViewById(R.id.timePicker)
        btnConfirm = findViewById(R.id.buttonLogin)
        ivGcash = findViewById(R.id.ivgcash)
        ivCash = findViewById(R.id.ivcash)
        backButton = findViewById(R.id.backButton) // Initialize back button

        ivGcash.setOnClickListener {
            selectedPaymentMethod = "GCash"
            ivGcash.setBackgroundResource(R.drawable.selected_border)
            ivCash.setBackgroundResource(0)
        }

        ivCash.setOnClickListener {
            selectedPaymentMethod = "Cash"
            ivCash.setBackgroundResource(R.drawable.selected_border)
            ivGcash.setBackgroundResource(0)
        }

        btnConfirm.setOnClickListener {
            // Call the function here
            showConfirmationDialog()
        }

        // Handle back button click
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed() // Navigates back to the previous activity
    }

    // Ensure this method is defined within the DeliveryActivity class
    private fun showConfirmationDialog() {
        val orderId = "ORD" + (100000..999999).random()
        val hour = timePicker.hour
        val minute = timePicker.minute
        val formattedTime = String.format("%02d:%02d", hour, minute)
        val selectedDate = etDate.text.toString()
        val fullName = etFullName.text.toString()
        val contactNumber = etContact.text.toString()
        val address = etAddress.text.toString()

        if (fullName.isEmpty() || contactNumber.isEmpty() || address.isEmpty() || selectedDate.isEmpty() || selectedPaymentMethod.isEmpty()) {
            Toast.makeText(this, "Please fill all details and select a payment method", Toast.LENGTH_SHORT).show()
            return
        }

        val receiptMessage = """
        Order ID: $orderId
        Name: $fullName
        Contact: $contactNumber
        Address: $address
        Delivery Date: $selectedDate
        Delivery Time: $formattedTime
        Payment: $selectedPaymentMethod
    """.trimIndent()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Order Confirmation")
        builder.setMessage(receiptMessage)
        builder.setPositiveButton("Confirm") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_LONG).show()

            // Set order details in ViewModel
            val orderDetails = OrderDetails(
                orderId = orderId,
                fullName = fullName,
                contactNumber = contactNumber,
                address = address,
                deliveryDate = selectedDate,
                deliveryTime = formattedTime,
                paymentMethod = selectedPaymentMethod
            )

            orderViewModel.setOrderDetails(orderDetails)

            // Navigate to HomeActivity using Intent
            val intent = Intent(this, home::class.java)
            startActivity(intent)

            // Close DeliveryActivity
            finish() // This will close the current DeliveryActivity
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}
