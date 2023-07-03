package com.example.gymguru.presentation.searchexercises

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.domain.usecase.GetExercisesUseCase
import com.example.gymguru.presentation.composables.GymGuruTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    private val _viewState = mutableStateOf(SearchExercisesViewState())
    val viewState: State<SearchExercisesViewState> = _viewState

    private val _viewEvent = MutableSharedFlow<SearchExercisesViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    private var searchExercise: Job? = null

    init {
        viewModelScope.launch(IO) {
            searchItems("", null)
        }
    }

    fun searchExercises(query: String) {
        _viewState.value = _viewState.value.copy(query = GymGuruTextFieldState(value = query))
        searchExercise?.cancel()

        searchExercise = viewModelScope.launch(IO) {
            delay(200L)

            searchItems(
                query = query,
                searchType = viewState.value.selectedType
            )
        }
    }

    private suspend fun searchItems(query: String, searchType: DomainExerciseType?) {
        val result = getExercisesUseCase(query, searchType)
        withContext(Main) {
            _viewState.value = _viewState.value.copy(exercises = result)
        }
    }

    fun onExerciseClick(id: Int?) {
        id?.let {
            viewModelScope.launch {
                _viewEvent.emit(SearchExercisesViewEvent.OpenExerciseDetails(id))
            }
        }
    }

    fun onTypeClicked(type: DomainExerciseType? = null) {
        _viewState.value =
            _viewState.value.copy(selectedType = type)

        viewModelScope.launch(IO) {
            searchItems(
                query = viewState.value.query.value,
                searchType = type
            )
        }
    }
}
