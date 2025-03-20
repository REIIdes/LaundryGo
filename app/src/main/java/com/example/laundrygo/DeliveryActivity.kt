package com.example.laundrygo

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DeliveryActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etContact: EditText
    private lateinit var etAddress: EditText
    private lateinit var etDate: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var btnConfirm: Button
    private lateinit var ivGcash: ImageView
    private lateinit var ivCash: ImageView
    private lateinit var backButton: ImageView
    private lateinit var totalTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedPaymentMethod: String = ""
    private var totalCost: Int = 0

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
        backButton = findViewById(R.id.backButton)
        totalTextView = findViewById(R.id.totalTextView)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("LaundryGoPrefs", MODE_PRIVATE)

        // Retrieve total cost from previous activity
        totalCost = intent.getIntExtra("TOTAL_COST", 0)
        totalTextView.text = "Total: PHP $totalCost"

        etDate.inputType = android.text.InputType.TYPE_NULL
        etDate.setOnClickListener { showDatePicker() }

        // Payment method selection
        ivGcash.setOnClickListener { selectPaymentMethod("GCash", ivGcash, ivCash) }
        ivCash.setOnClickListener { selectPaymentMethod("Cash", ivCash, ivGcash) }

        // Confirm button click
        btnConfirm.setOnClickListener { showConfirmationDialog() }

        // Handle back button click
        backButton.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun selectPaymentMethod(method: String, selected: ImageView, unselected: ImageView) {
        selectedPaymentMethod = method
        selected.setBackgroundResource(R.drawable.selected_border)
        unselected.setBackgroundResource(0)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%02d", selectedMonth + 1, selectedDay, selectedYear % 100)
            etDate.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showConfirmationDialog() {
        val orderId = "ORD" + (100000..999999).random()
        val hour = timePicker.hour
        val minute = timePicker.minute
        val formattedTime = String.format("%02d:%02d", hour, minute)
        val selectedDate = etDate.text.toString().trim()
        val fullName = etFullName.text.toString().trim()
        val contactNumber = etContact.text.toString().trim()
        val address = etAddress.text.toString().trim()

        // Validate user input
        if (fullName.isEmpty() || contactNumber.isEmpty() || address.isEmpty() ||
            selectedDate.isEmpty() || selectedPaymentMethod.isEmpty()) {
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
            Total: PHP $totalCost
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Order Confirmation")
            .setMessage(receiptMessage)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_LONG).show()

                // Save order details
                saveOrderDetails(receiptMessage)

                // Navigate to HomeActivity
                startActivity(Intent(this, home::class.java))
                finish() // Close DeliveryActivity
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    // ✅ Save order details to SharedPreferences for ProfileFragment retrieval
    private fun saveOrderDetails(orderDetails: String) {
        val editor = sharedPreferences.edit()
        editor.putString("LATEST_ORDER", orderDetails)
        editor.apply()
    }
}
