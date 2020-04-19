package com.delacrixmorgan.twilight.android.ui.form

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.LocationDataController
import com.delacrixmorgan.twilight.android.data.dao.LocationDataDao
import com.delacrixmorgan.twilight.android.data.dao.LocationDatabase
import com.delacrixmorgan.twilight.android.data.model.FormType
import com.delacrixmorgan.twilight.android.data.model.Keys
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.ui.zone.ZoneListFragment
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.launch

class FormFragment : Fragment(), ZoneListFragment.Listener {

    companion object {
        fun create(
            formType: FormType,
            locationUuid: String? = null
        ) = FormFragment().apply {
            arguments = bundleOf(
                Keys.Form.FormType.name to formType,
                Keys.Form.LocationUuid.name to locationUuid
            )
        }
    }

    data class LocationParams(
        var zone: Zone? = null,
        var name: String? = null,
        var personName: String? = null
    )

    private var location: Location? = null
    private val params = LocationParams()
    private var locationDataDao: LocationDataDao? = null

    private lateinit var formType: FormType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formType = arguments?.getSerializable(Keys.Form.FormType.name) as FormType

        when (formType) {
            FormType.Create -> {
                deleteButton.isVisible = false
                actionButton.text = "Done"
            }
            FormType.Edit -> {
                location = LocationDataController.getLocationById(
                    requireNotNull(arguments?.getString(Keys.Form.LocationUuid.name))
                )

                params.apply {
                    zone = location?.zone
                    name = location?.name
                    personName = location?.personName
                }

                updateViews(location?.zone)

                deleteButton.isVisible = true
                actionButton.text = "Save"
            }
        }

        if (params.zone == null) {
            launchZoneListFragment()
        }

        locationDataDao = LocationDatabase.getInstance(requireContext())?.locationDataDao()

        locationNameEditText.doAfterTextChanged {
            params.name = it?.toString()
        }

        personNameEditText.doAfterTextChanged {
            params.personName = it?.toString()
        }

        searchCardView.setOnClickListener {
            launchZoneListFragment()
        }

        deleteButton.setOnClickListener {
            deleteLocation()
        }

        actionButton.setOnClickListener {
            updateViews()
            updateLocation()
        }
    }

    private fun updateLocation() {
        val name = if (!params.name.isNullOrBlank()) {
            params.name
        } else {
            params.zone?.name
        } ?: "Location"

        val createLocation = Location(
            timeZoneId = requireNotNull(params.zone?.timeZoneId),
            name = name,
            personName = params.personName
        )

        lifecycleScope.launch {
            when (formType) {
                FormType.Create -> {
                    locationDataDao?.insertLocation(createLocation)
                    LocationDataController.locations.add(createLocation)
                }
                FormType.Edit -> {
                    location?.timeZoneId = requireNotNull(params.zone?.timeZoneId)
                    location?.personName = params.personName
                    location?.name = params.name.toString()

                    locationDataDao?.updateLocation(requireNotNull(location))
                    val index = LocationDataController.locations.indexOfFirst {
                        it.uuid == location?.uuid
                    }
                    LocationDataController.locations[index] = requireNotNull(location)
                }
            }
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }

    private fun deleteLocation() {
        lifecycleScope.launch {
            locationDataDao?.deleteLocationById(requireNotNull(location?.uuid))
            LocationDataController.locations.remove(location)

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

    private fun updateViews(zone: Zone? = null) {
        actionButton.isEnabled = params.zone != null

        zone?.let {
            searchTextView.text = "${zone.keywords[0]}/${zone.keywords[zone.keywords.size - 1]}"
            locationNameEditText.setText(zone.name)
            locationNameEditText.setSelection(zone.name.length)
            personNameEditText.setText(location?.personName)
        }
    }

    override fun onZoneSelected(zone: Zone) {
        params.zone = zone
        updateViews(zone)
    }
}