package com.example.places.feature_place.presentation.places.components

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Date

@Composable
fun PlaceCard(
    city: String,
    date: Date,
    about: String,
    rating: Int,
    picture: ImageView,
    checked: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        RadioButton(selected = checked,
//                    onClick = onCheck,
//                    colors = RadioButtonDefaults.colors(
//                        selectedColor = MaterialTheme.colorScheme.primary,
//                        unselectedColor = MaterialTheme.colorScheme.onBackground
//                    )
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = city, style = MaterialTheme.typography.headlineMedium)
//        //Text(text = date, )
//        ConstraintLayout {
//            Icon(painter = , contentDescription = )
//            Text()
//        }
    }
}