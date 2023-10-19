package com.example.lab_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import androidx.room.Update
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat


class UpdateFragment : Fragment() {


    private val args by navArgs<UpdateFragmentArgs>()
    private var id: Int? = null
    private var rating: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        id = args.currentPlace.id
        view.findViewById<TextInputEditText>(R.id.cityupdate).setText(args.currentPlace.city)
        val spf = SimpleDateFormat("dd/mm/yyyy")
        val date = spf.format(args.currentPlace.date)
        view.findViewById<TextInputEditText>(R.id.dateupdate).setText(date)
        view.findViewById<TextInputEditText>(R.id.aboutupdate).setText(args.currentPlace.about)
        rating = args.currentPlace.rating
        if (rating == 1) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 2) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 3) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 4) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }
        if (rating == 5) {
            view.findViewById<RadioButton>(R.id.b1u).isChecked = true
        }

        return view
    }

}