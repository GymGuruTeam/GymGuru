package com.example.gymguru.presentation.planworkout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gymguru.R
import com.example.gymguru.presentation.composables.GymGuruOutlinedTextField
import com.example.gymguru.presentation.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanWorkoutScreen(viewModel: PlanWorkoutViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState.value

    val exercisesDialog = remember { mutableStateOf(false) }

    if (exercisesDialog.value) {
        AlertDialog(onDismissRequest = { exercisesDialog.value = false }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(MaterialTheme.dimensions.xl),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.m)
            ) {
                items(viewState.exercisesList) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.clickable { viewModel.onAddExercise(it.id) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(MaterialTheme.dimensions.imageItemDialog),
                                contentScale = ContentScale.Crop,
                                model = ImageRequest.Builder(LocalContext.current).data(it.imageUrl)
                                    .crossfade(true).build(),
                                contentDescription = stringResource(R.string.exercise_preview)
                            )
                            Text(
                                text = it.name.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        topBar = {
            TopAppBar(actions = {
                IconButton(onClick = { viewModel.onEditModeClick() }) {
                    Icon(
                        imageVector = if (viewState.isEditMode) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Edit
                        },
                        contentDescription = stringResource(R.string.edit_plan)
                    )
                }
            }, title = {
                Text(text = stringResource(R.string.workout_plan))
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { exercisesDialog.value = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_workout_plan)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GymGuruOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    readOnly = !viewState.isEditMode,
                    state = viewState.title,
                    hint = stringResource(R.string.title),
                    onValueChange = { viewModel.updateTitle(it) }
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.xl)
                ) {
                    items(viewState.exercises) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    modifier = Modifier.size(MaterialTheme.dimensions.imageItem),
                                    contentScale = ContentScale.Crop,
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(it.imageUrl)
                                        .crossfade(true).build(),
                                    contentDescription = stringResource(R.string.exercise_preview)
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.name.toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                AnimatedVisibility(visible = viewState.isEditMode) {
                                    IconButton(onClick = { viewModel.removeFromPlan(it.id) }) {
                                        Icon(
                                            modifier = Modifier.size(
                                                MaterialTheme.dimensions.iconMedium
                                            ),
                                            tint = MaterialTheme.colorScheme.error,
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = stringResource(
                                                R.string.remove_from_plan
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
