package com.phucduong.searchgitprofile.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.util.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SearchUserViewModel @ViewModelInject constructor(
    private val userRepository: Repository
) : ViewModel() {
    // Two-way databinding, exposing MutableLiveData
    val searchKeyWord = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private var searchJob: Job? = null
    private val delaySearch: Long = 500L
    private var pageNumber = 1
    private val currentList = ArrayList<User>()
    private val _listUserInfo: MutableLiveData<ArrayList<User>> = MutableLiveData(ArrayList())
    val listUser: LiveData<ArrayList<User>>
        get() = _listUserInfo

    private val _error = SingleLiveEvent<Any>()
    val error: LiveData<Any>
        get() = _error

    private val mediator = MediatorLiveData<String>().apply {
        addSource(searchKeyWord) { value ->
            setValue(value)
            loading.value = true
            pageNumber = 1
            searchUserWithQuery()
        }
    }.also { it.observeForever() { /* empty */ } }

    fun searchUserWithQuery() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(delaySearch)
            if (pageNumber == 1) {
                currentList.clear()
            }
            val result = userRepository.searchUser(
                searchKeyWord.value?.trim()?.toLowerCase(
                    Locale.getDefault()
                ) ?: "", pageNumber++
            )
            loading.value = false

            if (result is Result.Success) {
                currentList.addAll(result.data)
                _listUserInfo.value = currentList
            } else _error.value = result
        }
    }
}
