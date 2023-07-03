package com.example.gymguru.presentation.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.model.DomainUserWeight
import com.example.gymguru.domain.usecase.InsertLocalUserWeightUseCase
import com.example.gymguru.domain.usecase.SetIsOnBoardingShownUseCase
import com.example.gymguru.domain.usecase.SetLocalUserBirthdayUseCase
import com.example.gymguru.domain.usecase.SetLocalUserHeightUseCase
import com.example.gymguru.domain.usecase.SetLocalUserNameUseCase
import com.example.gymguru.domain.validator.UserHeightValidator
import com.example.gymguru.domain.validator.UserWeightValidator
import com.example.gymguru.domain.validator.UsernameValidator
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
    private val insertLocalUserWeightUseCase: InsertLocalUserWeightUseCase,
    private val userWeightValidator: UserWeightValidator,
    private val userHeightValidator: UserHeightValidator,
    private val usernameValidator: UsernameValidator
) : ViewModel() {

    private val _viewState = mutableStateOf(OnBoardingViewState())
    val viewState: State<OnBoardingViewState> = _viewState

    private val _viewEvent = MutableSharedFlow<OnBoardingViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    fun updateUsername(input: String) {
        _viewState.value = _viewState.value.copy(name = usernameValidator(input))
    }

    fun updateHeight(input: String) {
        _viewState.value = _viewState.value.copy(height = userHeightValidator(input))
    }

    fun updateWeight(input: String) {
        _viewState.value = _viewState.value.copy(weight = userWeightValidator(input))
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
                    DomainUserWeight(null, _viewState.value.weight.value.toFloat(), LocalDate.now())
                )
            } catch (e: java.lang.Exception) {
                Timber.e("Couldn't insert weight")
            }
            setIsOnBoardingShownUseCase(true)
            _viewEvent.emit(OnBoardingViewEvent.CloseOnBoarding)
        }
    }
}
