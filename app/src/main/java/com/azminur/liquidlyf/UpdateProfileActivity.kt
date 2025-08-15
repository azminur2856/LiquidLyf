package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var backArrow: ImageView
    private lateinit var bloodGroupSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_profile)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        bloodGroupSpinner = findViewById(R.id.blood_group_spinner)
        saveButton = findViewById(R.id.save_button)
        progressBar = findViewById(R.id.progress_bar)

        backArrow.setOnClickListener {
            finish()
        }

        val bloodGroups = resources.getStringArray(R.array.blood_groups)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = adapter

        saveButton.setOnClickListener {
            // TODO: Implement logic to save the updated profile information.
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.VISIBLE

            android.os.Handler().postDelayed({
                progressBar.visibility = View.GONE
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }, 1000)
        }
    }
}