package com.example.gymguru.presentation.home.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.gymguru.R
import com.example.gymguru.presentation.home.HomeScreenViewState
import com.example.gymguru.presentation.navigation.Screens
import com.example.gymguru.presentation.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    viewState: HomeScreenViewState,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = viewState.username,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigate(Screens.SearchExercisesScreen.route) },
                content = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimensions.iconMedium),
                        imageVector = Icons.Default.List,
                        contentDescription = stringResource(R.string.search_exercises_screen)
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = { navController.navigate(Screens.ProfileScreen.route) },
                content = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimensions.iconMedium),
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.open_profile)
                    )
                }
            )
        }
    )
}
