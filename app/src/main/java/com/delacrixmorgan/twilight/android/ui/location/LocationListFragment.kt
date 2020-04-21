package com.delacrixmorgan.twilight.android.ui.location

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.LocationDataController
import com.delacrixmorgan.twilight.android.data.model.FormType
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.service.TimeTickBroadcastReceiver
import com.delacrixmorgan.twilight.android.service.TimeTickListener
import com.delacrixmorgan.twilight.android.ui.BottomNavigationBottomSheetFragment
import com.delacrixmorgan.twilight.android.ui.about.AboutFragment
import com.delacrixmorgan.twilight.android.ui.credit.CreditFragment
import com.delacrixmorgan.twilight.android.ui.form.FormActivity
import kotlinx.android.synthetic.main.fragment_location_list.*
import java.util.*

class LocationListFragment : Fragment(), LocationRecyclerViewAdapter.Listener,
    TimeTickListener, BottomNavigationBottomSheetFragment.Listener {

    companion object {
        private const val REQUEST_ADD_NEW_LOCATION = 1
        fun create() = LocationListFragment()
    }

    private var tickReceiver: TimeTickBroadcastReceiver? = null

    private val adapter = LocationRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.locations = LocationDataController.getLocation().toMutableList()
        recyclerView.adapter = adapter

        bottomAppBar.setNavigationOnClickListener {
            showBottomNavigationView()
        }

        addButton.setOnClickListener {
            launchFormActivity(formType = FormType.Create)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ADD_NEW_LOCATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    adapter.locations = LocationDataController.getLocation().toMutableList()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun showBottomNavigationView() {
        val bottomNavigationBottomSheetFragment = BottomNavigationBottomSheetFragment.create(this)
        bottomNavigationBottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            bottomNavigationBottomSheetFragment.javaClass.simpleName
        )
    }

    private fun launchFormActivity(formType: FormType, location: Location? = null) {
        val intent = FormActivity.create(requireContext(), formType, location)
        startActivityForResult(intent, REQUEST_ADD_NEW_LOCATION)
    }

    private fun launchCreditFragment() {
        val fragment = CreditFragment.create()
        activity?.supportFragmentManager?.commit {
            add(android.R.id.content, fragment, fragment::class.simpleName)
            addToBackStack(fragment::class.simpleName)
        }
    }

    private fun launchAboutFragment() {
        val fragment = AboutFragment.create()
        activity?.supportFragmentManager?.commit {
            add(android.R.id.content, fragment, fragment::class.simpleName)
            addToBackStack(fragment::class.simpleName)
        }
    }

    override fun onResume() {
        super.onResume()
        tickReceiver = TimeTickBroadcastReceiver(this)
        requireContext().registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        if (tickReceiver != null) {
            requireContext().unregisterReceiver(tickReceiver)
        }
    }

    override fun onLocationSelected(location: Location) {
        launchFormActivity(formType = FormType.Edit, location = location)
    }

    override fun onTimeTicked() {
        adapter.date = Date()
        adapter.notifyDataSetChanged()
    }

    override fun onNavigationViewSelected(menuItems: BottomNavigationBottomSheetFragment.MenuItems) {
        when (menuItems) {
            BottomNavigationBottomSheetFragment.MenuItems.Credits -> launchCreditFragment()
            BottomNavigationBottomSheetFragment.MenuItems.About -> launchAboutFragment()
        }
    }
}