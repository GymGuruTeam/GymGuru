package com.example.gymguru.domain.model

import java.time.LocalDate

data class UserWeight(
    val id: Int,
    val weight: Float?,
    val date: LocalDate?
)
