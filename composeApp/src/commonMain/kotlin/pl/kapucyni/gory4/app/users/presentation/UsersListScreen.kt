package pl.kapucyni.gory4.app.users.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.presentation.composables.ListScreenLayout
import pl.kapucyni.gory4.app.users.presentation.composables.UserItemLayout

@Composable
fun UsersListScreen(
    navigateUp: () -> Unit,
    viewModel: UsersListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    ListScreenLayout(
        state = state,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onBackPressed = navigateUp,
        itemLayout = { item ->
            if (item is User)
                UserItemLayout(
                    user = item,
                    onTypeChange = viewModel::onUserTypeChange,
                )
        },
    )
}