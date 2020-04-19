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
            val locations = locationDataDao?.getLocations()
            if (!locations.isNullOrEmpty()) {
                LocationDataController.locations = locations.toMutableList()
                launchLocationListFragment()
            } else {
                fetchLocations()
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
                    personName = "Yang",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "Columbus").first().uuid
                ),
                Location(
                    name = "New Jersey",
                    personName = "Ze-Xin, Ze-Wen",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "New Jersey").first().uuid
                ),
                Location(
                    name = "San Francisco",
                    personName = "Michael",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "San Francisco")
                        .first().uuid
                ),
                Location(
                    name = "London",
                    personName = "Ian",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "London").first().uuid
                ),
                Location(
                    name = "Perth",
                    personName = "Teck Hun",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "Perth").first().uuid
                ),
                Location(
                    name = "Tasmania",
                    personName = "Grace",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "Tasmania").first().uuid
                ),
                Location(
                    name = "Melbourne",
                    personName = "Maggie",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "Melbourne").first().uuid
                ),
                Location(
                    name = "Auckland",
                    personName = "Thomas",
                    zoneUuid = ZoneDataController.getZone(searchQuery = "Auckland").first().uuid
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