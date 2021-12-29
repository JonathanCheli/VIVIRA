package com.example.viviraapp_jonathancheli.ui.repos


import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.viviraapp_jonathancheli.data.GitHubSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor (
    private val repository: GitHubSearchRepository
) : ViewModel() {
    private val stream = Channel<String>(Channel.UNLIMITED)

    init {
        stream.trySend(DEFAULT_QUERY)
    }



    val repos = stream.receiveAsFlow().debounce(500).map {
        if(it.isEmpty()) DEFAULT_QUERY else it }
        .distinctUntilChanged()
        .asLiveData()
        .switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        if(query.isNotEmpty())
        stream.trySend(query)
    }

    companion object {
        private const val DEFAULT_QUERY = "language:Kotlin"
    }

}