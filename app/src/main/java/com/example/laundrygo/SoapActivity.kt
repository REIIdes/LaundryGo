package com.example.laundrygo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SoapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soap)

        val buttonContinue: TextView = findViewById(R.id.buttonContinue)
        buttonContinue.setOnClickListener {
            navigateToFabricConditionerActivity()
        }

        val soapOptions = mapOf(
            R.id.backgroundTide to "TIDE",
            R.id.backgroundAriel to "ARIEL",
            R.id.backgroundBreeze to "BREEZE",
            R.id.backgroundSurf to "SURF"
        )


        soapOptions.forEach { (layoutId, soapName) ->
            findViewById<View>(layoutId).setOnClickListener {
                showQuantityDialog(soapName)
            }
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun navigateToFabricConditionerActivity() {
        val intent = Intent(this, FabricConditionerActivity::class.java)
        startActivity(intent)
    }

    private fun showQuantityDialog(selectedSoap: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        var quantity = 1

        val quantityTextView = dialogView.findViewById<TextView>(R.id.quantityTextView)
        val buttonIncrement = dialogView.findViewById<Button>(R.id.buttonIncrement)
        val buttonDecrement = dialogView.findViewById<Button>(R.id.buttonDecrement)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.buttonConfirm)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)

        quantityTextView.text = quantity.toString()

        buttonIncrement.setOnClickListener {
            quantity++
            quantityTextView.text = quantity.toString()
        }

        buttonDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = quantity.toString()
            }
        }

        buttonConfirm.setOnClickListener {
            navigateToFabricConditioner(selectedSoap, quantity)
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun navigateToFabricConditioner(selectedSoap: String, soapQuantity: Int) {
        val intent = Intent(this, FabricConditionerActivity::class.java).apply {
            putExtra("selectedSoap", selectedSoap)
            putExtra("soapQuantity", soapQuantity)
        }
        startActivity(intent)
    }
}
