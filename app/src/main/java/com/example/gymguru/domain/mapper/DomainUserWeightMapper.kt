package com.example.gymguru.domain.mapper

import com.example.gymguru.data.model.UserWeightEntity
import com.example.gymguru.domain.formatter.LocalDateFormatter
import com.example.gymguru.domain.model.DomainUserWeight
import dagger.Reusable
import java.time.LocalDate
import javax.inject.Inject

@Reusable
class DomainUserWeightMapper @Inject constructor(
    private val localDateFormatter: LocalDateFormatter
) {
    operator fun invoke(userWeightEntity: UserWeightEntity): DomainUserWeight =
        with(userWeightEntity) {
            DomainUserWeight(
                id = id,
                weight = weight,
                date = localDateFormatter.invoke(date)
            )
        }

    operator fun invoke(domainUserWeight: DomainUserWeight): UserWeightEntity =
        with(domainUserWeight) {
            UserWeightEntity(
                id = id,
                weight = weight,
                date = localDateFormatter.invoke(date ?: LocalDate.now())
            )
        }
}
