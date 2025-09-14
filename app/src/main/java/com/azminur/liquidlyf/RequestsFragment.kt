package com.azminur.liquidlyf

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            // Updated to include a donor when a request is "Accepted"
            RequestItem("July 15, 2024", "Dhaka Medical College Hospital", "Completed", null),
            RequestItem("June 20, 2024", "Kurmitola General Hospital", "Cancelled", null),
            RequestItem("May 5, 2024", "Jashore Sodor Hospital", "Accepted", DonorDetails("Azminur Rahman", "+8801234567890", "O+")),
            RequestItem("May 5, 2024", "Jashore Sodor Hospital", "Completed", null)
        )
        requestsRecyclerView.adapter = RequestsAdapter(requestList) { requestItem ->
            // Handle item click to show donor details if available
            if (requestItem.status == "Accepted" && requestItem.donor != null) {
                showDonorDetailsDialog(requestItem.donor)
            } else {
                Toast.makeText(requireContext(), "No donor assigned yet or request completed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDonorDetailsDialog(donor: DonorDetails) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_donor_details, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
        val alertDialog = builder.create()

        val donorNameTextView = dialogView.findViewById<TextView>(R.id.donor_name)
        val donorPhoneTextView = dialogView.findViewById<TextView>(R.id.donor_phone)
        val donorBloodGroupTextView = dialogView.findViewById<TextView>(R.id.donor_blood_group)
        val callButton = dialogView.findViewById<Button>(R.id.call_button)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)
        val completeButton = dialogView.findViewById<Button>(R.id.complete_button)

        donorNameTextView.text = "Donor Name: ${donor.name}"
        donorPhoneTextView.text = "Phone: ${donor.phone}"
        donorBloodGroupTextView.text = "Blood Group: ${donor.bloodGroup}"

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${donor.phone}")
            }
            startActivity(intent)
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            Toast.makeText(context, "Request with ${donor.name} has been cancelled.", Toast.LENGTH_SHORT).show()
            // Here you would implement your logic to update the request status
            alertDialog.dismiss()
        }

        completeButton.setOnClickListener {
            Toast.makeText(context, "Request with ${donor.name} marked as complete.", Toast.LENGTH_SHORT).show()
            // Here you would implement your logic to update the request status
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}


data class RequestItem(val date: String, val hospital: String, val status: String, val donor: DonorDetails?)
data class DonorDetails(val name: String, val phone: String, val bloodGroup: String)


class RequestsAdapter(private val requests: List<RequestItem>, private val onItemClick: (RequestItem) -> Unit) :
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

        holder.itemView.setOnClickListener {
            onItemClick(request)
        }
    }

    override fun getItemCount() = requests.size
}