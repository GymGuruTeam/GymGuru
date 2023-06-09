package com.example.gymguru.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.model.DomainWorkoutPlan
import com.example.gymguru.domain.usecase.GetIsOnBoardingShownUseCase
import com.example.gymguru.domain.usecase.InsertWorkoutPlanUseCase
import com.example.gymguru.domain.usecase.ObserveLocalUserNameUseCase
import com.example.gymguru.domain.usecase.ObserveWorkoutPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIsOnBoardingShownUseCase: GetIsOnBoardingShownUseCase,
    private val observeLocalUserNameUseCase: ObserveLocalUserNameUseCase,
    private val insertWorkoutPlanUseCase: InsertWorkoutPlanUseCase,
    private val observeWorkoutPlansUseCase: ObserveWorkoutPlansUseCase
) : ViewModel() {

    var viewState by mutableStateOf(HomeScreenViewState())
        private set

    private val _viewEvent = MutableSharedFlow<HomeScreenViewEvent>(1)
    val viewEvent = _viewEvent.asSharedFlow()

    init {
        isOnBoardingShown()
        loadUserData()
        loadPlans()
    }

    private fun loadPlans() {
        viewModelScope.launch {
            observeWorkoutPlansUseCase().collect {
                viewState = viewState.copy(workoutPlans = it)
            }
        }
    }

    private fun loadUserData() {
        viewModelScope.launch(IO) {
            observeLocalUserNameUseCase().collect {
                withContext(Main) {
                    viewState = viewState.copy(username = it.orEmpty())
                }
            }
        }
    }

    private fun isOnBoardingShown() {
        viewModelScope.launch {
            if (!getIsOnBoardingShownUseCase()) {
                _viewEvent.emit(HomeScreenViewEvent.OpenOnBoarding)
            }
        }
    }

    fun createPlan(title: String) {
        viewModelScope.launch {
            insertWorkoutPlanUseCase(DomainWorkoutPlan(null, title))
        }
    }
}
