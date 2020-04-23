package com.delacrixmorgan.twilight.android.ui.location

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        fun onDateTimeColorUpdated(color: Int)
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
            headerRegionTextView.text = zonedDateTime.zone.toString().replace("_", " ")

            ColorController.getColorTintState(context, zonedDateTime).apply {
                backgroundDarkImageView.setColorFilter(colorDark, PorterDuff.Mode.SRC_ATOP)
                backgroundMediumImageView.setColorFilter(colorMedium, PorterDuff.Mode.SRC_ATOP)
                backgroundLightImageView.setColorFilter(colorLight, PorterDuff.Mode.SRC_ATOP)

                headerContainerViewGroup.setBackgroundColor(colorFade)
                headerTimeTextView.setTextColor(colorHint)
                headerDateTextView.setTextColor(colorHint)
                headerRegionTextView.setTextColor(colorHint)

                listener.onDateTimeColorUpdated(colorDark)
            }
        }
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(location: Location) = with(itemView) {
            val zonedDateTime = location.getCurrentZonedDateTime(date)
            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a"))

            val textColor = ColorController.getTextColorTint(context, zonedDateTime)
            val backgroundColor = ColorController.getBackgroundColorTint(context, zonedDateTime)

            timeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorGreyLevel2))
            personNameTextView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            containerViewGroup.setBackgroundColor(
                ContextCompat.getColor(context, R.color.colorBlackLevel2)
            )

            locationNameTextView.setTextColor(textColor)
            locationNameTextView.background.setColorFilter(
                backgroundColor,
                PorterDuff.Mode.SRC_ATOP
            );

            timeTextView.text = timeString

            if (!location.personName.isNullOrBlank()) {
                personNameTextView.text = location.personName
            } else {
                personNameTextView.text = location.name
            }

            locationNameTextView.text = location.name

            setOnClickListener {
                listener.onLocationSelected(location)
            }
        }
    }
}