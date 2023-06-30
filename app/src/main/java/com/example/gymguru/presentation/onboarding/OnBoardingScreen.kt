package com.example.gymguru.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymguru.presentation.navigation.Screens
import com.example.gymguru.presentation.onboarding.pages.FinishPage
import com.example.gymguru.presentation.onboarding.pages.UserBirthdayPage
import com.example.gymguru.presentation.onboarding.pages.UserDetailsPage
import com.example.gymguru.presentation.onboarding.pages.WelcomePage
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    homeSnackBarHostState: SnackbarHostState,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()

    val pages = listOf(
        OnBoardingPages.OnBoardingPage { WelcomePage(pagerState) },
        OnBoardingPages.OnBoardingPage { UserBirthdayPage(viewModel, pagerState) },
        OnBoardingPages.OnBoardingPage { UserDetailsPage(viewModel, pagerState) },
        OnBoardingPages.OnBoardingPage { FinishPage(viewModel, pagerState) }
    )

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collectLatest { event ->
            when (event) {
                OnBoardingViewEvent.CloseOnBoarding -> navController.navigate(
                    Screens.HomeScreen.route
                ) {
                    popUpTo(Screens.OnBoardingScreen.route) {
                        inclusive = true
                    }
                }

                is OnBoardingViewEvent.ShowSnackBar -> homeSnackBarHostState.showSnackbar(
                    event.message
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageCount = pages.size,
            userScrollEnabled = false
        ) {
            pages[it].screen()
        }

        Row(
            Modifier
                .align(CenterHorizontally)
                .padding(12.dp)
        ) {
            repeat(pages.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    }
                Box(
                    modifier = Modifier
                        .padding(MaterialTheme.dimensions.s)
                        .clip(CircleShape)
                        .background(color)
                        .size(MaterialTheme.dimensions.l)
                )
            }
        }
    }
}
