package com.example.gymguru.domain.mapper

import com.example.gymguru.data.model.ExerciseEntity
import com.example.gymguru.data.model.ExerciseType
import com.example.gymguru.domain.model.DomainExercise
import com.example.gymguru.domain.model.DomainExerciseType
import dagger.Reusable
import javax.inject.Inject
import com.example.gymguru.data.model.ExerciseType as RoomExerciseType

@Reusable
class DomainExerciseMapper @Inject constructor() {
    operator fun invoke(exerciseEntity: ExerciseEntity): DomainExercise = with(exerciseEntity) {
        DomainExercise(
            id = id,
            name = name,
            description = description,
            type = fromType(type),
            imageUrl = imageUrl
        )
    }

    private fun fromType(exerciseType: RoomExerciseType?): DomainExerciseType =
        when (exerciseType) {
            ExerciseType.CHEST -> DomainExerciseType.CHEST
            ExerciseType.ARMS -> DomainExerciseType.ARMS
            ExerciseType.SHOULDERS -> DomainExerciseType.SHOULDERS
            ExerciseType.BACK -> DomainExerciseType.BACK
            ExerciseType.ABS -> DomainExerciseType.ABS
            ExerciseType.LEGS -> DomainExerciseType.LEGS
            else -> DomainExerciseType.CHEST
        }
}
