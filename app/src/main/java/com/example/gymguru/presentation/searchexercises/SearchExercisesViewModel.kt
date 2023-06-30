package com.example.gymguru.presentation.searchexercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymguru.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    init {
        viewModelScope.launch(IO) {
            Timber.d(getExercisesUseCase().toString())
        }
    }
}
