package pl.kapucyni.gory4.app.directors.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kapucyni.gory4.app.directors.data.FirebaseDirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.DirectorsRepository
import pl.kapucyni.gory4.app.directors.domain.usecases.FilterDirectorsUseCase
import pl.kapucyni.gory4.app.directors.domain.usecases.GetDirectorsUseCase
import pl.kapucyni.gory4.app.directors.domain.usecases.SaveDirectorUseCase
import pl.kapucyni.gory4.app.directors.presentation.DirectorsListViewModel

val directorsModule = module {
    single<DirectorsRepository> { FirebaseDirectorsRepository(get()) }

    factory { GetDirectorsUseCase(get()) }
    factory { FilterDirectorsUseCase(get()) }
    factory { SaveDirectorUseCase(get()) }

    viewModel { DirectorsListViewModel(get(), get()) }
}