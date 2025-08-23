package com.azminur.liquidlyf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var languageItem: ConstraintLayout
    private lateinit var themItem: ConstraintLayout
    private lateinit var pushNotificationSwitch: MaterialSwitch
    private lateinit var emailNotificationSwitch: MaterialSwitch
    private lateinit var dataSharingSwitch: MaterialSwitch
    private lateinit var locationServiceSwitch: MaterialSwitch
    private lateinit var termsOfServiceItem: ConstraintLayout
    private lateinit var privacyPoliceItem: ConstraintLayout
    private lateinit var logoutButton: Button
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        languageItem = findViewById(R.id.language_item)
        themItem = findViewById(R.id.theme_item)
        pushNotificationSwitch = findViewById(R.id.push_notifications_switch)
        emailNotificationSwitch = findViewById(R.id.email_notifications_switch)
        dataSharingSwitch = findViewById(R.id.data_sharing_switch)
        locationServiceSwitch = findViewById(R.id.location_services_switch)
        termsOfServiceItem = findViewById(R.id.terms_of_service_item)
        privacyPoliceItem = findViewById(R.id.privacy_policy_item)
        logoutButton = findViewById(R.id.logout_button)
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)

        backArrow.setOnClickListener {
            finish()
        }

        languageItem.setOnClickListener {
            Toast.makeText(this, "Language settings clicked", Toast.LENGTH_SHORT).show()
        }

        themItem.setOnClickListener {
            Toast.makeText(this, "Theme settings clicked", Toast.LENGTH_SHORT).show()
        }

        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Push Notifications: ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Push Notifications: OFF", Toast.LENGTH_SHORT).show()
            }
        }


        emailNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Email Notifications: ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Email Notifications: OFF", Toast.LENGTH_SHORT).show()
            }
        }

        dataSharingSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Data Sharing: ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data Sharing: OFF", Toast.LENGTH_SHORT).show()
            }
        }

        locationServiceSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Location Services: ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location Services: OFF", Toast.LENGTH_SHORT).show()
            }
        }


        termsOfServiceItem.setOnClickListener {
            Toast.makeText(this, "Terms of Service clicked", Toast.LENGTH_SHORT).show()
            openWebPage("https://www.aiub.edu")
        }

        privacyPoliceItem.setOnClickListener {
            Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
            openWebPage("https://portal.aiub.edu")

        }

        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()

            val sharedPref = getSharedPreferences("auth_prefs", MODE_PRIVATE)
            sharedPref.edit()
                .putBoolean("is_logged_in", false)
                .apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Prevent to go back
        }

        bottomNavigationView.selectedItemId = R.id.nav_settings

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_requests -> {
                    val intent = Intent(this, RequestsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    // Handle re-selecting the settings screen or do nothing
                    true
                }
                else -> false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_settings
    }
    private fun openWebPage(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}