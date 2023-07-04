package com.example.gymguru.presentation.planworkout

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef
import com.example.gymguru.domain.model.DomainWorkoutPlan
import com.example.gymguru.domain.usecase.DeleteWorkoutPlanExerciseCrossRefUseCase
import com.example.gymguru.domain.usecase.GetExercisesUseCase
import com.example.gymguru.domain.usecase.InsertWorkoutPlanExerciseCrossRefUseCase
import com.example.gymguru.domain.usecase.InsertWorkoutPlanUseCase
import com.example.gymguru.domain.usecase.ObserveWorkoutPlanUseCase
import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlanWorkoutViewModel @Inject constructor(
    private val observeWorkoutPlanUseCase: ObserveWorkoutPlanUseCase,
    private val insertWorkoutPlanUseCase: InsertWorkoutPlanUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val insertWorkoutPlanExerciseCrossRefUseCase: InsertWorkoutPlanExerciseCrossRefUseCase,
    private val deleteWorkoutPlanExerciseCrossRefUseCase: DeleteWorkoutPlanExerciseCrossRefUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = mutableStateOf(PlanWorkoutViewState())
    val viewState: State<PlanWorkoutViewState> = _viewState

    init {
        savedStateHandle.get<Int>("planId")?.let { planId ->
            viewModelScope.launch(IO) {
                val exerciseList = getExercisesUseCase("", null)
                observeWorkoutPlanUseCase(planId).collect {
                    withContext(Main) {
                        _viewState.value = _viewState.value.copy(
                            planId = planId,
                            exercises = it.domainExercises,
                            title = GymGuruTextFieldState(
                                value = it.domainWorkoutPlan.name.orEmpty()
                            ),
                            exercisesList = exerciseList
                        )
                    }
                }
            }
        }
    }

    fun updateTitle(input: String) {
        _viewState.value = _viewState.value.copy(title = GymGuruTextFieldState(value = input))
    }

    fun removeFromPlan(exerciseId: Int?) {
        if (exerciseId != null) {
            _viewState.value.planId?.let { planId ->
                viewModelScope.launch(IO) {
                    deleteWorkoutPlanExerciseCrossRefUseCase(
                        WorkoutPlanExerciseCrossRef(
                            planId = planId,
                            id = exerciseId
                        )
                    )
                }
            }
        }
    }

    fun onEditModeClick() {
        if (_viewState.value.isEditMode) {
            saveTitle()
        }
        _viewState.value = _viewState.value.copy(isEditMode = !_viewState.value.isEditMode)
    }

    private fun saveTitle() {
        viewModelScope.launch(IO) {
            insertWorkoutPlanUseCase(
                DomainWorkoutPlan(
                    viewState.value.planId,
                    viewState.value.title.value
                )
            )
        }
    }

    fun onAddExercise(id: Int?) {
        if (id != null) {
            _viewState.value.planId?.let { planId ->
                viewModelScope.launch(IO) {
                    insertWorkoutPlanExerciseCrossRefUseCase(
                        WorkoutPlanExerciseCrossRef(
                            planId = planId,
                            id = id
                        )
                    )
                }
            }
        }
    }
}
