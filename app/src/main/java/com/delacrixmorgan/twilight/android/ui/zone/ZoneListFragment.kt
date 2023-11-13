package com.delacrixmorgan.twilight.android.ui.zone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.hideKeyboard

class ZoneListFragment : Fragment(), ZoneRecyclerViewAdapter.Listener {

    companion object {
        fun create(listener: Listener) = ZoneListFragment().apply {
            this.listener = listener
        }
    }

    interface Listener {
        fun onZoneSelected(zone: Zone)
    }

    private lateinit var listener: Listener

    private val adapter: ZoneRecyclerViewAdapter by lazy {
        ZoneRecyclerViewAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zone_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                hideKeyboard()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.zones = ZoneDataController.getZone(
//                    searchQuery = newText
//                ).toMutableList()
//                recyclerView.scrollToPosition(0)
//                return true
//            }
//        })
//
//        backButton.setOnClickListener {
//            activity?.supportFragmentManager?.popBackStack()
//        }
//
//        adapter.zones = ZoneDataController.getZone().toMutableList()
//        recyclerView.adapter = adapter
    }

    override fun onZoneSelected(zone: Zone) {
        listener.onZoneSelected(zone)
        activity?.supportFragmentManager?.popBackStack()
    }
}