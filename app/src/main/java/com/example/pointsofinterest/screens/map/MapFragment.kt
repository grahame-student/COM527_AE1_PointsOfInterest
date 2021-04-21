package com.example.pointsofinterest.screens.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pointsofinterest.R
import com.example.pointsofinterest.databinding.MapFragmentBinding

class MapFragment : Fragment() {
    private lateinit var binding: MapFragmentBinding
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<MapFragmentBinding>(
            inflater,
            R.layout.map_fragment,
            container,
            false
        )

        Log.i("MapFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        return binding.root
    }
}
