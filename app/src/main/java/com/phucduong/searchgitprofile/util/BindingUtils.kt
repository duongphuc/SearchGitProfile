package com.phucduong.searchgitprofile.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.phucduong.searchgitprofile.adapter.UserAdapter
import com.phucduong.searchgitprofile.data.local.User


object BindingUtils {
    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: ListView, list: List<User>) {
        with(listView.adapter as UserAdapter) {
            replaceData(list)
            if (list.isNotEmpty()) listView.context.hideKeyboard(listView)
        }
    }

    @JvmStatic
    @BindingAdapter("onEditorEnterAction")
    fun EditText.onEditorEnterAction(performSearch: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            true
        }
    }
}