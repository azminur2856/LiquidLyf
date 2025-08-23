package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RequestsFragment : Fragment() {

    private lateinit var requestsRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestsRecyclerView = view.findViewById(R.id.requests_recycler_view)

        requestsRecyclerView.layoutManager = LinearLayoutManager(context)
        val requestList = listOf(
            RequestItem("July 15, 2024", "Dhaka Medical College Hospital", "Completed"),
            RequestItem("June 20, 2024", "Kurmitola General Hospital", "Cancelled"),
            RequestItem("May 5, 2024", "Jashore Sodor Hospital", "Completed")
        )
        requestsRecyclerView.adapter = RequestsAdapter(requestList)
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
