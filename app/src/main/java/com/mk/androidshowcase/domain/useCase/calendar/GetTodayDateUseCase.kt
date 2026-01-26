package com.mk.androidshowcase.domain.useCase.calendar

import kotlinx.datetime.LocalDate
import com.mk.androidshowcase.domain.repository.DateRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetTodayDateUseCase @Inject constructor(
    private val dateRepository: DateRepository,
) : UseCase<None, LocalDate>() {
    override suspend fun run(params: None): LocalDate = dateRepository.today()
}
