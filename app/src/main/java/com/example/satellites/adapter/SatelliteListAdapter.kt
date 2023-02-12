package com.example.satellites.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.satellites.database.list.SatelliteEntity
import com.example.satellites.holder.SatelliteListHolder

class SatelliteListAdapter(
    private val onItemClicked: (Int, String) -> Unit
) : ListAdapter<SatelliteEntity, SatelliteListHolder>(SatelliteEntityDiffUtil) {

    private companion object SatelliteEntityDiffUtil : DiffUtil.ItemCallback<SatelliteEntity>() {
        override fun areItemsTheSame(oldItem: SatelliteEntity, newItem: SatelliteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SatelliteEntity,
            newItem: SatelliteEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteListHolder {
        return SatelliteListHolder.create(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: SatelliteListHolder, position: Int) {
        val satelliteEntity = getItem(position)
        holder.bind(satelliteEntity)
    }
}