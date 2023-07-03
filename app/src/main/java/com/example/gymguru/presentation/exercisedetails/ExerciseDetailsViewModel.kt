package com.example.gymguru.presentation.exercisedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.usecase.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var viewState by mutableStateOf(ExerciseDetailsViewState())
        private set

    init {
        savedStateHandle.get<Int>("exerciseId")?.let { exerciseId ->
            getExercise(id = exerciseId)
        }
    }

    private fun getExercise(id: Int) {
        viewModelScope.launch(IO) {
            val result = getExerciseUseCase(id)

            withContext(Main) {
                viewState = viewState.copy(domainExercise = result)
            }
        }
    }
}
