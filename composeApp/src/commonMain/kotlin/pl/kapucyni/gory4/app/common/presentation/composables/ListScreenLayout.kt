package pl.kapucyni.gory4.app.common.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import pl.kapucyni.gory4.app.common.domain.model.ListItem
import pl.kapucyni.gory4.app.common.presentation.ListScreenState
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreenLayout(
    state: ListScreenState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    itemLayout: @Composable LazyItemScope.(ListItem) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val searchBarHeightDp = 56.dp + 12.dp
    val searchBarHeightPx = with(LocalDensity.current) { searchBarHeightDp.roundToPx().toFloat() }
    var searchBarOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = searchBarOffsetHeightPx + delta
                searchBarOffsetHeightPx = newOffset.coerceIn(-searchBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus(true) }
    ) {
        stickyHeader {
            ListSearchBar(
                query = searchQuery,
                onQueryChanged = onSearchQueryChange,
                onBackPressed = onBackPressed,
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = searchBarOffsetHeightPx.roundToInt())
                    }
            )
        }

        when (state) {
            is ListScreenState.Loading -> item { LoadingBox() }
            is ListScreenState.Success -> {
                items(items = state.data, key = { it.itemId ?: state.data.indexOf(it) }) { item ->
                    itemLayout(item)
                }

                if (state.data.isEmpty())
                    item {
                        EmptyListInfo()
                    }
            }
        }
    }
}