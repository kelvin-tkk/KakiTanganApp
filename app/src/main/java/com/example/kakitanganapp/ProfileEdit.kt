package com.example.kakitanganapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import androidx.core.content.ContextCompat
import java.util.*


class ProfileEdit : AppCompatActivity(), View.OnClickListener {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val permissionId = 2

    private lateinit var btnEPSave : Button
    private lateinit var btnGetLocation : Button
    private lateinit var ttlProfileEdit : Toolbar
    private lateinit var editTextAddress: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        ttlProfileEdit = findViewById(R.id.ttlProfileEdit)
        setSupportActionBar(ttlProfileEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnGetLocation= findViewById(R.id.btnGetLocation)
        btnGetLocation.setOnClickListener(this)
        btnEPSave= findViewById(R.id.btnEPSave)
        btnEPSave.setOnClickListener(this)

        editTextAddress = findViewById(R.id.editTextAddress)

        // initialize fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnGetLocation->{
                getLocation()
            }
            R.id.btnEPSave->{
                //If successful
                Toast.makeText(
                    this,
                    "Success saved changes",
                    Toast.LENGTH_LONG
                ).show()
                finish();
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if(isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener {task->
                    var location:Location? = task.result
                    if(location == null){
                        Toast.makeText(this, "Unexpected error occured", Toast.LENGTH_LONG).show()
                        val locationRequest = LocationRequest.create().apply {
                            interval = 1000
                            fastestInterval = 500
                            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        }
                        mFusedLocationClient.requestLocationUpdates(
                            locationRequest, locationCallback, Looper.getMainLooper()
                        )
                        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)

                        editTextAddress.setText(getCityName(location.latitude,location.longitude))
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName:String = ""
        var postalCode = ""
        var adminArea = ""
        var thoroughFare = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)


        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        postalCode = Adress.get(0).postalCode.toString()
        adminArea = Adress.get(0).adminArea.toString()
        thoroughFare = Adress.get(0).thoroughfare
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return  thoroughFare + " " + adminArea + " " + postalCode + " " + cityName + ", " + countryName
    }
}