package com.delacrixmorgan.twilight.android.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import kotlinx.android.synthetic.main.fragment_location_list.*

class LocationListFragment : Fragment(), LocationRecyclerViewAdapter.Listener {

    companion object {
        fun create() = LocationListFragment()
    }

    private val adapter: LocationRecyclerViewAdapter by lazy {
        LocationRecyclerViewAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        adapter.locations = locations
        recyclerView.adapter = adapter
    }

    override fun onLocationSelected(location: Location) {

    }
}