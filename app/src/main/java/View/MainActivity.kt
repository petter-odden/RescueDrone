package View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.rescuedrone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var database: DatabaseReference
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        val user = auth.currentUser

        val btnLogout : Button = findViewById(R.id.logout_button)
        val firstNameDisplay : TextView = findViewById(R.id.display_first_name)
        val lastNameDisplay : TextView = findViewById(R.id.display_last_name)
        val emailDisplay : TextView = findViewById(R.id.display_email)

        val reference = FirebaseDatabase.getInstance().reference.child("users").child(user?.uid.toString())
//        val userListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val dbUser = dataSnapshot.getValue<User>()
//                firstNameDisplay.text = dbUser?.first_name
//                lastNameDisplay.text = dbUser?.last_name
//                emailDisplay.text = dbUser?.email_address
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        reference.addValueEventListener(userListener)

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


}