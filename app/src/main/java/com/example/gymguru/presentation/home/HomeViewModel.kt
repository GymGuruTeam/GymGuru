package com.example.gymguru.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.usecase.GetIsOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIsOnBoardingShownUseCase: GetIsOnBoardingShownUseCase
) : ViewModel() {

    private val _viewEvent = MutableSharedFlow<HomeScreenViewEvent>(1)
    val viewEvent = _viewEvent.asSharedFlow()

    init {
        isOnBoardingShown()
    }

    private fun isOnBoardingShown() {
        viewModelScope.launch {
            if (!getIsOnBoardingShownUseCase()) {
                _viewEvent.emit(HomeScreenViewEvent.OpenOnBoarding)
            }
        }
    }
}
