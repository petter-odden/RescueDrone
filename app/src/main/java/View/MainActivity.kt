package View

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.rescuedrone.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var database: DatabaseReference
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = Firebase.database.reference
        val user : FirebaseUser? = auth.currentUser
        val btnLogout : Button = findViewById(R.id.logout_button)
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)



        database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(user?.uid.toString())

        val nameListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getValue()
                val dbUser = dataSnapshot.getValue<User>()
                updateUI(dbUser)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(nameListener)


        bottomNav.menu.findItem(R.id.dronehub).isChecked = true;

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.missions -> {
                    val intent = Intent(this, MissionActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }

                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.dronehub -> {
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

        btnLogout.setOnClickListener() {
            logOut()
        }

    }

    fun logOut() {

        try {
            auth.signOut()
        } catch (e: Exception) {
            Toast.makeText(
                this, e.message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val intent = Intent(this, LoginRegister::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message)
        }

        startActivity(intent)
    }

    fun updateUI(dbUser: User?) {
        val firstNameDisplay : TextView = findViewById(R.id.display_first_name)
        val lastNameDisplay : TextView = findViewById(R.id.display_last_name)
        val emailDisplay : TextView = findViewById(R.id.display_email)

        firstNameDisplay.text = dbUser?.fname.toString()
        lastNameDisplay.text = dbUser?.lname.toString()
        emailDisplay.text = dbUser?.email


    }


}