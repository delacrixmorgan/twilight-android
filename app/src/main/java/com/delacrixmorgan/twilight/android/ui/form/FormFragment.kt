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
import kotlinx.android.synthetic.main.layout_navigation_bar.view.*
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

    private var location: Location? = null
    private var locationDataDao: LocationDataDao? = null

    private lateinit var formType: FormType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationDataDao = LocationDatabase.getInstance(requireContext())?.locationDataDao()
    }

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
                navigationBar.actionButton.text = getString(R.string.done)
                navigationBar.titleTextView.text = "Create a Location"
                location = Location()
            }
            FormType.Edit -> {
                location = LocationDataController.getLocationById(
                    requireNotNull(arguments?.getString(Keys.Form.LocationUuid.name))
                )
                deleteButton.isVisible = true
                navigationBar.titleTextView.text = "Edit Location"
                navigationBar.actionButton.text = getString(R.string.save)

                updateViews()
            }
        }

        if (location?.zone == null) {
            launchZoneListFragment()
        }

        locationNameEditText.doAfterTextChanged {
            location?.name = it.toString()
        }

        personNameEditText.doAfterTextChanged {
            location?.personName = it.toString()
        }

        searchCardView.setOnClickListener {
            launchZoneListFragment()
        }

        deleteButton.setOnClickListener {
            deleteLocation()
        }

        navigationBar.backButton.setOnClickListener {
            activity?.finish()
        }

        navigationBar.actionButton.setOnClickListener {
            location?.name = if (!location?.name.isNullOrBlank()) {
                location?.name
            } else {
                location?.zone?.name
            } ?: "Location"
            updateLocation()
        }
    }

    private fun updateLocation() {
        lifecycleScope.launch {
            when (formType) {
                FormType.Create -> {
                    locationDataDao?.insertLocation(requireNotNull(location))
                    LocationDataController.locations.add(requireNotNull(location))
                }
                FormType.Edit -> {
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

    private fun updateViews() {
        navigationBar.actionButton.isEnabled = location?.zone != null
        personNameEditText.setText(location?.personName)
        locationNameEditText.setText(location?.zone?.name)
        searchTextView.text = location?.zone?.regionZoneName
        locationNameEditText.setSelection(location?.zone?.name?.length ?: 0)
    }

    override fun onZoneSelected(zone: Zone) {
        location?.timeZoneId = zone.timeZoneId
        updateViews()
    }
}