package com.delacrixmorgan.twilight.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.twilight.android.data.controller.LocationDataController
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import com.delacrixmorgan.twilight.android.data.dao.LocationDataDao
import com.delacrixmorgan.twilight.android.data.dao.LocationDatabase
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.ui.location.LocationListFragment
import kotlinx.coroutines.launch

class LaunchFragment : Fragment() {

    companion object {
        fun create() = LaunchFragment()
    }

    private var locationDataDao: LocationDataDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            locationDataDao = LocationDatabase.getInstance(requireContext())?.locationDataDao()

            if (BuildConfig.DEBUG) {
                val locations = locationDataDao?.getLocations()
                if (!locations.isNullOrEmpty()) {
                    LocationDataController.locations = locations.toMutableList()
                    launchLocationListFragment()
                } else {
                    fetchLocations()
                }
            } else {
                val locations = locationDataDao?.getLocations()
                LocationDataController.locations = locations?.toMutableList() ?: mutableListOf()
                launchLocationListFragment()
            }
        }
    }

    private fun launchLocationListFragment() {
        val fragment = LocationListFragment.create()
        activity?.supportFragmentManager?.commit {
            replace(android.R.id.content, fragment, fragment.javaClass.simpleName)
        }
    }

    private fun fetchLocations() {
        val locations = mutableListOf<Location>()
        locations.addAll(
            listOf(
                Location(
                    name = "Columbus",
                    description = "Yang",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Columbus")
                        .first().timeZoneId
                ),
                Location(
                    name = "New Jersey",
                    description = "Ze-Xin, Ze-Wen",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "New Jersey")
                        .first().timeZoneId
                ),
                Location(
                    name = "San Francisco",
                    description = "Michael",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "San Francisco")
                        .first().timeZoneId
                ),
                Location(
                    name = "London",
                    description = "Ian",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "London")
                        .first().timeZoneId
                ),
                Location(
                    name = "Perth",
                    description = "Teck Hun",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Perth")
                        .first().timeZoneId
                ),
                Location(
                    name = "Tasmania",
                    description = "Grace",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Tasmania")
                        .first().timeZoneId
                ),
                Location(
                    name = "Melbourne",
                    description = "Maggie",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Melbourne")
                        .first().timeZoneId
                ),
                Location(
                    name = "Auckland",
                    description = "Thomas",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Auckland")
                        .first().timeZoneId
                )
            )
        )
        addLocationDatabase(locations)
    }

    private fun addLocationDatabase(locations: List<Location>) {
        lifecycleScope.launch {
            locations.forEach { locationDataDao?.insertLocation(it) }
            LocationDataController.locations = locations.toMutableList()
            launchLocationListFragment()
        }
    }

    override fun onDestroy() {
        LocationDatabase.destroyInstance()
        super.onDestroy()
    }
}