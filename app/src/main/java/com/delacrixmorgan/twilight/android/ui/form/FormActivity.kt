package com.delacrixmorgan.twilight.android.ui.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class FormActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent {
            val intent = Intent(context, FormActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = FormFragment.create()

        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}