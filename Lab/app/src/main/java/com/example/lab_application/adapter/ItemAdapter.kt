package com.example.lab_application.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.ListFragment
import com.example.lab_application.ListFragmentDirections
import com.example.lab_application.R
import com.example.lab_application.model.Place
import java.text.SimpleDateFormat


class ItemAdapter(
    private val context: Context,
    private var dataset: List<Place>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just an Affirmation object.
        class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.item_title)
            val textView2: TextView = view.findViewById(R.id.item_date)
            val textView3: TextView = view.findViewById(R.id.item_about)
            val imageView: ImageView = view.findViewById(R.id.item_image)
            val rating: TextView = view.findViewById(R.id.rating)
        }

        /**
         * Create new views (invoked by the layout manager)
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return ItemViewHolder(adapterLayout)
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         */
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val item = dataset[position]
            val spf = SimpleDateFormat("dd/mm/yyyy")
            val date = spf.format(item.date)
            holder.textView.text = item.city.toString()
            holder.textView2.text = date.toString()
            holder.textView3.text = item.about.toString()
            var rating = item.rating.toString()
            holder.imageView.setImageURI(item.image)
            if (rating == "0") {
                rating = "None"
            }
            holder.rating.text = "Rating: ${rating}"
            holder.itemView.findViewById<CardView>(R.id.card_layout).setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
                holder.itemView.findNavController().navigate(action)
            }

        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size

        fun setData(place: List<Place>) {
            dataset = place
            notifyDataSetChanged()
        }

        fun getPlace(place: Place) : Place {
            return place
        }
}
