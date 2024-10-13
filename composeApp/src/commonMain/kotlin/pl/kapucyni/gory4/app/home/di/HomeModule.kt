package pl.kapucyni.gory4.app.home.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kapucyni.gory4.app.home.presentation.HomeViewModel

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}