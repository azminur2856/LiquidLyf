package com.azminur.liquidlyf

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class DashboardFragment : Fragment() {

    private lateinit var availabilitySwitch: Switch
    private lateinit var availabilityText: TextView
    private lateinit var incomingRequestsRecyclerView: RecyclerView
    private lateinit var recentActivityRecyclerView: RecyclerView
    private lateinit var requestMaterialButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        availabilitySwitch = view.findViewById(R.id.availability_switch)
        availabilityText = view.findViewById(R.id.availability_text)
        incomingRequestsRecyclerView = view.findViewById(R.id.incoming_requests_recycler_view)
        recentActivityRecyclerView = view.findViewById(R.id.recent_activity_recycler_view)
        requestMaterialButton = view.findViewById(R.id.request_blood_fab)

        setupIncomingRequestsRecyclerView()
        setupRecentActivityRecyclerView()
        setupAvailabilitySwitch()

        requestMaterialButton.setOnClickListener {
            val intent = Intent(requireActivity(), RequestBloodActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAvailabilitySwitch() {
        if (availabilitySwitch.isChecked) {
            availabilityText.text = "Ready to donate (Available)"
            availabilityText.setTextColor(resources.getColor(R.color.green_status, null))
        } else {
            availabilityText.text = "Not Ready to donate (Unavailable)"
            availabilityText.setTextColor(resources.getColor(R.color.red_status, null))
        }

        availabilitySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                availabilityText.setTextColor(resources.getColor(R.color.green_status, null))
                availabilityText.text = "Ready to donate (Available)"
                Toast.makeText(requireContext(), "Availability set to Available", Toast.LENGTH_SHORT).show()
            } else {
                availabilityText.setTextColor(resources.getColor(R.color.red_status, null))
                availabilityText.text = "Not Ready to donate (Unavailable)"
                Toast.makeText(requireContext(), "Availability set to Not Available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupIncomingRequestsRecyclerView() {
        val incomingRequests = listOf(
            IncomingRequest("Urgent: Blood Type O-", "10 miles away"),
            IncomingRequest("Urgent: Blood Type A+", "12 miles away")
        )
        incomingRequestsRecyclerView.layoutManager = LinearLayoutManager(context)
        incomingRequestsRecyclerView.adapter = IncomingRequestsAdapter(requireContext(), incomingRequests)
    }

    private fun setupRecentActivityRecyclerView() {
        val recentActivities = listOf(
            RecentActivity("Blood Donation", "Donated to local hospital", "2 weeks ago", R.drawable.ic_check_circle),
            RecentActivity("Blood Request", "Request from local hospital", "1 month ago", R.drawable.ic_request_blood)
        )
        recentActivityRecyclerView.layoutManager = LinearLayoutManager(context)
        recentActivityRecyclerView.adapter = RecentActivityAdapter(recentActivities)
    }
}

// Data Classes
data class IncomingRequest(val title: String, val distance: String)
data class RecentActivity(val title: String, val subtitle: String, val time: String, val iconResId: Int)

// Adapters
class IncomingRequestsAdapter(private val context: Context, private val requests: List<IncomingRequest>) :
    RecyclerView.Adapter<IncomingRequestsAdapter.RequestViewHolder>() {

    class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.request_profile_image)
        val requestInfo: TextView = view.findViewById(R.id.request_info_text)
        val actionButton: Button = view.findViewById(R.id.action_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_incoming_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.requestInfo.text = "${request.title}\n${request.distance}"

        holder.actionButton.setOnClickListener {
            // Show the request details modal window
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Request Details")
            dialogBuilder.setMessage("Details for ${request.title}")
            dialogBuilder.setPositiveButton("Accept") { dialog, _ ->
                Toast.makeText(context, "Accepted request for ${request.title}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialogBuilder.setNegativeButton("Reject") { dialog, _ ->
                Toast.makeText(context, "Rejected request for ${request.title}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    override fun getItemCount() = requests.size
}

class RecentActivityAdapter(private val activities: List<RecentActivity>) :
    RecyclerView.Adapter<RecentActivityAdapter.ActivityViewHolder>() {

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.activity_icon)
        val info: TextView = view.findViewById(R.id.activity_info)
        val time: TextView = view.findViewById(R.id.activity_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.icon.setImageResource(activity.iconResId)
        holder.info.text = "${activity.title}\n${activity.subtitle}"
        holder.time.text = activity.time
    }

    override fun getItemCount() = activities.size
}