package View

import Controller.MissionListAdapter
import Model.Mission
import View.MissionActivity
import Model.User
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.BuildConfig.MAPS_API_KEY
import com.example.rescuedrone.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.schibstedspain.leku.*
import com.schibstedspain.leku.locale.SearchZoneRect
import java.util.*


private const val MAP_BUTTON_REQUEST_CODE = 1
private const val MAP_POIS_BUTTON_REQUEST_CODE = 2


class CreateMissionActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private var auth: FirebaseAuth = Firebase.auth
    private var longitude = 0.00
    private var latitude = 0.00



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_mission)

        val user : User = intent.getSerializableExtra("user") as User;
        val firebaseUser = auth.currentUser

        val titleLayout : TextInputLayout = findViewById(R.id.title_layout)
        val titleField : TextInputEditText = findViewById(R.id.title_field)

        val btnOpenMaps : AppCompatButton = findViewById(R.id.btn_open_maps)

        val descriptionLayout : TextInputLayout = findViewById(R.id.description_layout)
        val descriptionField : TextInputEditText = findViewById(R.id.description_field)

        val qualifiedButton : RadioButton = findViewById(R.id.qualified_button)
        val allButton : RadioButton = findViewById(R.id.all_button)

        // Todo: Make priorityPicker appear only to emergencyPersonnel
        val priorityField : NumberPicker = findViewById(R.id.priority_field)

        val btnCancel : AppCompatButton = findViewById(R.id.btn_cancel)
        val btnSubmit : AppCompatButton = findViewById(R.id.btn_submit)

        val currentTime: Date = Calendar.getInstance().time
        priorityField.maxValue = 3
        priorityField.minValue = 1


        btnOpenMaps.setOnClickListener() {
            val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(41.4036299, 2.1743558)
                    .withGeolocApiKey(MAPS_API_KEY)
                    .withSearchZone("es_ES")
                    .withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
                    .withDefaultLocaleSearchZone()
                    .shouldReturnOkOnBackPressed()
                    .withStreetHidden()
                    .withCityHidden()
                    .withZipCodeHidden()
                    .withSatelliteViewHidden()
                    .withGooglePlacesApiKey(MAPS_API_KEY)
                    .withGoogleTimeZoneEnabled()
                    .withVoiceSearchHidden()
                    .withUnnamedRoadHidden()
                    .build(applicationContext)

            startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE)
        }

        btnCancel.setOnClickListener() {
            this.finish()
        }

        btnSubmit.setOnClickListener() {
            val priority = priorityField.value


            var wantQualified  = true
            var wantAll  = false

            if (qualifiedButton.isChecked) {
                wantQualified = true
                wantAll = false
            }

            if (allButton.isChecked) {
                wantQualified = false
                wantAll = true
            }

            val mission  = Mission(
                titleField.text.toString(),
                com.google.maps.model.LatLng(latitude, longitude),
                descriptionField.text.toString(),
                firebaseUser?.uid,
                user,
                currentTime,
                wantAll,
                wantQualified,
                false,
                priority
            )
            database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
                "missions"
            ).push()
            database.setValue(mission).addOnSuccessListener() {
                Toast.makeText(this, "Mission added successfully", Toast.LENGTH_SHORT).show()

                finish()
//                val intent = Intent(this, MissionActivity::class.java)
//                startActivity(intent)
            }
        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK")
            if (requestCode == 1) {
                latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                longitude = data.getDoubleExtra(LONGITUDE, 0.0)

                val locationInfo : TextView = findViewById(R.id.location_info)
                locationInfo.text = "$latitude $longitude"


                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val postalcode = data.getStringExtra(ZIPCODE)
                Log.d("POSTALCODE****", postalcode.toString())
                val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
                Log.d("BUNDLE TEXT****", bundle?.getString("test").toString())
                val fullAddress = data.getParcelableExtra<Address>(ADDRESS)
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString())
                }
                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
                Log.d("TIME ZONE ID****", timeZoneId.toString())
                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
                Log.d("TIME ZONE NAME****", timeZoneDisplayName.toString())
            } else if (requestCode == 2) {
                latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                Log.d("LekuPoi****", lekuPoi.toString())
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED")
        }
    }


}