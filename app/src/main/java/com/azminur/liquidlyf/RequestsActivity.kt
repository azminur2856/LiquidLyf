package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RequestsActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var requestsRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_requests)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        backArrow = findViewById(R.id.back_arrow)
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        requestsRecyclerView = findViewById(R.id.requests_recycler_view)

        backArrow.setOnClickListener {
            finish()
        }

        requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        val requestList = listOf(
            RequestItem("July 15, 2024", "Dhaka Medical College Hospital", "Completed"),
            RequestItem("June 20, 2024", "Kurmitola General Hospital", "Cancelled"),
            RequestItem("May 5, 2024", "Jashore Sodor Hospital", "Completed")
        )
        requestsRecyclerView.adapter = RequestsAdapter(requestList)

        bottomNavigationView.selectedItemId = R.id.nav_requests

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
                    true
                }
                R.id.nav_requests -> {
                    // Do nothing or handle re-selecting the requests
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_requests
    }
}

data class RequestItem(val date: String, val hospital: String, val status: String)

class RequestsAdapter(private val requests: List<RequestItem>) :
    RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.request_date)
        val hospital: TextView = view.findViewById(R.id.request_hospital)
        val status: TextView = view.findViewById(R.id.request_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.date.text = request.date
        holder.hospital.text = request.hospital
        holder.status.text = request.status
    }

    override fun getItemCount() = requests.size
}
