package com.example.lab_application.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_application.AddUpdateView
import com.example.lab_application.R
import com.example.lab_application.databinding.AddUpdateLayoutBinding
import com.example.lab_application.model.Affirmation

class ItemAdapter(
    private val context: Context,
    private val dataset: List<Affirmation>
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
            holder.textView.text = context.resources.getText(item.stringResourceId)
            holder.textView2.text = context.resources.getString(item.stringResourceId2)
            holder.textView3.text = context.resources.getString(item.stringResourceId3)
            holder.imageView.setImageResource(item.imageResourceId)
            holder.imageView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, AddUpdateView::class.java)
                intent.putExtra(AddUpdateView.CITY, holder.textView.text.toString())
                intent.putExtra(AddUpdateView.DATE, holder.textView2.text.toString())
                intent.putExtra(AddUpdateView.ABOUT, holder.textView3.text.toString())
                context.startActivity(intent)
            }

        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size
}
