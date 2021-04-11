package View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val userEmail = ""
private val userPassword = ""

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

        val emailField : TextInputEditText = getView()!!.findViewById(R.id.email_field)
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

        //Kosmetisk kos for login knapp
        loginButton.setOnClickListener {
            loginButton.startAnimation()
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

public fun getEmail() {
    return
}
public fun getPassword() {}