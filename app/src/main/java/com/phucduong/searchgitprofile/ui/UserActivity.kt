package com.phucduong.searchgitprofile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phucduong.searchgitprofile.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
