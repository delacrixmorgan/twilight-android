package com.delacrixmorgan.twilight.android.ui.zone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.hideKeyboard
import kotlinx.android.synthetic.main.fragment_zone_list.*

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
        searchEditText.doAfterTextChanged {
            adapter.zones = ZoneDataController.getZone(
                searchQuery = it?.toString()
            ).toMutableList()
        }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
            }
            false
        }

        adapter.zones = ZoneDataController.getZone().toMutableList()
        recyclerView.adapter = adapter
    }

    override fun onZoneSelected(zone: Zone) {
        listener.onZoneSelected(zone)
        activity?.supportFragmentManager?.popBackStack()
    }
}