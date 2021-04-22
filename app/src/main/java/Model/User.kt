package Model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties

data class User(val fname: String? = null, val lname: String? = null, val email: String? = null) {
}