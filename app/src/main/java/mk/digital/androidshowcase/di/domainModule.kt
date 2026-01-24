package mk.digital.androidshowcase.di

import mk.digital.androidshowcase.domain.useCase.GetUsersUseCase
import mk.digital.androidshowcase.domain.useCase.TrackButtonClickUseCase
import mk.digital.androidshowcase.domain.useCase.auth.CheckEmailExistsUseCase
import mk.digital.androidshowcase.domain.useCase.auth.RegisterUserUseCase
import mk.digital.androidshowcase.domain.useCase.biometric.AuthenticateWithBiometricUseCase
import mk.digital.androidshowcase.domain.useCase.biometric.IsBiometricEnabledUseCase
import mk.digital.androidshowcase.domain.useCase.calendar.GetTodayDateUseCase
import mk.digital.androidshowcase.domain.useCase.analytics.TrackScreenUseCase
import mk.digital.androidshowcase.domain.useCase.notes.DeleteAllNotesUseCase
import mk.digital.androidshowcase.domain.useCase.notes.DeleteNoteUseCase
import mk.digital.androidshowcase.domain.useCase.notes.InsertNoteUseCase
import mk.digital.androidshowcase.domain.useCase.notes.ObserveNotesUseCase
import mk.digital.androidshowcase.domain.useCase.notes.SearchNotesUseCase
import mk.digital.androidshowcase.domain.useCase.notes.UpdateNoteUseCase
import mk.digital.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import mk.digital.androidshowcase.domain.useCase.settings.SetThemeModeUseCase
import mk.digital.androidshowcase.domain.useCase.storage.ClearCacheUseCase
import mk.digital.androidshowcase.domain.useCase.storage.LoadStorageDataUseCase
import mk.digital.androidshowcase.domain.useCase.storage.ObserveStorageDataUseCase
import mk.digital.androidshowcase.domain.useCase.storage.SetPersistentCounterUseCase
import mk.digital.androidshowcase.domain.useCase.storage.SetSessionCounterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::TrackScreenUseCase)
    singleOf(::TrackButtonClickUseCase)
    singleOf(::GetUsersUseCase)
    singleOf(::LoadStorageDataUseCase)
    singleOf(::ObserveStorageDataUseCase)
    singleOf(::SetSessionCounterUseCase)
    singleOf(::SetPersistentCounterUseCase)
    singleOf(::ClearCacheUseCase)
    singleOf(::GetThemeModeUseCase)
    singleOf(::SetThemeModeUseCase)
    singleOf(::ObserveNotesUseCase)
    singleOf(::SearchNotesUseCase)
    singleOf(::InsertNoteUseCase)
    singleOf(::UpdateNoteUseCase)
    singleOf(::DeleteNoteUseCase)
    singleOf(::DeleteAllNotesUseCase)
    singleOf(::GetTodayDateUseCase)
    singleOf(::IsBiometricEnabledUseCase)
    singleOf(::AuthenticateWithBiometricUseCase)
    singleOf(::CheckEmailExistsUseCase)
    singleOf(::RegisterUserUseCase)
}
