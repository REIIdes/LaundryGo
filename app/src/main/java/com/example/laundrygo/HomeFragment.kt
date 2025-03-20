package com.example.laundrygo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Navigate to SoapActivity when 'wash' button is clicked
        val washButton: LinearLayout = view.findViewById(R.id.button1)
        washButton.setOnClickListener {
            navigateToSoapActivity()
        }

        // Navigate to ChoiceActivity when 'fold' button is clicked
        val foldButton: LinearLayout = view.findViewById(R.id.button2)
        foldButton.setOnClickListener {
            navigateToChoiceActivity() // ✅ Proper function call
        }

        // Navigate to SoapActivity when 'handwash' button is clicked
        val handwashButton: LinearLayout = view.findViewById(R.id.button3)
        handwashButton.setOnClickListener {
            navigateToSoapActivity()
        }

        // Navigate to SoapActivity when 'washdryfold' button is clicked
        val washdryfoldButton: LinearLayout = view.findViewById(R.id.button4)
        washdryfoldButton.setOnClickListener {
            navigateToSoapActivity()
        }

        // Replace the current fragment with the UserFragment when the user icon is clicked
        val userIcon: ImageView = view.findViewById(R.id.user)
        userIcon.setOnClickListener {
            replaceFragment(UserFragment())
        }

        // Navigate to NotificationFragment when the bell icon is clicked
        val bellIcon: ImageView = view.findViewById(R.id.bell)
        bellIcon.setOnClickListener {
            replaceFragment(NotificationFragment()) // Replace with NotificationFragment
        }

        return view
    }

    // Navigate to SoapActivity using intent
    private fun navigateToSoapActivity() {
        val intent = Intent(activity, SoapActivity::class.java)
        startActivity(intent)
    }

    // ✅ New function to navigate to ChoiceActivity
    private fun navigateToChoiceActivity() {
        val intent = Intent(activity, ChoiceActivity::class.java)
        startActivity(intent)
    }

    // Replace the current fragment with the given fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment) // Ensure that fragment_container exists in your activity layout
        transaction.addToBackStack(null) // Optionally add to back stack for backward navigation
        transaction.commit() // Commit the transaction to apply the change
    }
}