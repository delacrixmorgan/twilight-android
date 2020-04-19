package com.delacrixmorgan.twilight.android.ui.form

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.LocationDataController
import com.delacrixmorgan.twilight.android.data.dao.LocationDataDao
import com.delacrixmorgan.twilight.android.data.dao.LocationDatabase
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.ui.zone.ZoneListFragment
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.launch

class FormFragment : Fragment(), ZoneListFragment.Listener {

    companion object {
        fun create() = FormFragment()
    }

    data class LocationParams(
        var zone: Zone? = null,
        var name: String? = null,
        var personName: String? = null
    )

    private val params = LocationParams()
    private var locationDataDao: LocationDataDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (params.zone == null) {
            launchZoneListFragment()
        }

        locationDataDao = LocationDatabase.getInstance(requireContext())?.locationDataDao()

        searchCardView.setOnClickListener {
            updateViews()
            launchZoneListFragment()
        }

        locationNameEditText.doAfterTextChanged {
            params.name = it?.toString()
        }

        doneButton.setOnClickListener {
            updateViews()
            addLocation()
        }
    }

    private fun addLocation() {
        val name = if (!params.name.isNullOrBlank()) {
            params.name
        } else {
            params.zone?.name
        } ?: "Location"

        val location = Location(
            timeZoneId = requireNotNull(params.zone?.timeZoneId),
            name = name,
            personName = params.personName
        )

        lifecycleScope.launch {
            locationDataDao?.insertLocation(location)
            LocationDataController.locations.add(location)

            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }

    private fun launchZoneListFragment() {
        val fragment = ZoneListFragment.create(this)
        activity?.supportFragmentManager?.commit {
            add(android.R.id.content, fragment, fragment::class.simpleName)
            addToBackStack(fragment::class.simpleName)
        }
    }

    private fun updateViews() {
        doneButton.isEnabled = params.zone != null
    }

    override fun onZoneSelected(zone: Zone) {
        params.zone = zone
        searchEditText.setText("${zone.keywords[0]}/${zone.keywords[zone.keywords.size - 1]}")
        locationNameEditText.setText(zone.name)
        locationNameEditText.setSelection(zone.name.length)
        updateViews()
    }
}