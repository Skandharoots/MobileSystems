package com.example.lab_application.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.R
import com.example.lab_application.model.Place

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
            //val imgFile = File(item.image.toString())
            //var myBitmap: Bitmap? = null
//            if(imgFile.exists()) {
//                myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
//            }
            holder.textView.text = item.city.toString()
            holder.textView2.text = item.date.toString()
            holder.textView3.text = item.about.toString()
            var rating = item.rating.toString()
            if (rating == "0") {
                rating = "None"
            }
            holder.rating.text = "Rating: ${rating}"
//            holder.itemView.findViewById<CardView>(R.id.card_layout).setOnClickListener {
//                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
//                holder.itemView.findNavController().navigate(action)
//            }
        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size

        fun setData(place: List<Place>) {
            dataset = place
            notifyDataSetChanged()
        }
}
