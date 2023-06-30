package com.example.gymguru.presentation.onboarding.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.gymguru.R
import com.example.gymguru.presentation.composables.GymGuruButton
import com.example.gymguru.presentation.onboarding.OnBoardingViewModel
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinishPage(
    viewModel: OnBoardingViewModel,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.complete))

    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = !pagerState.isScrollInProgress && pagerState.currentPage == 3,
        iterations = 1,
        restartOnPlay = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimensions.xl),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.onboarding_finish_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            composition = composition,
            progress = { progress }
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GymGuruButton(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                text = stringResource(R.string.save),
                onClick = {
                    viewModel.saveUserData()
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.s))

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
        }
    }
}
