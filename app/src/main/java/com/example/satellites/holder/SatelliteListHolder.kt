package com.example.satellites.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.satellites.R
import com.example.satellites.database.list.SatelliteEntity
import com.example.satellites.databinding.ItemSatelliteListBinding

class SatelliteListHolder private constructor(
    private val binding: ItemSatelliteListBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): SatelliteListHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSatelliteListBinding.inflate(layoutInflater, parent, false)
            return SatelliteListHolder(binding)
        }
    }

    fun bind(satelliteEntity: SatelliteEntity) {
        val isActive = satelliteEntity.isActive == 1
        binding.stateIndicatorView.updateState(isActive)
        binding.nameTV.text = satelliteEntity.name
        val stateStringRes = if (isActive) R.string.active_state else R.string.passive_state
        val stateString = itemView.context.getString(stateStringRes)
        binding.stateTV.text = stateString

        binding.nameTV.alpha = if (isActive) 1f else 0.5f
        binding.stateTV.alpha = if (isActive) 1f else 0.5f
    }
}