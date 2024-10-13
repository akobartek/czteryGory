package pl.kapucyni.gory4.app.auth.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kapucyni.gory4.app.auth.data.FirebaseAuthRepository
import pl.kapucyni.gory4.app.auth.domain.AuthRepository
import pl.kapucyni.gory4.app.auth.presentation.AuthViewModel

val authModule = module {
    factory<AuthRepository> { FirebaseAuthRepository(get(), get()) }

    viewModel { AuthViewModel(get(), get()) }
}