package mk.digital.androidshowcase.domain.useCase.calendar

import kotlinx.datetime.LocalDate
import mk.digital.androidshowcase.domain.repository.DateRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class GetTodayDateUseCase(
    private val dateRepository: DateRepository,
) : UseCase<None, LocalDate>() {
    override suspend fun run(params: None): LocalDate = dateRepository.today()
}
