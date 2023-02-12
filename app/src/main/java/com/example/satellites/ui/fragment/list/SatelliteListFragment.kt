package com.example.satellites.ui.fragment.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.satellites.R
import com.example.satellites.adapter.SatelliteListAdapter
import com.example.satellites.databinding.FragmentSatelliteListBinding
import com.example.satellites.ui.state.Error
import com.example.satellites.ui.state.Loading
import com.example.satellites.ui.state.Success
import com.example.satellites.util.queryFlow
import com.example.satellites.util.viewBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SatelliteListFragment : Fragment(R.layout.fragment_satellite_list) {

    private val viewModel: SatelliteListViewModel by viewModel()
    private val binding by viewBinding(FragmentSatelliteListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
        binding.satelliteListRV.adapter = SatelliteListAdapter { satelliteId, satelliteName ->
            findNavController().navigate(
                SatelliteListFragmentDirections.actionSatelliteListFragmentToSatelliteDetailFragment(satelliteId, satelliteName)
            )
        }
        binding.satelliteListRV.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    @OptIn(FlowPreview::class)
    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState
                        .collect { uiState ->
                            when (uiState) {
                                is Error -> {
                                    Toast.makeText(requireContext(), uiState.messageRes, Toast.LENGTH_LONG).show()
                                }

                                Loading -> {

                                }

                                is Success -> {
                                    (binding.satelliteListRV.adapter as SatelliteListAdapter)
                                        .submitList(uiState.data)
                                }
                            }
                        }

                }
                launch {
                    binding.searchBar.queryFlow()
                        .debounce(DEBOUNCE_MS) // OptIn
                        .collect { queryString ->
                            viewModel.searchSatellite(queryString)
                        }
                }
            }
        }
    }

    companion object {
        private const val DEBOUNCE_MS = 500L
    }
}