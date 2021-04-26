package View

import Controller.MissionListAdapter
import Model.Mission
import Model.User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.example.rescuedrone.R
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
            intent.putExtra("user",user)
            startActivity(intent)
        }



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
    }



    fun getFirebaseUser(): FirebaseUser? {
        return firebaseUser
    }

    fun getUser(): User {
        return user
    }

}