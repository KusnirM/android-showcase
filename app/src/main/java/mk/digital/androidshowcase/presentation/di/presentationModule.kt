package mk.digital.androidshowcase.presentation.di

import mk.digital.androidshowcase.presentation.component.barcode.CodeGenerator
import mk.digital.androidshowcase.presentation.component.imagepicker.ImagePickerViewModel
import mk.digital.androidshowcase.presentation.screen.calendar.CalendarViewModel
import mk.digital.androidshowcase.presentation.screen.database.DatabaseViewModel
import mk.digital.androidshowcase.presentation.screen.home.HomeViewModel
import mk.digital.androidshowcase.presentation.screen.login.LoginViewModel
import mk.digital.androidshowcase.presentation.screen.networking.NetworkingViewModel
import mk.digital.androidshowcase.presentation.screen.notifications.NotificationsViewModel
import mk.digital.androidshowcase.presentation.screen.platformapis.PlatformApisViewModel
import mk.digital.androidshowcase.presentation.screen.register.RegisterViewModel
import mk.digital.androidshowcase.presentation.screen.scanner.ScannerViewModel
import mk.digital.androidshowcase.presentation.screen.settings.SettingsViewModel
import mk.digital.androidshowcase.presentation.screen.storage.StorageViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    factoryOf(::CodeGenerator)

    viewModelOf(::HomeViewModel)
    viewModelOf(::NetworkingViewModel)
    viewModelOf(::StorageViewModel)
    viewModelOf(::PlatformApisViewModel)
    viewModelOf(::ScannerViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ImagePickerViewModel)
    viewModelOf(::DatabaseViewModel)
    viewModelOf(::CalendarViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
