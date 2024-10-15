package pl.kapucyni.gory4.app.directors.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import pl.kapucyni.gory4.app.common.presentation.composables.ListScreenLayout
import pl.kapucyni.gory4.app.directors.domain.model.Director
import pl.kapucyni.gory4.app.directors.presentation.composables.DirectorListItemLayout

@Composable
fun DirectorsListScreen(
    navigateUp: () -> Unit,
    openDetails: (Director?) -> Unit,
    isAdmin: Boolean,
    viewModel: DirectorsListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            if (isAdmin)
                FloatingActionButton(onClick = {openDetails(null)}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
        }
    ) {
        ListScreenLayout(
            state = state,
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::updateSearchQuery,
            onBackPressed = navigateUp,
            itemLayout = { item ->
                if (item is Director) {
                    DirectorListItemLayout(
                        director = item,
                        onClick = openDetails,
                        isAdmin = isAdmin,
                    )
                }
            },
        )
    }
}