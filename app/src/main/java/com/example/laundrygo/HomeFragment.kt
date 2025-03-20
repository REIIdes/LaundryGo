package com.example.laundrygo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    // Variable to store selected type of service (wash, handwash, fold, etc.)
    private var selectedType: String = "N/A"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Handle button clicks and set selection type
        val washButton: LinearLayout = view.findViewById(R.id.button1)
        washButton.setOnClickListener {
            selectedType = "wash"
            navigateToSoapActivity()
        }

        val foldButton: LinearLayout = view.findViewById(R.id.button2)
        foldButton.setOnClickListener {
            selectedType = "fold"
            navigateToChoiceActivity()
        }

        val handwashButton: LinearLayout = view.findViewById(R.id.button3)
        handwashButton.setOnClickListener {
            selectedType = "handwash"
            navigateToSoapActivity()
        }

        val washdryfoldButton: LinearLayout = view.findViewById(R.id.button4)
        washdryfoldButton.setOnClickListener {
            selectedType = "washdryfold"
            navigateToSoapActivity()
        }

        return view
    }

    // Navigate to SoapActivity when wash or handwash is selected
    private fun navigateToSoapActivity() {
        val intent = Intent(activity, SoapActivity::class.java)
        intent.putExtra("selectedType", selectedType)  // Pass selected type
        startActivityForResult(intent, REQUEST_CODE_SOAP)
    }

    // Navigate to ChoiceActivity when fold is selected
    private fun navigateToChoiceActivity() {
        val intent = Intent(activity, ChoiceActivity::class.java)
        intent.putExtra("selectedType", selectedType)  // Pass selected type
        startActivity(intent)
    }

    // Handle the result from SoapActivity (when selections are made)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SOAP && resultCode == Activity.RESULT_OK) {
            // We don’t need to save individual selections here anymore, just move to SummaryActivity
            navigateToSummaryActivity()
        }
    }

    // Navigate to SummaryActivity and pass the selected type
    private fun navigateToSummaryActivity() {
        val intent = Intent(activity, SummaryActivity::class.java)
        intent.putExtra("selectedType", selectedType)  // Pass selected type to SummaryActivity
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_CODE_SOAP = 1
    }
}
