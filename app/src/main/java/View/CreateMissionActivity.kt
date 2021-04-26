package View

import Controller.MissionListAdapter
import Model.Mission
import View.MissionActivity
import Model.User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R
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
import java.util.*

class CreateMissionActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private var auth: FirebaseAuth = Firebase.auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_mission)

        val user : User = intent.getSerializableExtra("user") as User;
        val firebaseUser = auth.currentUser

        val titleLayout : TextInputLayout = findViewById(R.id.title_layout)
        val titleField : TextInputEditText = findViewById(R.id.title_field)

        val locationLayout : TextInputLayout = findViewById(R.id.location_layout)
        val locationField : TextInputEditText = findViewById(R.id.location_field)

        val descriptionLayout : TextInputLayout = findViewById(R.id.description_layout)
        val descriptionField : TextInputEditText = findViewById(R.id.description_field)

        val qualifiedButton : RadioButton = findViewById(R.id.qualified_button)
        val allButton : RadioButton = findViewById(R.id.all_button)

        // Todo: Make priorityPicker appear only to emergencyPersonnel
        val priorityField : NumberPicker = findViewById(R.id.priority_field)

        val btnCancel : CircularProgressButton = findViewById(R.id.btn_cancel)
        val btnSubmit : CircularProgressButton = findViewById(R.id.btn_submit)

        val currentTime: Date = Calendar.getInstance().time
        priorityField.maxValue = 3
        priorityField.minValue = 1


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
                locationField.text.toString(),
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

                val intent = Intent(this, MissionActivity::class.java)
                startActivity(intent)
            }
        }



    }


}