package com.example.pointsofinterest.screens.poi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointsofinterest.R
import com.example.pointsofinterest.data.PointOfInterestDatabase
import com.example.pointsofinterest.databinding.PoiFragmentBinding

class PoiFragment : Fragment() {
    private lateinit var binding: PoiFragmentBinding
    private lateinit var viewModel: PoiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("MapFragment", "Called ViewModelProvider.get")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.poi_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = PointOfInterestDatabase.getInstance(application).pointDao()
        val viewModelFactory = PoiViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PoiViewModel::class.java)

        // Set up the viewModel binding so that it can handle events defined in the layout
        binding.poiViewModel = viewModel

        viewModel.eventAddPoiCompleted.observe(viewLifecycleOwner, { isAddingPoi ->
            if (isAddingPoi) addNewPoiCompleted()
        })

        return binding.root
    }

    private fun addNewPoiCompleted() {
        viewModel.onAddingPoiComplete()
        view?.findNavController()?.navigate(R.id.action_poiFragment_to_mapFragment)
    }
}
