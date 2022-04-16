package com.example.kakitanganapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.regex.Pattern


class ProfileEdit : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth:FirebaseAuth
    private lateinit var userRef: DatabaseReference
    private lateinit var currUser: User

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val permissionId = 2

    private lateinit var btnEPSave : Button
    private lateinit var btnGetLocation : Button
    private lateinit var ttlProfileEdit : Toolbar
    private lateinit var editTextAddress: EditText
    private lateinit var editTextName : EditText
    private lateinit var editTextPhoneNo : EditText
    private lateinit var editRadGroupGender: RadioGroup


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
        editTextName = findViewById(R.id.editTextName)
        editTextPhoneNo = findViewById(R.id.editTextPhoneNo)
        editRadGroupGender = findViewById(R.id.editRadGroupGender)

        // initialize fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        auth = FirebaseAuth.getInstance()
        var sessionUser = auth.currentUser
        if(sessionUser == null){
            startActivity(Intent(this,UserLogin::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }

        userRef = Firebase.database.reference.child("User").child(auth.uid.toString())
        userRef.get().addOnSuccessListener {
            currUser = it.getValue<User>()!!
            editTextName.setText(currUser.name)
            when(currUser.gender){
                "Male" -> editRadGroupGender.check(R.id.radBtnMale)
                "Female" -> editRadGroupGender.check(R.id.radBtnFemale)
            }
            editTextPhoneNo.setText(currUser.phoneNum)
            editTextAddress.setText(currUser.address)

        }.addOnFailureListener{
            Toast.makeText(
                this,"Unexpected Error occured",
                Toast.LENGTH_LONG
            ).show()
            startActivity(
                Intent(
                    this,UserLogin::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnGetLocation->{
                getLocation()
            }
            R.id.btnEPSave->{
                val userName = editTextName.text.toString().trim()
                val userPhone = editTextPhoneNo.text.toString().trim()
                val userAddress = editTextAddress.text.toString().trim()
                val phoneFormat = Pattern.compile ("^(01)(([02-46-9]-*[0-9]{7,7})|([1]-*[0-9]{8,8}))\$")
                val format = phoneFormat.matcher(userPhone)
                lateinit var userGender: String
                when(editRadGroupGender.checkedRadioButtonId){
                    R.id.radBtnFemale -> userGender = "Female"
                    R.id.radBtnMale -> userGender = "Male"
                }

                currUser.gender = userGender
                if(!TextUtils.isEmpty(userName))
                    currUser.name = userName
                if(!TextUtils.isEmpty(userAddress))
                    currUser.address = userAddress
                if(!TextUtils.isEmpty(userPhone)) {
                    if (!format.matches()) {
                        editTextPhoneNo.error = "Invalid phone number format"
                        editTextPhoneNo.requestFocus()
                        return
                    } else
                        currUser.phoneNum = userPhone
                }
                userRef.setValue(currUser).addOnSuccessListener {
                    Toast.makeText(this,"Changes has been updated",Toast.LENGTH_LONG).show()
                    finish();
                }.addOnFailureListener{
                    Toast.makeText(
                        this,"Something unexpected had occured, changes was not saveed", Toast.LENGTH_SHORT).show()

                }
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