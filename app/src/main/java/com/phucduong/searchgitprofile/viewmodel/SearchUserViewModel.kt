package com.phucduong.searchgitprofile.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.local.User
import kotlinx.coroutines.launch
import java.util.*

class SearchUserViewModel @ViewModelInject constructor(
    private val UserRepository: Repository
) : ViewModel() {
    // Two-way databinding, exposing MutableLiveData
    val searchKeyWord = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val errorText = MutableLiveData<String>()

    private val _listUserInfo = MutableLiveData<List<User>>().apply { value = emptyList() }
    val listUser: LiveData<List<User>>
        get() = _listUserInfo

    //Used below code for observe searchKeyword value change
    private val mediator = MediatorLiveData<String>().apply {
        addSource(searchKeyWord) { value ->
            setValue(value)
            //getUserWithCurrentKeyword()
        }
    }.also { it.observeForever() { /* empty */ } }

    fun getUserWithCurrentKeyword() {
        loading.value = true
        viewModelScope.launch {
            val result = UserRepository.getUserListByKeyword(
                searchKeyWord.value?.trim()?.toLowerCase(
                    Locale.getDefault()
                ) ?: ""
            )
            loading.value = false
            when (result) {
                is com.phucduong.searchgitprofile.data.Result.Success -> bindData(result.data, "")
                is com.phucduong.searchgitprofile.data.Result.Error -> bindData(emptyList(), result.errorResponse?.message)
                is com.phucduong.searchgitprofile.data.Result.NetWorkError -> bindData(emptyList(), result.msg)
                is com.phucduong.searchgitprofile.data.Result.UnKnowError -> bindData(emptyList(), result.msg)
            }
        }
    }

    private fun bindData(UserList: List<User>, errorMsg: String?) {
        _listUserInfo.value = UserList
        errorText.value = errorMsg
    }

    fun checkRefreshCached() {
        viewModelScope.launch {
            UserRepository.checkRefreshCached()
        }
    }
}
