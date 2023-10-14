package com.example.lab_application.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(@StringRes val stringResourceId: Int,
                       @StringRes val stringResourceId2: Int,
                       @StringRes val stringResourceId3: Int,
                       @DrawableRes val imageResourceId: Int,

) {
}