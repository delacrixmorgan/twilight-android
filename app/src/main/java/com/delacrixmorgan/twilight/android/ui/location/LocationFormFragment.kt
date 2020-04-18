package com.delacrixmorgan.twilight.android.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import kotlinx.android.synthetic.main.fragment_location_form.*

class LocationFormFragment : Fragment() {

    companion object {
        fun create() = LocationFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText.doAfterTextChanged {

        }
    }
}