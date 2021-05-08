package View

import Controller.MissionListAdapter
import Model.Intel
import Model.Mission
import Model.User
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rescuedrone.R
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*


class MissionActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var database: DatabaseReference
    private val TAG = "MissionActivity"
    private lateinit var user : User
    private var firebaseUser : FirebaseUser? = auth.currentUser
    val list : ArrayList<Mission> = arrayListOf()
    lateinit var intelAdapter: FirebaseListAdapter<Intel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)


        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val newAssignmentButton : FloatingActionButton = findViewById(R.id.btnNewAssignment)



        //region Getting the user object from firebase
        database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
                "users"
        ).child(firebaseUser?.uid.toString())
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.value
                user = dataSnapshot.getValue<User>()!!
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting user failed, log a message
                Log.w(TAG, databaseError.message, databaseError.toException())
            }
        }
        database.addValueEventListener(userListener)
        //endregion

        newAssignmentButton.setOnClickListener() {
            val intent = Intent(this@MissionActivity, CreateMissionActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }




        //region RecyclerView setup
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MissionListAdapter(list, this)
        recyclerView.adapter = adapter
        val missionReference = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
                "missions"
        )

        val missionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapsot: DataSnapshot) {
                for(missionSnapshot : DataSnapshot in dataSnapsot.children) {
                    missionSnapshot.value
                    val mission = missionSnapshot.getValue<Mission>()!!

                    list.add(mission)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Mission failed, log a message
                Log.w(TAG, databaseError.message, databaseError.toException())
            }
        }
        missionReference.addValueEventListener(missionListener)

        adapter.setOnItemClickListener(object : MissionListAdapter.OnItemClickListener {
            override fun onPushToDronehub(position: Int) {
                val mapPosition = list[position]
                val missionLatitude = mapPosition.location.lat.toString()
                val missionLongitude = mapPosition.location.lng.toString()
                val missionID = mapPosition.missionID.toString()
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra("missionLatitude", missionLatitude)
                intent.putExtra("missionLongitude", missionLongitude)
                intent.putExtra("missionID", missionID)

                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                val title = "Choose"
                // Create intent to show chooser
                val chooser = Intent.createChooser(intent, title)

                // Try to invoke the intent.
                try {
                    startActivity(chooser)
                } catch (e: ActivityNotFoundException) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }

            override fun onViewIntel(position: Int) {
                val mapPosition = list[position]
                val missionID = mapPosition.missionID


                val builder = MaterialAlertDialogBuilder(this@MissionActivity)
                val inflater = layoutInflater
                builder.setTitle("Mission Intel")
                val dialogueLayout = inflater.inflate(R.layout.intel_dialogue, null)
                builder.setView(dialogueLayout)

                val intelList: ListView = dialogueLayout.findViewById(R.id.intel_list)
                var intel: Intel

//                database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("intel/$missionID")
//                val intelListener = object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        dataSnapshot.value
//                        intel = dataSnapshot.getValue<Intel>()!!
//                    }
//
//                    override fun onCancelled(databaseError: DatabaseError) {
//                        // Getting user failed, log a message
//                        Log.w(TAG, databaseError.message, databaseError.toException())
//                    }
//                }
//                database.addValueEventListener(userListener)

                val query: Query = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("intel/$missionID")
                val options = FirebaseListOptions.Builder<Intel>()
                        .setLayout(R.layout.intel_list_item)
                        .setQuery(query, Intel::class.java)
                        .build()

                intelAdapter = object : FirebaseListAdapter<Intel>(options) {
                    override fun populateView(v: View, model: Intel, position: Int) {
                        // Bind the Chat to the view
                        val tvIntelCoordinates: TextView = v.findViewById(R.id.tv_coordinates)
                        tvIntelCoordinates.text = model.droneLat.toString() + "\n" + model.droneLng.toString()

                    }
                }

                intelList.adapter = intelAdapter

                intelAdapter.startListening()

                builder.setNegativeButton("Close") { _, _ ->

                }

                builder.show()
            }
        })
        //endregion




        //region Bottom navigation
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.menu.findItem(R.id.missions).isChecked = true;

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.missions -> {

                }

                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.dronehub -> {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.profile -> {
                    val intent = Intent(this, UserProfileActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }
            }
            true
        }
        //endregion
    }

    fun getFirebaseUser(): FirebaseUser? {
        return firebaseUser
    }

    fun getUser(): User {
        return user
    }

}