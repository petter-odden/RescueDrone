package Model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties

data class User(val fName: String? = null, val lName: String? = null, val email: String? = null) {
    val firstName = fName?.capitalize(Locale.ROOT)
    val lastName = lName?.capitalize(Locale.ROOT)
    val emailAddress = email
}