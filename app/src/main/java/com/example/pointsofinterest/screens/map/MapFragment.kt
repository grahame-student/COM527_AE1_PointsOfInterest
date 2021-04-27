package com.example.pointsofinterest.screens.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointsofinterest.R
import com.example.pointsofinterest.databinding.MapFragmentBinding
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class MapFragment : Fragment(), LocationListener {
    private val gpsPermissionCode = 111

    private lateinit var binding: MapFragmentBinding
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        Log.i("MapFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.map_fragment,
                container,
                false
        )

        // Set up the viewModel binding so that it can handle events defined in the layout
        binding.mapViewModel = viewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventStartAddingPoi.observe(viewLifecycleOwner, { isAddingPoi ->
            if (isAddingPoi) addNewPoi()
        })

        return binding.root
    }

    private fun addNewPoi() {
        Log.i("MapFragment", "Navigating to the PoiFragment")
        view?.findNavController()?.navigate(R.id.action_mapFragment_to_poiFragment)
        viewModel.onAddingPoiComplete()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMap()

        // We should only start monitoring location after onCreateView() has completed to ensure
        // that the context is set
        monitorLocation()
    }

    private fun configureMap() {
        // The context is null until onCreateView() completes. If, somehow, we get here before
        // then we simply exit. Logging could be added before returning to help with debugging
        val context = context ?: return

        val items = ItemizedIconOverlay(context, arrayListOf<OverlayItem>(), null)

        binding.map1.controller.setZoom(14.0)
        binding.map1.overlays.add(items)
    }

    private fun monitorLocation() {
        // The context is null until onCreateView() completes. If, somehow, we get here before
        // then we simply exit. Logging could be added before returning to help with debugging
        val context = context ?: return
        val permission = checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            Log.i("MapFragment", "Starting to monitor location")
            val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        } else {
            Log.i("MapFragment", "Access to fine location denied")
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.i(
                        "MapFragment",
                        "Permission was previously denied, show rationale for the permission"
                )
                showRationale()
            } else {
                Log.i("MapFragment", "Request fine access permission from the user")
                requestFineLocationPermission()
            }
        }
    }

    private fun showRationale() {
        // The context is null until onCreateView() completes. If, somehow, we get here before
        // then we simply exit. Logging could be added before returning to help with debugging
        val context = context ?: return
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Permission to access your fine location is required so that nearby points of interest can be identified")
                .setTitle("Permission Required")
        builder.setPositiveButton("OK") { _, _ ->
            Log.i("MapFragment", "User has read location permission rationale")
            requestFineLocationPermission()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestFineLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), gpsPermissionCode)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            gpsPermissionCode -> {
                if (grantResults.isEmpty() ||
                        (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.i(
                            "MapFragment",
                            "User has granted permission to allow access to fine location"
                    )
                    monitorLocation()
                }
            }
        }
    }

    override fun onLocationChanged(newLoc: Location) {
        // There are no properties directly in the layout that allow us to update the location
        // using live data, therefore we do the next best thing and use the binding to update
        // the location instead.
        viewModel.setCurrentLocation(newLoc)
        binding.map1.controller.setCenter(
                GeoPoint(
                        viewModel.getCurrentLocation().latitude,
                        viewModel.getCurrentLocation().longitude
                )
        )
    }

    override fun onProviderDisabled(provider: String) {
        // The context is null until onCreateView() completes. If, somehow, we get here before
        // then we simply exit. Logging could be added before returning to help with debugging
        val context = context ?: return
        Toast.makeText(context, "Provider disabled", Toast.LENGTH_LONG).show()
    }

    override fun onProviderEnabled(provider: String) {
        // The context is null until onCreateView() completes. If, somehow, we get here before
        // then we simply exit. Logging could be added before returning to help with debugging
        val context = context ?: return
        Toast.makeText(context, "Provider enabled", Toast.LENGTH_LONG).show()
    }

    // Deprecated at API level 29, but must still be included, otherwise your
    // app will crash on lower-API devices as their API will try and call it
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }
}
