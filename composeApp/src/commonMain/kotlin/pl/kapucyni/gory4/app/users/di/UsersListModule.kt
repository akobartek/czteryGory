package pl.kapucyni.gory4.app.users.di

import org.koin.dsl.module
import pl.kapucyni.gory4.app.users.data.FirebaseUsersListRepository
import pl.kapucyni.gory4.app.users.domain.UsersListRepository
import pl.kapucyni.gory4.app.users.domain.usecases.ChangeUserStatusUseCase
import pl.kapucyni.gory4.app.users.domain.usecases.FilterUsersUseCase
import pl.kapucyni.gory4.app.users.domain.usecases.GetUsersUseCase
import pl.kapucyni.gory4.app.users.presentation.UsersListViewModel

val usersListModule = module {
    single<UsersListRepository> { FirebaseUsersListRepository(get()) }

    factory { GetUsersUseCase(get()) }
    factory { FilterUsersUseCase(get()) }
    factory { ChangeUserStatusUseCase(get()) }

    single { UsersListViewModel(get(), get(), get()) }
}