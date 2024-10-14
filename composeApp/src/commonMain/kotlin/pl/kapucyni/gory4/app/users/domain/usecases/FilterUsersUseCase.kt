package pl.kapucyni.gory4.app.users.domain.usecases

import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.domain.usecases.FilterListUseCase
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class FilterUsersUseCase(private val usersListRepository: UsersListRepository) :
    FilterListUseCase<User> {

    override suspend operator fun invoke(list: List<User>, query: String) =
        usersListRepository.filterUsers(list, query)
}