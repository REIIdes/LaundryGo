package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class BleachActivity : AppCompatActivity() {

    private var fabricName: String? = null
    private var fabricQuantity: Int = 0
    private var selectedSoap: String? = null
    private var soapQuantity: Int = 0
    private var selectedType: String? = null // Added selectedType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bleach)

        // Retrieve all relevant data from the previous activity
        fabricName = intent.getStringExtra("fabricName")
        fabricQuantity = intent.getIntExtra("fabricQuantity", 0)
        selectedSoap = intent.getStringExtra("selectedSoap")
        soapQuantity = intent.getIntExtra("soapQuantity", 0)
        selectedType = intent.getStringExtra("selectedType") // Get selected type

        // Initialize UI components
        val fabric1: LinearLayout = findViewById(R.id.fabric1)
        val fabric2: LinearLayout = findViewById(R.id.fabric2)
        val backButton: ImageView = findViewById(R.id.backButton)
        val buttonContinue: TextView = findViewById(R.id.buttonContinue)

        // Set click listeners for fabric options
        fabric1.setOnClickListener { showQuantityDialog("COLOR SAFE ZONROX") }
        fabric2.setOnClickListener { showQuantityDialog("ORIGINAL ZONROX") }

        // Handle the back button click
        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Handle continue button click
        buttonContinue.setOnClickListener { goToSummaryActivity(null, 0) }
    }

    // Method to show the quantity dialog for bleach
    private fun showQuantityDialog(bleachName: String) {
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_quantity, null)

        val quantityTextView: TextView = dialogView.findViewById(R.id.quantityTextView)
        val decrementButton: Button = dialogView.findViewById(R.id.buttonDecrement)
        val incrementButton: Button = dialogView.findViewById(R.id.buttonIncrement)
        val confirmButton: Button = dialogView.findViewById(R.id.buttonConfirm)
        val cancelButton: Button = dialogView.findViewById(R.id.buttonCancel)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.show()

        var quantity = 1
        quantityTextView.text = quantity.toString()

        decrementButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = quantity.toString()
            }
        }

        incrementButton.setOnClickListener {
            quantity++
            quantityTextView.text = quantity.toString()
        }

        // On confirm button click, pass the bleach data to the summary activity
        confirmButton.setOnClickListener {
            goToSummaryActivity(bleachName, quantity)
            dialog.dismiss()
        }

        // On cancel button click, dismiss the dialog
        cancelButton.setOnClickListener { dialog.dismiss() }
    }

    // Method to navigate to the SummaryActivity and pass all data
    private fun goToSummaryActivity(bleachName: String?, bleachQuantity: Int) {
        val intent = Intent(this, SummaryActivity::class.java).apply {
            // Pass selected soap, soap quantity, fabric, and fabric quantity
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("fabricName", fabricName)
            putExtra("fabricQuantity", fabricQuantity)

            // Pass bleach name and quantity if selected
            bleachName?.let {
                putExtra("bleachName", bleachName)
                putExtra("bleachQuantity", bleachQuantity)
            }

            // Pass selected service type (wash, fold, handwash, etc.)
            putExtra("selectedType", selectedType)
        }
        startActivity(intent)
    }
}
