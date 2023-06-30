package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalExerciseDao
import com.example.gymguru.domain.mapper.DomainExerciseMapper
import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val localExerciseDao: LocalExerciseDao,
    private val domainExerciseMapper: DomainExerciseMapper
) : ExerciseRepository {

    override suspend fun getExercises(): List<DomainExercise> =
        localExerciseDao.getExercises().map { domainExerciseMapper(it) }
}
