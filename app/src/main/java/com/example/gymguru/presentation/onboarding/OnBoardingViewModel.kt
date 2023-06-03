package com.example.gymguru.presentation.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.presentation.onboarding.pages.FinishPage
import com.example.gymguru.presentation.onboarding.pages.UserWeightPage
import com.example.gymguru.presentation.onboarding.pages.UsernamePage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {

    private val _viewState = mutableStateOf(OnBoardingViewState())
    val viewState: State<OnBoardingViewState> = _viewState

    init {
        setupOnBoardingPages()
    }

    private fun setupOnBoardingPages() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(
                pages = listOf(
                    OnBoardingPages.OnBoardingPage(screen = { UsernamePage() }),
                    OnBoardingPages.OnBoardingPage(screen = { UserWeightPage() }),
                    OnBoardingPages.OnBoardingPage(screen = { FinishPage() })
                )
            )
        }
    }
}
