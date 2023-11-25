package com.example.lab_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.R
import com.example.lab_application.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat

class MarkerAdapter(
    private val context: Context,
    private var dataset: List<Marker>,
    private val myMap: GoogleMap? = null
) : RecyclerView.Adapter<MarkerAdapter.MarkerViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.


    class MarkerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_marker)
        val date: TextView = view.findViewById(R.id.date_marker)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.marker_item, parent, false)
        return MarkerViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val item = dataset[position]
        val spf = SimpleDateFormat("dd/MM/yyyy")
        val date = spf.format(item.date)
        holder.title.text = "Title: " + item.title.toString()
        holder.date.text = "Date: " + date.toString()
        holder.itemView.findViewById<ConstraintLayout>(R.id.marker_item).setOnClickListener {
            val latlng = LatLng(item.lat, item.lng)
            myMap?.animateCamera(CameraUpdateFactory.newLatLng(latlng))
            myMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14f))
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size

    fun setData(marker: List<Marker>) {
        dataset = marker
        notifyDataSetChanged()
    }

    fun getPlace(marker: Marker): Marker {
        return marker
    }
}