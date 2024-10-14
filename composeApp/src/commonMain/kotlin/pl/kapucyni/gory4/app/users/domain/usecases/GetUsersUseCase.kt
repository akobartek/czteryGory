package pl.kapucyni.gory4.app.users.domain.usecases

import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.domain.usecases.GetListUseCase
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class GetUsersUseCase(private val usersListRepository: UsersListRepository) : GetListUseCase<User> {

    override operator fun invoke() = usersListRepository.getUsers()
}