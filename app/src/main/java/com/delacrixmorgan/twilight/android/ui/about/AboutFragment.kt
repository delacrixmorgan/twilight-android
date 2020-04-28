package com.delacrixmorgan.twilight.android.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.launchPlayStore
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    companion object {
        fun create() = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        supportButton.setOnClickListener {
            launchPlayStore(view.context.packageName)
        }

        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}