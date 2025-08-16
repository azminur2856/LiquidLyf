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

class SettingsActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var languageItem: ConstraintLayout
    private lateinit var themItem: ConstraintLayout
    private lateinit var pushNotificationItem: ConstraintLayout
    private lateinit var emailNotificationItem: ConstraintLayout
    private lateinit var dataSharingItem: ConstraintLayout
    private lateinit var locationServiceItem: ConstraintLayout
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
        pushNotificationItem = findViewById(R.id.push_notifications_switch)
        emailNotificationItem = findViewById(R.id.email_notifications_switch)
        dataSharingItem = findViewById(R.id.data_sharing_switch)
        locationServiceItem = findViewById(R.id.location_services_switch)
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

        pushNotificationItem.setOnClickListener {
            Toast.makeText(this, "Push Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        emailNotificationItem.setOnClickListener {
            Toast.makeText(this, "Email Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        dataSharingItem.setOnClickListener {
            Toast.makeText(this, "Data Sharing clicked", Toast.LENGTH_SHORT).show()
        }

        locationServiceItem.setOnClickListener {
            Toast.makeText(this, "Location Services clicked", Toast.LENGTH_SHORT).show()
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