package com.example.gymguru.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymguru.presentation.navigation.Screens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.value

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageCount = viewState.pages.size
        ) {
            viewState.pages[it].screen()
        }
        AnimatedVisibility(visible = viewState.pages.size == pagerState.currentPage.plus(1)) {
            Button(
                onClick = { navController.navigate(Screens.HomeScreen.route) }
            ) {
                Text(text = "Finish")
            }
        }

        Row(
            Modifier
                .align(CenterHorizontally)
                .padding(12.dp)
        ) {
            repeat(viewState.pages.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(14.dp)
                )
            }
        }
    }
}
