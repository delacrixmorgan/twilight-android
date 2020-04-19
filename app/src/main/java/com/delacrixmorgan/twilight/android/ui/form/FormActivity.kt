package com.delacrixmorgan.twilight.android.ui.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.delacrixmorgan.twilight.android.data.model.FormType
import com.delacrixmorgan.twilight.android.data.model.Keys
import com.delacrixmorgan.twilight.android.data.model.Location

class FormActivity : AppCompatActivity() {

    companion object {
        fun create(
            context: Context,
            formType: FormType,
            location: Location? = null
        ): Intent {
            return Intent(context, FormActivity::class.java).apply {
                putExtra(Keys.Form.FormType.name, formType)
                putExtra(Keys.Form.LocationUuid.name, location?.uuid)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = FormFragment.create(
            formType = intent.getSerializableExtra(Keys.Form.FormType.name) as FormType,
            locationUuid = intent.getStringExtra(Keys.Form.LocationUuid.name)
        )
        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}