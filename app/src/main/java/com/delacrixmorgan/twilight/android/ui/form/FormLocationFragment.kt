package com.delacrixmorgan.twilight.android.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import kotlinx.android.synthetic.main.fragment_form_location.*

class FormLocationFragment : Fragment() {

    companion object {
        fun create() =
            FormLocationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchTextView.doAfterTextChanged {

        }
    }
}