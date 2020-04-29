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
import kotlinx.android.synthetic.main.fragment_launch.*
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
            buildNumberTextView.text = "v${BuildConfig.VERSION_NAME}#${BuildConfig.VERSION_CODE}"

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
                    name = "Berlin",
                    description = "Aerith",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Berlin")
                        .first().timeZoneId
                ),
                Location(
                    name = "Ivory Coast",
                    description = "Barret",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Abidjan")
                        .first().timeZoneId
                ),
                Location(
                    name = "Uluru",
                    description = "Nanaki",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Darwin")
                        .first().timeZoneId
                ),
                Location(
                    name = "London",
                    description = "Cait Sith",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "London")
                        .first().timeZoneId
                ),
                Location(
                    name = "Transylvania",
                    description = "Vincent",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Bucharest")
                        .first().timeZoneId
                ),
                Location(
                    name = "Highwind",
                    description = "Cid",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Amsterdam")
                        .first().timeZoneId
                ),
                Location(
                    name = "Kyoto",
                    description = "Cloud",
                    timeZoneId = ZoneDataController.getZone(searchQuery = "Tokyo")
                        .first().timeZoneId
                ),
                Location(
                    name = "Auckland",
                    description = "Tifa",
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