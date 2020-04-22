package com.delacrixmorgan.twilight.android.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.ColorController
import com.delacrixmorgan.twilight.android.data.model.Location
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
            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a"))
            val dayString = zonedDateTime.format(DateTimeFormatter.ofPattern("EEE, d MMMM yyyy"))

            headerTimeTextView.text = timeString
            headerDateTextView.text = dayString
            headerRegionTextView.text = "Asia/Kuala Lumpur"

        }
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(location: Location) = with(itemView) {
            val zonedDateTime = location.getCurrentZonedDateTime(date)
            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a"))
            val dayString = zonedDateTime.format(DateTimeFormatter.ofPattern("EEE, d MMMM"))

            val textColor = ColorController.getTextColorTint(context, zonedDateTime)
            val backgroundColor = ColorController.getBackgroundColorTint(context, zonedDateTime)

//            dayTextView.setTextColor(textColor)
            timeTextView.setTextColor(textColor)
//            locationNameTextView.setTextColor(textColor)
            personNameTextView.setTextColor(textColor)
            containerViewGroup.setBackgroundColor(backgroundColor)

            timeTextView.text = timeString
//            dayTextView.text = dayString

            if (!location.personName.isNullOrBlank()) {
                personNameTextView.text = location.personName
            } else {
                personNameTextView.text = location.name
            }

            locationNameTextView.text = location.name
//            personNameTextView.isVisible = !location.personName.isNullOrBlank()

            setOnClickListener {
                listener.onLocationSelected(location)
            }
        }
    }
}