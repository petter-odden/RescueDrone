package View

import Model.User
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rescuedrone.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class UserProfileActivity : AppCompatActivity() {


    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var database: DatabaseReference
    private val TAG = "UserProfileActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        val currentUser = auth.currentUser
        val btnChangeEmail : TextView = findViewById(R.id.change_email)
        val btnChangeFirstName : TextView = findViewById(R.id.change_first_name)
        val btnChangeLastName : TextView = findViewById(R.id.change_last_name)
        val btnLogout : TextView = findViewById(R.id.btnLogOut)
        val btnChangePassword : TextView = findViewById(R.id.btnChangePassword)

        database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(currentUser?.uid.toString())

        val nameListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.value
                val dbUser = dataSnapshot.getValue<User>()
                updateUI(dbUser)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(nameListener)



        btnChangeEmail.setOnClickListener() {
            showEmailDialogue()
        }

        btnChangeFirstName.setOnClickListener() {
            showNameDialogue()
        }

        btnChangeLastName.setOnClickListener() {
            showNameDialogue()
        }

        btnLogout.setOnClickListener() {
            logOut()
        }

        btnChangePassword.setOnClickListener() {
            showPasswordDialogue()
        }

        bottomNav.menu.findItem(R.id.profile).isChecked = true;
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
                    val intent = Intent(this, MainActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.profile -> {

                }
            }
            true
        }

    }


    fun showEmailDialogue() {

        val builder = MaterialAlertDialogBuilder(this)
        val inflater = layoutInflater
        builder.setTitle("Change email address")
        val dialogueLayout = inflater.inflate(R.layout.change_email_dialogue, null)


        val emailLayout : TextInputLayout = dialogueLayout.findViewById(R.id.new_email_layout)
        val confirmEmailLayout : TextInputLayout = dialogueLayout.findViewById(R.id.confirm_new_email_layout)
        val passwordLayout : TextInputLayout = dialogueLayout.findViewById(R.id.password_layout)

        val emailField : TextInputEditText = dialogueLayout.findViewById(R.id.new_email_field)
        val confirmEmailField : TextInputEditText = dialogueLayout.findViewById(R.id.confirm_new_email_field)
        val passwordField : TextInputEditText = dialogueLayout.findViewById(R.id.password_field)

        builder.setView(dialogueLayout)

        builder.setPositiveButton("Submit") { _, _ ->

//            emailLayout.error = null
//            confirmEmailLayout.error = null
//            passwordLayout.error = null
//
//            var fieldCheck = true
//
//            if(TextUtils.isEmpty(emailField.text)) {
//                emailLayout.error = "Please enter new email address";
//                fieldCheck = false
//            }
//            if (TextUtils.isEmpty(confirmEmailField.text)) {
//                confirmEmailLayout.error = "Please confirm the new email address"
//                fieldCheck = false
//            }
//
//            if (TextUtils.isEmpty(passwordField.text)) {
//                passwordLayout.error = "Please enter password"
//            }
//
//            if (emailField.text !== confirmEmailField.text) {
//                emailLayout.error = "Please confirm the new email address"
//                confirmEmailLayout.error = "Please confirm the new email address"
//                fieldCheck = false
//            }
//
//            if(!fieldCheck) {
//                return@setPositiveButton
//            }

            // TODO: Fix the field check for the alert dialogues

            val currentUser = auth.currentUser
            val credential = EmailAuthProvider.getCredential(currentUser?.email!!, passwordField.text.toString())

            // Prompt the user to re-provide their sign-in credentials
            currentUser.reauthenticate(credential).addOnCompleteListener {

                currentUser.updateEmail(emailField.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(currentUser.uid.toString()).child("email")
                                database.setValue(emailField.text.toString().trim())
                                Toast.makeText(applicationContext, "Email address updated successfully", Toast.LENGTH_SHORT).show()
                            }
                        }

            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
    }

    fun showPasswordDialogue() {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Change password")
        val dialogueLayout = inflater.inflate(R.layout.change_password_dialogue, null)

        val newPasswordLayout : TextInputLayout = dialogueLayout.findViewById(R.id.new_password_layout)
        val confirmNewPasswordLayout : TextInputLayout = dialogueLayout.findViewById(R.id.confirm_new_password_layout)
        val passwordLayout : TextInputLayout = dialogueLayout.findViewById(R.id.password_layout)

        val newPasswordField : TextInputEditText = dialogueLayout.findViewById(R.id.new_password_field)
        val confirmNewPasswordField : TextInputEditText = dialogueLayout.findViewById(R.id.confirm_new_password_field)
        val passwordField : TextInputEditText = dialogueLayout.findViewById(R.id.password_field)

        builder.setView(dialogueLayout)

        builder.setPositiveButton("Submit") { _, _ ->

//            newPasswordLayout.error = null
//            confirmNewPasswordLayout.error = null
//            passwordLayout.error = null
//
//            var fieldCheck = true
//
//            if(TextUtils.isEmpty(newPasswordField.text)) {
//                newPasswordLayout.error = "Please enter a new password";
//                fieldCheck = false
//            }
//            if (TextUtils.isEmpty(confirmNewPasswordField.text)) {
//                confirmNewPasswordLayout.error = "Please confirm the new password"
//                fieldCheck = false
//            }
//
//            if (TextUtils.isEmpty(passwordField.text)) {
//                passwordLayout.error = "Please enter password"
//            }
//
//            if (newPasswordField.text !== confirmNewPasswordField.text) {
//                newPasswordLayout.error = "Please confirm the new password"
//                confirmNewPasswordLayout.error = "Please confirm the new password"
//                fieldCheck = false
//            }
//
//            if(!fieldCheck) {
//                return@setPositiveButton
//            }

            val currentUser = auth.currentUser
            val credential = EmailAuthProvider.getCredential(currentUser?.email!!, passwordField.text.toString())

            // Prompt the user to re-provide their sign-in credentials
            currentUser.reauthenticate(credential).addOnCompleteListener {

                currentUser.updatePassword(newPasswordField.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext, "Password updated successfully", Toast.LENGTH_SHORT).show()
                            }
                        }

            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
    }

    fun showNameDialogue() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Change name")
        val dialogueLayout = inflater.inflate(R.layout.change_name_dialogue, null)

        val newFirstNameLayout : TextInputLayout = dialogueLayout.findViewById(R.id.new_fname_layout)
        val newLastNameLayout : TextInputLayout = dialogueLayout.findViewById(R.id.new_lname_layout)

        val newFirsNameField : TextInputEditText = dialogueLayout.findViewById(R.id.new_fname_field)
        val newLastNameField : TextInputEditText = dialogueLayout.findViewById(R.id.new_lname_field)

        builder.setView(dialogueLayout)

        builder.setPositiveButton("Submit") { _, _ ->
//            newFirstNameLayout.error = null
//            newLastNameLayout.error = null
//
//            var fieldCheck = true
//
//            if(TextUtils.isEmpty(newFirsNameField.text)) {
//                newFirstNameLayout.error = "Please Enter First Name";
//                fieldCheck = false
//            }
//            if (TextUtils.isEmpty(newLastNameField.text)) {
//                newLastNameLayout.error = "Please Enter Last Name"
//                fieldCheck = false
//            }
//
//            if(!fieldCheck) {
//                return@setPositiveButton
//            }

            val currentUser = auth.currentUser

            try {
                database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(currentUser?.uid.toString()).child("fname")
                database.setValue(newFirsNameField.text.toString().trim())

                database = FirebaseDatabase.getInstance("https://rescuedrone-6c5d7-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(currentUser?.uid.toString()).child("lname")
                database.setValue(newLastNameField.text.toString().trim())

                Toast.makeText(applicationContext, "Name changed successfully", Toast.LENGTH_SHORT).show()

            } catch (e: java.lang.Exception) {
                Toast.makeText(applicationContext, "Error: " + e.message, Toast.LENGTH_LONG).show()
            }

        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
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
        val displayEmailText : TextView = findViewById(R.id.display_email_text)
        val displayFirstNameText : TextView = findViewById(R.id.display_first_name_text)
        val displayLastNameText : TextView = findViewById(R.id.display_last_name_text)

        displayEmailText.text = dbUser?.email.toString()
        displayFirstNameText.text = dbUser?.fname.toString()
        displayLastNameText.text = dbUser?.lname.toString()


    }
}