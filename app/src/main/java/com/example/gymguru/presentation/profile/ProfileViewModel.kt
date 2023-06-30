package com.example.gymguru.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.domain.usecase.DeleteLocalUserWeightByIdUseCase
import com.example.gymguru.domain.usecase.InsertLocalUserWeightUseCase
import com.example.gymguru.domain.usecase.ObserveLocalUserBirthdayUseCase
import com.example.gymguru.domain.usecase.ObserveLocalUserHeightUseCase
import com.example.gymguru.domain.usecase.ObserveLocalUserNameUseCase
import com.example.gymguru.domain.usecase.ObserveLocalUserWeightsUseCase
import com.example.gymguru.domain.usecase.SetLocalUserHeightUseCase
import com.example.gymguru.domain.usecase.SetLocalUserNameUseCase
import com.example.gymguru.domain.validator.UserHeightValidator
import com.example.gymguru.domain.validator.UserWeightValidator
import com.example.gymguru.domain.validator.UsernameValidator
import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeLocalUserNameUseCase: ObserveLocalUserNameUseCase,
    private val observeLocalUserWeightsUseCase: ObserveLocalUserWeightsUseCase,
    private val observeLocalUserHeightUseCase: ObserveLocalUserHeightUseCase,
    private val observeLocalUserBirthdayUseCase: ObserveLocalUserBirthdayUseCase,
    private val insertLocalUserWeightUseCase: InsertLocalUserWeightUseCase,
    private val setLocalUserNameUseCase: SetLocalUserNameUseCase,
    private val setLocalUserHeightUseCase: SetLocalUserHeightUseCase,
    private val userWeightValidator: UserWeightValidator,
    private val userHeightValidator: UserHeightValidator,
    private val deleteLocalUserWeightByIdUseCase: DeleteLocalUserWeightByIdUseCase,
    private val usernameValidator: UsernameValidator
) : ViewModel() {

    private val _viewState = mutableStateOf(ProfileScreenViewState())
    val viewState: State<ProfileScreenViewState> = _viewState

    private val _viewEvent = MutableSharedFlow<ProfileViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    init {
        loadLocalUserData()
    }

    private fun loadLocalUserData() {
        viewModelScope.launch(IO) {
            observeLocalUserNameUseCase().collect {
                withContext(Dispatchers.Main) {
                    _viewState.value =
                        _viewState.value.copy(name = GymGuruTextFieldState(value = it.orEmpty()))
                }
            }
        }

        viewModelScope.launch(IO) {
            observeLocalUserHeightUseCase().collect {
                withContext(Dispatchers.Main) {
                    _viewState.value =
                        _viewState.value.copy(height = GymGuruTextFieldState(value = it.toString()))
                }
            }
        }

        viewModelScope.launch(IO) {
            observeLocalUserWeightsUseCase().collect {
                withContext(Dispatchers.Main) {
                    _viewState.value = _viewState.value.copy(weightList = it)
                }
            }
        }
    }

    fun updateUsername(input: String) {
        _viewState.value = _viewState.value.copy(name = usernameValidator(input))
    }

    fun updateHeight(input: String) {
        _viewState.value = _viewState.value.copy(height = userHeightValidator(input))
    }

    fun updateWeight(input: String) {
        _viewState.value = _viewState.value.copy(weight = userWeightValidator(input))
    }

    fun onEditProfileClick(isEditMode: Boolean) {
        if (!isEditMode) {
            viewModelScope.launch(IO) {
                if (!_viewState.value.name.isError && !_viewState.value.height.isError) {
                    setLocalUserNameUseCase(_viewState.value.name.value)
                    setLocalUserHeightUseCase(_viewState.value.height.value.toInt())
                    _viewState.value = _viewState.value.copy(isEditMode = isEditMode)
                } else {
                    _viewEvent.emit(
                        ProfileViewEvent.ShowSnackBar("Make sure you provided correct data")
                    )
                }
            }
        } else {
            _viewState.value = _viewState.value.copy(isEditMode = isEditMode)
        }
    }

    fun saveWeight() {
        viewModelScope.launch(IO) {
            try {
                insertLocalUserWeightUseCase(
                    UserWeight(
                        null,
                        _viewState.value.weight.value.toFloat(),
                        LocalDate.now()
                    )
                )
            } catch (e: java.lang.Exception) {
                Timber.e("Couldn't insert weight")
            }
        }
    }

    fun deleteUserWeight(userWeight: UserWeight) {
        viewModelScope.launch(IO) {
            userWeight.id?.let { deleteLocalUserWeightByIdUseCase(it) }
        }
    }
}
