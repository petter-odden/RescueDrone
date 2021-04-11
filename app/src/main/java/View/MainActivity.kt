package View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.rescuedrone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val user = auth.currentUser
        val btnLogout : Button = findViewById(R.id.logout_button)
        val uidDisplay : TextView = findViewById(R.id.display_uid)
        val emailDisplay : TextView = findViewById(R.id.display_email)
        val userId = intent.getStringExtra("user_id")
        val userEmail = intent.getStringExtra("user_email")

        uidDisplay.text = "User ID : $userId"
        emailDisplay.text = "User Email : $userEmail"

        if (userId == null || userEmail == null) {
            uidDisplay.text = "User ID: \n" + user?.uid
            emailDisplay.text = "Email address: \n" + user?.email
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
}