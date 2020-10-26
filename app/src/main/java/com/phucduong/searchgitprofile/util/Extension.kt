package com.phucduong.searchgitprofile.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.phucduong.searchgitprofile.R
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile
import com.phucduong.searchgitprofile.data.remote.model.SearchListResponse
import com.phucduong.searchgitprofile.data.remote.model.UserProfileResponse

fun SearchListResponse.mapToListUser(query: String): List<User> {
    val listResult = ArrayList<User>()
    for (item in items) {
        val user = User(
            item.login, item.avatar_url, item.id, item.url, item.type,
            item.score, query
        )
        listResult.add(user)
    }
    return listResult
}

fun UserProfileResponse.mapToUserProfile(): UserProfile {
    return UserProfile(
        followers,
        avatar_url,
        following,
        name ?: "",
        company ?: "",
        location ?: "",
        login ?: "",
        email ?: ""
    )
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) =
    beginTransaction().apply(action).commit()

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.handleError(error: Any) {
    when (error) {
        is Result.Error -> activity?.showToast(
            error.errorResponse?.message ?: getString(R.string.something_went_wrong)
        )
        is Result.NetWorkError -> activity?.showToast(getString(R.string.network_error))
        is Result.UnKnowError -> activity?.showToast(getString(R.string.something_went_wrong))
    }
}



