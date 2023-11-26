package com.example.lab_application.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.cardview.widget.CardView
import androidx.constraintlayout.core.motion.utils.Utils
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.fragment.ListFragmentDirections
import com.example.lab_application.R
import com.example.lab_application.model.Place
import com.google.android.material.textview.MaterialTextView
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Locale


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
            val spf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
            val date = spf.format(item.date)
            holder.textView.text = item.city.toString()
            holder.textView2.text = date.toString()
            holder.textView3.text = item.about.toString()
            var rating = item.rating.toString()
            if (item.image.toString().isNotEmpty()) {
                val file = DocumentFile.fromSingleUri(context, item.image)
                if (file != null) {
                    if (file.exists()) {
                        holder.imageView.setImageURI(item.image)
                    } else {
                        Toast.makeText(context, "Image not found for " + item.city.toString(), LENGTH_LONG).show()
                    }
                }
            }
            if (rating == "0") {
                rating = "None"
            }
            holder.rating.text = "Rating: ${rating}"
            holder.itemView.findViewById<MaterialTextView>(R.id.item_about).movementMethod = ScrollingMovementMethod()
//            holder.itemView.findViewById<MaterialTextView>(R.id.item_about).setOnTouchListener(holder.itemView.findViewById<RecyclerView>(R.id.recycler_view).OnTouchListener { v, event ->
//                if (holder.itemView.findViewById<MaterialTextView>(R.id.item_about).hasFocus()) {
//                    v.parent.requestDisallowInterceptTouchEvent(true)
//                    when (event.action and MotionEvent.ACTION_MASK) {
//                        MotionEvent.ACTION_SCROLL -> {
//                            v.parent.requestDisallowInterceptTouchEvent(false)
//                            return@OnTouchListener false
//                        }
//                    }
//                }
//                false
//            })
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
