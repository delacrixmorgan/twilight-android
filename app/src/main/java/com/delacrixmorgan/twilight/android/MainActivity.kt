package com.delacrixmorgan.twilight.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)

        val fragment = LaunchFragment.create()
        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment.javaClass.simpleName)
        }
    }
}