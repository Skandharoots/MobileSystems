package com.example.lab_application.data

import com.example.lab_application.R
import com.example.lab_application.model.Affirmation

class DataSource() {
    fun loadAffirmations(): MutableList<Affirmation> {
        return mutableListOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.string.date1, R.string.about1, R.drawable.image1),
            Affirmation(R.string.affirmation2, R.string.date2, R.string.about2, R.drawable.image2),
            Affirmation(R.string.affirmation3, R.string.date3, R.string.about3, R.drawable.image3),
            Affirmation(R.string.affirmation4, R.string.date4, R.string.about4, R.drawable.image4),
            Affirmation(R.string.affirmation5, R.string.date5, R.string.about5, R.drawable.image5),
            Affirmation(R.string.affirmation6, R.string.date6, R.string.about6, R.drawable.image6),
            Affirmation(R.string.affirmation7, R.string.date7, R.string.about7, R.drawable.image7),
            Affirmation(R.string.affirmation8, R.string.date8, R.string.about8, R.drawable.image8),
            Affirmation(R.string.affirmation9, R.string.date9, R.string.about9, R.drawable.image9),
            Affirmation(R.string.affirmation10, R.string.date10, R.string.about10, R.drawable.image10))
        }

}