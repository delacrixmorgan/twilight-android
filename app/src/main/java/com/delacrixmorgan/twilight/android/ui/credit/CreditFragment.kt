package com.delacrixmorgan.twilight.android.ui.credit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.launchWebsite

class CreditFragment : Fragment() {

    companion object {
        fun create() = CreditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        backButton.setOnClickListener {
//            activity?.supportFragmentManager?.popBackStack()
//        }
//
//        spartanImageView.setOnClickListener {
//            launchWebsite("https://github.com/theleagueof/league-spartan")
//        }
//
//        threeTenABPImageView.setOnClickListener {
//            launchWebsite("https://github.com/JakeWharton/ThreeTenABP")
//        }
//
//        delesignImageView.setOnClickListener {
//            launchWebsite("https://iconscout.com/illustrations/man-enjoying-vacation")
//        }
    }
}