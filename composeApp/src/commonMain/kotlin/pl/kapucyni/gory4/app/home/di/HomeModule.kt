package pl.kapucyni.gory4.app.home.di

import org.koin.dsl.module
import pl.kapucyni.gory4.app.home.presentation.HomeViewModel

val homeModule = module {
    single { HomeViewModel(get()) }
}