package com.delacrixmorgan.twilight.android.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.DateTimeController
import com.delacrixmorgan.twilight.android.data.model.Location
import com.delacrixmorgan.twilight.android.getZoneCity
import com.delacrixmorgan.twilight.android.toZonedDateTime
import kotlinx.android.synthetic.main.cell_location_header.view.*
import kotlinx.android.synthetic.main.cell_location_list.view.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class LocationRecyclerViewAdapter(
    val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            field.sortByDescending { it.getCurrentZonedDateTime(date).offset }
            diffResult.dispatchUpdatesTo(this)
        }

    enum class ViewHolderType {
        Header,
        Item
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewHolderType.Header.ordinal
            else -> ViewHolderType.Item.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewHolderType.Header.ordinal -> {
                HeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_location_header, parent, false)
                )
            }
            else -> {
                LocationViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_location_list, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(date)
            is LocationViewHolder -> holder.bind(locations[position - 1])
        }
    }

    override fun getItemCount() = locations.size + 1

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(date: Date) = with(itemView) {
            val zonedDateTime = date.toZonedDateTime()
            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm"))
            val periodString = zonedDateTime.format(DateTimeFormatter.ofPattern("a"))
            val greetingString = DateTimeController.getGreetingText(
                context, zonedDateTime
            )

            val textColor = DateTimeController.getTextColorTint(context, zonedDateTime)
            val backgroundColor = DateTimeController.getBackgroundColorTint(context, zonedDateTime)
            val backgroundBlendColor = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                backgroundColor,
                BlendModeCompat.SRC_ATOP
            )

            headerTimeTextView.text = timeString
            headerPeriodTextView.text = periodString
            headerGreetingsTextView.text = greetingString
            headerLocationTextView.text = zonedDateTime.getZoneCity()

            headerLocationTextView.setTextColor(textColor)
            headerLocationTextView.background.colorFilter = backgroundBlendColor
        }
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(location: Location) = with(itemView) {
            val zonedDateTime = location.getCurrentZonedDateTime(date)
            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm"))
            val periodString = zonedDateTime.format(DateTimeFormatter.ofPattern("a"))
            val textColor = DateTimeController.getTextColorTint(context, zonedDateTime)
            val backgroundColor = DateTimeController.getBackgroundColorTint(context, zonedDateTime)
            val backgroundBlendColor = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                backgroundColor,
                BlendModeCompat.SRC_ATOP
            )

            locationNameTextView.setTextColor(textColor)
            locationNameTextView.background.colorFilter = backgroundBlendColor

            timeTextView.text = timeString
            periodTextView.text = periodString
            statusTextView.text = DateTimeController.getStatus(zonedDateTime)
            descriptionTextView.text = if (!location.description.isNullOrBlank()) {
                location.description
            } else {
                location.name
            }

            locationNameTextView.text = location.name

            setOnClickListener {
                listener.onLocationSelected(location)
            }
        }
    }
}