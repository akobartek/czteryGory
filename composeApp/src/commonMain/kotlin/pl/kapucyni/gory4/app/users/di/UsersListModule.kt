package pl.kapucyni.gory4.app.users.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kapucyni.gory4.app.users.data.FirestoreUsersListRepository
import pl.kapucyni.gory4.app.users.domain.UsersListRepository
import pl.kapucyni.gory4.app.users.presentation.UsersListViewModel

val usersListModule = module {
    single<UsersListRepository> { FirestoreUsersListRepository(get()) }

    viewModel { UsersListViewModel(get()) }
}