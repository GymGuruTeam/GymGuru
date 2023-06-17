package com.example.gymguru.presentation.onboarding.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.gymguru.R
import com.example.gymguru.presentation.composables.GymGuruButton
import com.example.gymguru.presentation.composables.GymGuruOutlinedTextField
import com.example.gymguru.presentation.onboarding.OnBoardingViewModel
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsernamePage(
    viewModel: OnBoardingViewModel,
    pagerState: PagerState
) {
    val viewState = viewModel.viewState.value

    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.profile))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        restartOnPlay = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimensions.xl),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.onboarding_provide_name_content),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f),
            composition = composition,
            progress = { progress }
        )

        GymGuruOutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            state = viewState.name,
            onValueChange = { viewModel.updateUsername(it) },
            hint = stringResource(R.string.name)
        )

        GymGuruOutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            state = viewState.height,
            keyboardType = KeyboardType.Number,
            onValueChange = { viewModel.updateHeight(it) },
            hint = stringResource(R.string.height)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                text = stringResource(R.string.back)
            )

            GymGuruButton(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                enabled = !viewState.name.isError && viewState.name.value.isNotEmpty() &&
                    !viewState.height.isError && viewState.height.value.isNotEmpty(),
                text = stringResource(R.string.next),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            )
        }
    }
}
