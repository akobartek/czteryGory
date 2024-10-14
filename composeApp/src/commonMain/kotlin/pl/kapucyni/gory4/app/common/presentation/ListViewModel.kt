package pl.kapucyni.gory4.app.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.domain.model.ListItem
import pl.kapucyni.gory4.app.common.domain.usecases.FilterListUseCase
import pl.kapucyni.gory4.app.common.domain.usecases.GetListUseCase

@OptIn(FlowPreview::class)
abstract class ListViewModel<T: ListItem>(
    private val getListUseCase: GetListUseCase<T>,
    private val filterListUseCase: FilterListUseCase<T>
): ViewModel() {

    private var currentList = listOf<T>()
    protected val mutableState = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val state: StateFlow<ListScreenState> = mutableState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getListUseCase()
                    .shareIn(this, SharingStarted.Lazily, 1)
                    .collect { users ->
                        currentList = users
                        filterUsers()
                    }
            } catch (_: Exception) {
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery
                .onEach { mutableState.update { ListScreenState.Loading } }
                .debounce { query ->
                    if (query.isNotBlank()) 666L
                    else 0L
                }
                .collect { filterUsers() }
        }
    }

    private suspend fun filterUsers() {
        val filteredList = filterListUseCase(currentList, searchQuery.value)
        mutableState.update {
            ListScreenState.Success(filteredList, false)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }
}