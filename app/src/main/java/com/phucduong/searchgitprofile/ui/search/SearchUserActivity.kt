package com.phucduong.searchgitprofile.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phucduong.searchgitprofile.R
import com.phucduong.searchgitprofile.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        replaceFragment(SearchWeatherFragment.newInstance(), R.id.container)
    }
}
