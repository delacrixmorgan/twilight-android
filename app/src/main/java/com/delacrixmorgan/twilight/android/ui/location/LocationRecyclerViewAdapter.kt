package com.delacrixmorgan.twilight.android.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.toZonedDateTime
import kotlinx.android.synthetic.main.cell_location_list.view.*
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class LocationRecyclerViewAdapter(
    val listener: Listener
) : RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder>() {
    interface Listener {
        fun onLocationSelected(location: Location)
    }

    var date = Date()

    var locations: MutableList<Location> = mutableListOf()
        set(value) {
            val oldList = field
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].uuid == value[newItemPosition].uuid
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ) = oldList[oldItemPosition] == value[newItemPosition]

                override fun getOldListSize() = oldList.size

                override fun getNewListSize() = value.size

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) = true
            }, true)

            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_location_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount() = locations.size

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(location: Location) = with(itemView) {
            val zoneId = ZoneId.of(location.zone?.timeZoneId)
            val zonedDateTime = ZonedDateTime.ofInstant(date.toZonedDateTime().toInstant(), zoneId)

            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a"))
            val dayString = zonedDateTime.format(DateTimeFormatter.ofPattern("EEEE, d MMMM"))

            timeTextView.text = timeString
            dayTextView.text = dayString

            personNameTextView.text = location.personName
            locationNameTextView.text = location.name

            setOnClickListener {
                listener.onLocationSelected(location)
            }
        }
    }
}