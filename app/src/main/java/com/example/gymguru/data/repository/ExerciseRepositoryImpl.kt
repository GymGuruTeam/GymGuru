package com.example.gymguru.data.repository

import com.example.gymguru.data.local.LocalExerciseDao
import com.example.gymguru.data.model.WorkoutPlanExerciseCrossRef
import com.example.gymguru.domain.mapper.DomainExerciseMapper
import com.example.gymguru.domain.mapper.DomainWorkoutPlanMapper
import com.example.gymguru.domain.mapper.DomainWorkoutPlanWithExercisesMapper
import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType
import com.example.gymguru.domain.model.DomainWorkoutPlan
import com.example.gymguru.domain.model.DomainWorkoutPlanWithExercises
import com.example.gymguru.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val localExerciseDao: LocalExerciseDao,
    private val domainExerciseMapper: DomainExerciseMapper,
    private val domainWorkoutPlanMapper: DomainWorkoutPlanMapper,
    private val domainWorkoutPlanWithExercisesMapper: DomainWorkoutPlanWithExercisesMapper
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

    override suspend fun observeWorkoutPlans(): Flow<List<DomainWorkoutPlanWithExercises>> =
        localExerciseDao.getWorkoutPlans().map { plans ->
            plans.map { domainWorkoutPlanWithExercisesMapper(it) }
        }

    override fun observeWorkoutPlan(planId: Int): Flow<DomainWorkoutPlanWithExercises> =
        localExerciseDao.getWorkoutPlan(planId).filterNotNull()
            .map { domainWorkoutPlanWithExercisesMapper(it) }

    override suspend fun insertPlan(domainWorkoutPlan: DomainWorkoutPlan) {
        localExerciseDao.insertPlan(domainWorkoutPlanMapper(domainWorkoutPlan))
    }

    override suspend fun insertWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    ) {
        localExerciseDao.insertWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }

    override suspend fun deleteWorkoutPlanExerciseCrossRef(
        workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef
    ) {
        localExerciseDao.deleteWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }
}
