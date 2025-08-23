package com.azminur.liquidlyf

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsFragment : Fragment() {

    private lateinit var languageItem: ConstraintLayout
    private lateinit var themItem: ConstraintLayout
    private lateinit var pushNotificationSwitch: MaterialSwitch
    private lateinit var emailNotificationSwitch: MaterialSwitch
    private lateinit var dataSharingSwitch: MaterialSwitch
    private lateinit var locationServiceSwitch: MaterialSwitch
    private lateinit var termsOfServiceItem: ConstraintLayout
    private lateinit var privacyPoliceItem: ConstraintLayout
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageItem = view.findViewById(R.id.language_item)
        themItem = view.findViewById(R.id.theme_item)
        pushNotificationSwitch = view.findViewById(R.id.push_notifications_switch)
        emailNotificationSwitch = view.findViewById(R.id.email_notifications_switch)
        dataSharingSwitch = view.findViewById(R.id.data_sharing_switch)
        locationServiceSwitch = view.findViewById(R.id.location_services_switch)
        termsOfServiceItem = view.findViewById(R.id.terms_of_service_item)
        privacyPoliceItem = view.findViewById(R.id.privacy_policy_item)
        logoutButton = view.findViewById(R.id.logout_button)

        languageItem.setOnClickListener {
            Toast.makeText(requireContext(), "Language settings clicked", Toast.LENGTH_SHORT).show()
        }

        themItem.setOnClickListener {
            Toast.makeText(requireContext(), "Theme settings clicked", Toast.LENGTH_SHORT).show()
        }

        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(requireContext(), "Push Notifications: $status", Toast.LENGTH_SHORT).show()
        }

        emailNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(requireContext(), "Email Notifications: $status", Toast.LENGTH_SHORT).show()
        }

        dataSharingSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(requireContext(), "Data Sharing: $status", Toast.LENGTH_SHORT).show()
        }

        locationServiceSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(requireContext(), "Location Services: $status", Toast.LENGTH_SHORT).show()
        }

        termsOfServiceItem.setOnClickListener {
            Toast.makeText(requireContext(), "Terms of Service clicked", Toast.LENGTH_SHORT).show()
            openWebPage("https://www.aiub.edu")
        }

        privacyPoliceItem.setOnClickListener {
            Toast.makeText(requireContext(), "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
            openWebPage("https://portal.aiub.edu")
        }

        logoutButton.setOnClickListener {
            Toast.makeText(requireContext(), "Logged out!", Toast.LENGTH_SHORT).show()
            val sharedPref = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("is_logged_in", false).apply()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun openWebPage(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}