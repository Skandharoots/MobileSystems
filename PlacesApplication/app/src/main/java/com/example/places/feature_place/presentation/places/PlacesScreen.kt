package com.example.places.feature_place.presentation.places

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel()
) {
    var scaffoldState = rememberScaffoldState()
    var scope = rememberCoroutineScope()
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { TODO() },
                Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add place")
            }
            scaffoldState = scaffoldState
        },

    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.places) { place ->
                PlaceItem (
                    place = place,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { TODO() },
                    onDeleteClick = {
                        viewModel.onEvent(PlacesEvent.DeletePlace(place))
                        scope.launch {
                            scaffoldState.snackbarHostState.show
                        }
                    }
                )
            }
        }
    }
}