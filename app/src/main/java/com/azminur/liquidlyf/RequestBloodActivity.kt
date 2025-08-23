package com.azminur.liquidlyf

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

class RequestBloodActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var backArrow: ImageView
    private lateinit var submitRequestButton: Button
    private lateinit var bloodGroupSpinner: Spinner
    private lateinit var progressBar: ProgressBar

    private lateinit var map: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val searchResultsList = mutableListOf<SearchResult>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_blood)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView = findViewById(R.id.search_bar)
        searchResultsRecyclerView = findViewById(R.id.search_results_recycler_view)

        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsAdapter = SearchResultsAdapter(searchResultsList) { selectedResult ->
            updateMapWithMarker(selectedResult.location, selectedResult.name)
            searchResultsRecyclerView.visibility = View.GONE
            searchView.setQuery(selectedResult.name, false)
            searchView.clearFocus()
        }
        searchResultsRecyclerView.adapter = searchResultsAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchForHospital(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searchForHospital(newText)
                    searchResultsRecyclerView.visibility = View.VISIBLE
                } else {
                    searchResultsList.clear()
                    searchResultsAdapter.notifyDataSetChanged()
                    searchResultsRecyclerView.visibility = View.GONE
                }
                return true
            }
        })

        backArrow = findViewById(R.id.back_arrow)
        submitRequestButton = findViewById(R.id.submit_request_button)
        bloodGroupSpinner = findViewById(R.id.blood_group_spinner)
        progressBar = findViewById(R.id.progress_bar)

        val bloodGroups = resources.getStringArray(R.array.blood_groups)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = adapter

        backArrow.setOnClickListener {
            finish()
        }

        submitRequestButton.setOnClickListener {
            // TODO: Implement logic to handle the blood request submission.
            Toast.makeText(this, "Request submitted!", Toast.LENGTH_SHORT).show()

            progressBar.visibility = View.VISIBLE

            android.os.Handler().postDelayed({
                progressBar.visibility = View.GONE
//                val intent = Intent(this, AfterLoginActivity::class.java)
//                startActivity(intent)
            }, 1000)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val defaultLocation = LatLng(23.8103, 90.4125) // Coordinates for Dhaka
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    private fun searchForHospital(query: String) = coroutineScope.launch {
        map.clear()
        searchResultsList.clear()

        withContext(Dispatchers.IO) {
            val geocoder = Geocoder(this@RequestBloodActivity, Locale.getDefault())
            try {
                val addresses: List<Address>? = geocoder.getFromLocationName("hospitals in $query, Bangladesh", 10)

                withContext(Dispatchers.Main) {
                    if (!addresses.isNullOrEmpty()) {
                        addresses.forEach { address ->
                            val name = address.featureName ?: "Unknown Location"
                            val latLng = LatLng(address.latitude, address.longitude)
                            val result = SearchResult(name, latLng)
                            searchResultsList.add(result)
                        }
                    } else {
                        Toast.makeText(this@RequestBloodActivity, "No results found for '$query'", Toast.LENGTH_SHORT).show()
                    }
                    searchResultsAdapter.notifyDataSetChanged()
                }
            } catch (e: IOException) {
                Log.e("RequestBloodActivity", "Geocoder failed", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RequestBloodActivity, "Network error, please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateMapWithMarker(location: LatLng, title: String) {
        map.clear()
        map.addMarker(MarkerOptions().position(location).title(title))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}

// Data class for a search result
data class SearchResult(val name: String, val location: LatLng)

// Adapter for the search results RecyclerView
class SearchResultsAdapter(
    private val results: List<SearchResult>,
    private val onResultClick: (SearchResult) -> Unit
) : RecyclerView.Adapter<SearchResultsAdapter.ResultViewHolder>() {

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultText: TextView = view.findViewById(R.id.result_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.resultText.text = result.name
        holder.itemView.setOnClickListener { onResultClick(result) }
    }

    override fun getItemCount() = results.size
}
