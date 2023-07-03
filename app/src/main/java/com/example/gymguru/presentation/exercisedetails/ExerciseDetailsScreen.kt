package com.example.gymguru.presentation.exercisedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gymguru.R
import com.example.gymguru.presentation.ui.theme.dimensions

@Composable
fun ExerciseDetailsScreen(
    viewModel: ExerciseDetailsViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(horizontal = MaterialTheme.dimensions.l)
    ) {
        if (viewState.domainExercise != null) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewState.domainExercise.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.exercise_preview)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.xl))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewState.domainExercise.name.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.xl))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewState.domainExercise.description.orEmpty(),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            CircularProgressIndicator()
        }
    }
}
