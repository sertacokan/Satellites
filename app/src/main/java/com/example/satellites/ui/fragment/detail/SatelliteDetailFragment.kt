package com.example.satellites.ui.fragment.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.satellites.R
import com.example.satellites.databinding.FragmentSatelliteDetailBinding
import com.example.satellites.ui.state.Error
import com.example.satellites.ui.state.Loading
import com.example.satellites.ui.state.Success
import com.example.satellites.util.fromHtml
import com.example.satellites.util.orZero
import com.example.satellites.util.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SatelliteDetailFragment : Fragment(R.layout.fragment_satellite_detail) {

    private val args by navArgs<SatelliteDetailFragmentArgs>()
    private val viewModel: SatelliteDetailViewModel by viewModel(parameters = { parametersOf(args.id) })
    private val binding by viewBinding(FragmentSatelliteDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUIState()
    }

    private fun collectUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    when (uiState) {
                        is Error -> {
                            if (binding.progressBar.isVisible) {
                                binding.progressBar.isVisible = false
                            }
                            Toast.makeText(requireContext(), uiState.messageRes, Toast.LENGTH_LONG).show()
                        }

                        Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Success -> {
                            val detailUIModel = uiState.data
                            updateUI(detailUIModel)
                            if (binding.progressBar.isVisible) {
                                binding.progressBar.isVisible = false
                            }
                        }
                    }
                }
        }
    }

    private fun updateUI(detailUIModel: DetailUIModel) {
        binding.detailNameTV.text = args.name
        binding.detailDateTV.text = detailUIModel.firstFlightDate
        val heightMassText = requireContext().resources.getString(R.string.height_mass_formatted_text, detailUIModel.height.orZero(), detailUIModel.mass.orZero())
        binding.detailHeightMassTV.text = heightMassText.fromHtml()
        val costText = requireContext().resources.getString(R.string.cost_formatted_text, detailUIModel.costPerLaunch.orZero())
        binding.detailCostTV.text = costText.fromHtml()
        val positionText = requireContext().resources.getString(R.string.last_position_formatted_text, detailUIModel.posX.orZero(), detailUIModel.posY.orZero())
        binding.detailLastPositionTV.text = positionText.fromHtml()
    }
}