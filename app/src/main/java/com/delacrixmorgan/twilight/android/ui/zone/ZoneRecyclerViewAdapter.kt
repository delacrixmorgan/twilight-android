package com.delacrixmorgan.twilight.android.ui.zone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.controller.DateTimeController
import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.toZonedDateTime
import kotlinx.android.synthetic.main.cell_zone_list.view.*
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class ZoneRecyclerViewAdapter(
    val listener: Listener
) : RecyclerView.Adapter<ZoneRecyclerViewAdapter.ZoneViewHolder>() {

    interface Listener {
        fun onZoneSelected(zone: Zone)
    }

    val date = Date()

    var zones: MutableList<Zone> = mutableListOf()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoneViewHolder {
        return ZoneViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_zone_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ZoneViewHolder, position: Int) {
        holder.bind(zones[position])
    }

    override fun getItemCount() = zones.size

    inner class ZoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(zone: Zone) = with(itemView) {
            val zoneId = ZoneId.of(zone.timeZoneId)
            val zonedDateTime = ZonedDateTime.ofInstant(date.toZonedDateTime().toInstant(), zoneId)

            val timeString = zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm"))
            val periodString = zonedDateTime.format(DateTimeFormatter.ofPattern("a"))

            val textColor = DateTimeController.getTextColorTint(context, zonedDateTime)
            val backgroundColor = DateTimeController.getBackgroundColorTint(context, zonedDateTime)
            val backgroundBlendColor = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                backgroundColor,
                BlendModeCompat.SRC_ATOP
            )

            nameTextView.text = zone.name
            locationNameTextView.text = zone.keywords[0]

            timeTextView.text = timeString
            periodTextView.text = periodString

            locationNameTextView.setTextColor(textColor)
            locationNameTextView.background.colorFilter = backgroundBlendColor

            setOnClickListener {
                listener.onZoneSelected(zone)
            }
        }
    }
}