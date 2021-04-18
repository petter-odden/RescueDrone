package View

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.log

// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLogin : Fragment() {
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



        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp)

        val emailFieldLayout : TextInputLayout = getView()!!.findViewById(R.id.emailError)
        val emailField : TextInputEditText = getView()!!.findViewById(R.id.email_field)

        val passwordFieldLayout : TextInputLayout = getView()!!.findViewById(R.id.passwordError)
        val passwordField : TextInputEditText = getView()!!.findViewById(R.id.password_field)

        val loginButton : CircularProgressButton = getView()!!.findViewById(R.id.login_button)
        val goToRegisterButton : TextView = getView()!!.findViewById(R.id.btnGoToRegister)
        val logo : AppCompatImageView = getView()!!.findViewById(R.id.logo)

        //Knapp til frontpage fragment
        logo.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer,FragmentFrontpage(),"FragmentFrontpage").addToBackStack(null).commit()
        }

        //Knapp til register fragment
        goToRegisterButton.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer,FragmentRegister(),"FragmentRegister").addToBackStack(null).commit()
        }

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

        //Login knapp
        loginButton.setOnClickListener {

            emailFieldLayout.error = null
            passwordFieldLayout.error = null

            var fieldCheck = true

            if(TextUtils.isEmpty(emailField.text)) {
                emailFieldLayout.error = "Please Enter Email Address";
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

            loginButton.startAnimation()

            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            try {
                loginButton.doneLoadingAnimation(2, bitmap)
                (activity as LoginRegister).loginUser(email, password)

            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                loginButton.revertAnimation()
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentLogin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentLogin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}