package com.phucduong.searchgitprofile.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.model.UserProfile
import com.phucduong.searchgitprofile.util.SingleLiveEvent
import kotlinx.coroutines.launch

class UserProfileViewModel @ViewModelInject constructor(private val userRepository: Repository) :
    ViewModel() {
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile>
        get() = _userProfile
    private val _error = SingleLiveEvent<Any>()
    val error: LiveData<Any>
        get() = _error
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getUserProfile(userName: String) {
        _loading.value = true
        viewModelScope.launch {
            when (val userProfile = userRepository.getUserProfile(userName)) {
                is Result.Success -> _userProfile.value = userProfile.data
                else -> _error.value = userProfile
            }
            _loading.value = false
        }
    }
}