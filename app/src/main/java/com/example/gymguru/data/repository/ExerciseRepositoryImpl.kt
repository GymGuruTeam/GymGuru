package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalExerciseDao
import com.example.gymguru.domain.mapper.DomainExerciseMapper
import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val localExerciseDao: LocalExerciseDao,
    private val domainExerciseMapper: DomainExerciseMapper
) : ExerciseRepository {

    override suspend fun getExercises(
        query: String,
        exerciseType: DomainExerciseType?
    ): List<DomainExercise> = localExerciseDao.getExercises(
        query = query,
        exerciseType = exerciseType?.name ?: ""
    ).map { domainExerciseMapper(it) }

    override suspend fun getExercise(id: Int): DomainExercise =
        domainExerciseMapper(localExerciseDao.getExercise(id))
}
