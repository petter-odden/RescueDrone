package View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.rescuedrone.R

//  Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFrontpage.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFrontpage : Fragment() {
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
        return inflater.inflate(R.layout.fragment_frontpage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToRegisterButton : CircularProgressButton = getView()!!.findViewById(R.id.btnGoToRegister)
        val goToLoginButton : CircularProgressButton = getView()!!.findViewById(R.id.btnGoToLogin)
        val goToSOS : CircularProgressButton = getView()!!.findViewById(R.id.btnSos)


        // Knapp til login fragment
        goToLoginButton.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer,FragmentLogin(),"FragmentLogin").addToBackStack(null).commit()
        }

        // Knapp til register fragment
        goToRegisterButton.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer,FragmentRegister(),"FragmentRegister").addToBackStack(null).commit()
        }

        // Knapp til SOS fragment
        // Todo: Fikse SOS fragment
        goToSOS.setOnClickListener{
            fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer,FragmentSos(),"FragmentSos").addToBackStack(null).commit()
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentFrontpage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentFrontpage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}