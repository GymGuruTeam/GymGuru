package com.example.gymguru.presentation.home

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymguru.R
import com.example.gymguru.presentation.home.composables.HomeAppBar
import com.example.gymguru.presentation.navigation.Screens
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState

    val addWorkoutPlanDialog = remember { mutableStateOf(false) }

    if (addWorkoutPlanDialog.value) {
        val title = remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { addWorkoutPlanDialog.value = false }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(MaterialTheme.dimensions.m))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(MaterialTheme.dimensions.xl),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Workout name")

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.m))

                TextField(value = title.value, onValueChange = { title.value = it })

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.m))

                Button(onClick = {
                    viewModel.createPlan(title.value)
                    addWorkoutPlanDialog.value = false
                }) {
                    Text(text = stringResource(R.string.create_plan))
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collectLatest { event ->
            when (event) {
                is HomeScreenViewEvent.OpenOnBoarding -> {
                    navController.navigate(Screens.OnBoardingScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            HomeAppBar(viewState = viewState, navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addWorkoutPlanDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_workout_plan)
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = MaterialTheme.dimensions.xl)
            ) {
                Text(
                    text = stringResource(R.string.my_plans),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.m))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.xl)
                ) {
                    itemsIndexed(viewState.workoutPlans) { index, plan ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(MaterialTheme.dimensions.m)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screens.PlanWorkoutScreen.route +
                                                    "/${plan.domainWorkoutPlan.planId}"
                                        )
                                    }
                                    .padding(MaterialTheme.dimensions.xxl),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.width(MaterialTheme.dimensions.xl))

                                Text(
                                    text = plan.domainWorkoutPlan.name.orEmpty(),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
