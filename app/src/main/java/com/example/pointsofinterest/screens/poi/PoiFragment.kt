package com.example.pointsofinterest.screens.poi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pointsofinterest.R
import com.example.pointsofinterest.databinding.PoiFragmentBinding

class PoiFragment : Fragment() {
    private lateinit var binding: PoiFragmentBinding
    private lateinit var viewModel: PoiViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Log.i("MapFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(PoiViewModel::class.java)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.poi_fragment,
                container,
                false
        )

        // Set up the viewModel binding so that it can handle events defined in the layout
        binding.poiViewModel = viewModel
        return binding.root
    }
}
