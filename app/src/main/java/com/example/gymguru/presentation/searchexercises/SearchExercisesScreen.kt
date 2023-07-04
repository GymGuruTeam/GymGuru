package com.example.gymguru.presentation.searchexercises

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gymguru.R
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.presentation.composables.GymGuruOutlinedTextField
import com.example.gymguru.presentation.navigation.Screens
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchExercisesScreen(
    navController: NavController,
    viewModel: SearchExercisesViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.value

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collectLatest { event ->
            when (event) {
                is SearchExercisesViewEvent.OpenExerciseDetails -> navController.navigate(
                    Screens.ExerciseDetailsScreen.route + "/${event.exerciseId}"
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = CenterHorizontally
    ) {
        GymGuruOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(MaterialTheme.dimensions.m),
            state = viewState.query,
            onValueChange = {
                viewModel.searchExercises(it)
            },
            shape = CircleShape,
            hint = stringResource(R.string.search)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.dimensions.s),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.m)
        ) {
            items(DomainExerciseType.values()) {
                Card(
                    shape = CircleShape,
                    border = if (viewState.selectedType == it) {
                        BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.primary
                        )
                    } else {
                        BorderStroke(0.dp, Color.Transparent)
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                viewModel.onTypeClicked(
                                    type = if (it == viewState.selectedType) null else it
                                )
                            }
                            .padding(MaterialTheme.dimensions.m),
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.l))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimensions.l),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.l)
        ) {
            items(viewState.exercises) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.clickable { viewModel.onExerciseClick(it.id) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(MaterialTheme.dimensions.imageItem),
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.exercise_preview)
                        )
                        Text(
                            text = it.name.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
