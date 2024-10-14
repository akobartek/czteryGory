package pl.kapucyni.gory4.app.users.domain.usecases

import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class ChangeUserStatusUseCase(private val usersListRepository: UsersListRepository) {
    suspend operator fun invoke(user: User) = usersListRepository.changeUserStatus(user)
}