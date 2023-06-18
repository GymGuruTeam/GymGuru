package com.example.gymguru.presentation.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.domain.usecase.InsertLocalUserWeightUseCase
import com.example.gymguru.domain.usecase.SetIsOnBoardingShownUseCase
import com.example.gymguru.domain.usecase.SetLocalUserBirthdayUseCase
import com.example.gymguru.domain.usecase.SetLocalUserHeightUseCase
import com.example.gymguru.domain.usecase.SetLocalUserNameUseCase
import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setLocalUserNameUseCase: SetLocalUserNameUseCase,
    private val setLocalUserHeightUseCase: SetLocalUserHeightUseCase,
    private val setLocalUserBirthdayUseCase: SetLocalUserBirthdayUseCase,
    private val setIsOnBoardingShownUseCase: SetIsOnBoardingShownUseCase,
    private val insertLocalUserWeightUseCase: InsertLocalUserWeightUseCase
) : ViewModel() {

    private val _viewState = mutableStateOf(OnBoardingViewState())
    val viewState: State<OnBoardingViewState> = _viewState

    private val _viewEvent = MutableSharedFlow<OnBoardingViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    fun updateUsername(input: String) {
        if (input.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH) {
            _viewState.value = _viewState.value.copy(
                name = GymGuruTextFieldState(
                    value = input,
                    isError = false,
                    errorMessage = ""
                )
            )
        } else {
            _viewState.value = _viewState.value.copy(
                name = GymGuruTextFieldState(
                    value = input,
                    isError = true,
                    errorMessage = "Name should be $MIN_NAME_LENGTH to $MAX_NAME_LENGTH characters"
                )
            )
        }
    }

    fun updateHeight(input: String) {
        if (input.isDigitsOnly() && isHeightCorrect(input)) {
            _viewState.value = _viewState.value.copy(
                height = GymGuruTextFieldState(
                    value = input,
                    isError = false,
                    errorMessage = ""
                )
            )
        } else {
            _viewState.value = _viewState.value.copy(
                height = GymGuruTextFieldState(
                    value = input,
                    isError = true,
                    errorMessage = "Number between $MIN_HEIGHT-$MAX_HEIGHT"
                )
            )
        }
    }

    fun updateWeight(input: String) {
        if (isWeightCorrect(input)) {
            _viewState.value = _viewState.value.copy(
                weight = GymGuruTextFieldState(
                    value = input,
                    isError = false,
                    errorMessage = ""
                )
            )
        } else {
            _viewState.value = _viewState.value.copy(
                weight = GymGuruTextFieldState(
                    value = input,
                    isError = true,
                    errorMessage = "Number between $MIN_WEIGHT-$MAX_WEIGHT"
                )
            )
        }
    }

    fun updateBirthday(date: LocalDate) {
        if (date.isAfter(LocalDate.now())) {
            viewModelScope.launch {
                _viewEvent.emit(OnBoardingViewEvent.ShowSnackBar("Choose date from the past"))
            }
        } else {
            _viewState.value = _viewState.value.copy(birthday = date)
        }
    }

    fun saveUserData() {
        viewModelScope.launch {
            setLocalUserNameUseCase(_viewState.value.name.value)
            setLocalUserBirthdayUseCase(_viewState.value.birthday)
            if (_viewState.value.height.value.isDigitsOnly()) {
                setLocalUserHeightUseCase(_viewState.value.height.value.toInt())
            }
            try {
                insertLocalUserWeightUseCase(
                    UserWeight(1, _viewState.value.weight.value.toFloat(), LocalDate.now())
                )
            } catch (e: java.lang.Exception) {
                Timber.e("Couldn't insert weight")
            }
            setIsOnBoardingShownUseCase(true)
            _viewEvent.emit(OnBoardingViewEvent.CloseOnBoarding)
        }
    }

    private fun isHeightCorrect(input: String): Boolean = try {
        input.toInt() in MIN_HEIGHT..MAX_HEIGHT
    } catch (e: Exception) {
        false
    }

    private fun isWeightCorrect(input: String): Boolean = try {
        input.toFloat() in MIN_WEIGHT..MAX_WEIGHT
    } catch (e: Exception) {
        false
    }

    companion object {
        private const val MIN_NAME_LENGTH = 3
        private const val MAX_NAME_LENGTH = 16
        private const val MIN_HEIGHT = 50
        private const val MAX_HEIGHT = 300
        private const val MIN_WEIGHT = 20f
        private const val MAX_WEIGHT = 500f
    }
}
