package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var saveChangesButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        saveChangesButton = findViewById(R.id.save_changes_button)
        progressBar = findViewById(R.id.progress_bar)

        backArrow.setOnClickListener {
            finish()
        }

        saveChangesButton.setOnClickListener {
            // TODO: Implement logic to validate the current password and update the password.
            Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.VISIBLE

            android.os.Handler().postDelayed({
                progressBar.visibility = View.GONE
//                val intent = Intent(this, AfterLoginActivity::class.java)
//                startActivity(intent)
                finish()
            }, 1000)
        }
    }
}