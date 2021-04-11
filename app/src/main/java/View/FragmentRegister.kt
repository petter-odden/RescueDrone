package View

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var auth: FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRegister.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRegister : Fragment() {
    // Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Instansiering
        auth = FirebaseAuth.getInstance()
        val nameFieldLayout : TextInputLayout = getView()!!.findViewById(R.id.nameError)
        val nameField : TextInputEditText = getView()!!.findViewById(R.id.name_field)

        val emailFieldLayout : TextInputLayout = getView()!!.findViewById(R.id.emailError)
        val emailField : TextInputEditText = getView()!!.findViewById(R.id.email_field)

        val passwordFieldLayout : TextInputLayout = getView()!!.findViewById(R.id.passwordError)
        val passwordField : TextInputEditText = getView()!!.findViewById(R.id.password_field)

        val registerButton : CircularProgressButton = getView()!!.findViewById(R.id.register_button)
        val goToLoginButton : TextView = getView()!!.findViewById(R.id.btnGoToLogin)
        val logo : AppCompatImageView = getView()!!.findViewById(R.id.logo)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp)

        //endregion

        // Knapp til frontpage fragment
        logo.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(
                R.id.fragmentContainer,
                FragmentFrontpage(),
                "FragmentFrontpage"
            ).addToBackStack(null).commit()

        }

        // Knapp til login fragment
        goToLoginButton.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(
                R.id.fragmentContainer,
                FragmentLogin(),
                "FragmentLogin"
            ).addToBackStack(null).commit()
        }

        // Register knapp
        registerButton.setOnClickListener {

            var fieldCheck = true

            if(TextUtils.isEmpty(nameField.text)) {
                nameFieldLayout.error = " Please Enter Name";
                fieldCheck = false
            }
            if(TextUtils.isEmpty(emailField.text)) {
                emailFieldLayout.error = " Please Enter Email Address";
                fieldCheck = false
                // Todo: Sjekke at email format er riktig, abc@noe.io
            }
            if(TextUtils.isEmpty(passwordField.text)){
                passwordFieldLayout.error = "Please Choose a Password"
                fieldCheck = false
                // Todo: Lage strengere krav for passord?
            }
            if(!fieldCheck) {
                return@setOnClickListener
            }

            registerButton.startAnimation()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            try {
                (activity as LoginRegister).registerUser(email, password)

            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                registerButton.revertAnimation()
            }

        }

        //region Kosmetisk kos
        // Kosmetisk kos for goToLoginButton
        goToLoginButton.setPaintFlags(goToLoginButton.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        // Kosmetisk kos for hint text
        nameField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                nameField.hint = ""
            else
                nameField.hint = "Full Name"
        }

        // Kosmetisk kos for hint text
        emailField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                emailField.hint = ""
            else
                emailField.hint = "Email Address"
        }

        // Kosmetisk kos for hint text
        passwordField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passwordField.hint = ""
            else
                passwordField.hint = "Password"
        }

        //endregion






    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRegister.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRegister().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

public fun getNameReg() {}

public fun getEmailReg() {}

public fun getPassReg() {}