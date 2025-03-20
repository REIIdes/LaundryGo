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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bleach)

        fabricName = intent.getStringExtra("fabricName")
        fabricQuantity = intent.getIntExtra("fabricQuantity", 0)
        selectedSoap = intent.getStringExtra("selectedSoap")
        soapQuantity = intent.getIntExtra("soapQuantity", 0)

        val fabric1: LinearLayout = findViewById(R.id.fabric1)
        val fabric2: LinearLayout = findViewById(R.id.fabric2)
        val backButton: ImageView = findViewById(R.id.backButton)
        val buttonContinue: TextView = findViewById(R.id.buttonContinue)

        fabric1.setOnClickListener { showQuantityDialog("COLOR SAFE ZONROX") }
        fabric2.setOnClickListener { showQuantityDialog("ORIGINAL ZONROX") }

        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        buttonContinue.setOnClickListener { goToSummaryActivity(null, 0) }
    }

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

        confirmButton.setOnClickListener {
            goToSummaryActivity(bleachName, quantity)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener { dialog.dismiss() }
    }

    private fun goToSummaryActivity(bleachName: String?, bleachQuantity: Int) {
        val intent = Intent(this, SummaryActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
            putExtra("fabricName", fabricName)
            putExtra("fabricQuantity", fabricQuantity)
            bleachName?.let {
                putExtra("bleachName", bleachName)
                putExtra("bleachQuantity", bleachQuantity)
            }
        }
        startActivity(intent)
    }
}
