package com.phucduong.searchgitprofile.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.phucduong.searchgitprofile.data.local.User
import com.phucduong.searchgitprofile.data.remote.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun AppCompatActivity.replaceFragment(fragment: Fragment, framId: Int) =
    supportFragmentManager.transact {
        replace(framId, fragment)
    }

fun WeatherResponse.mapToListWeather(keyword: String): List<User> {
    val listResult = ArrayList<User>()
    for (info in list) {
        val weather = User(
            city.name, info.dt.getDate(), info.humidity, info.pressure, info.temp.day.toInt(),
            info.weather[0].description, keyword
        )
        listResult.add(weather)
    }
    return listResult
}

fun Long.getDate(dateFormat: String = "EEE, d MMM yyyy"): String {
    val date = Date(this * 1000)
    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) =
    beginTransaction().apply(action).commit()

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


