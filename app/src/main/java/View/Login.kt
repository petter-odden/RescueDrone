package View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.CircularPropagation
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val passwordField: TextInputEditText = findViewById(R.id.password_field)
        val emailField : TextInputEditText = findViewById(R.id.email_field)
        val loginButton : CircularProgressButton = findViewById(R.id.login_button)

        //Kosmetisk kos for hint text
        emailField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                emailField.hint = ""
            else
                emailField.hint = "Email Address"
        }

        //Kosmetisk kos for hint text
        passwordField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passwordField.hint = ""
            else
                passwordField.hint = "Password"
        }

        //Kosmetisk kos for login knapp
        loginButton.setOnClickListener {
            loginButton.startAnimation()
        }

    }


}