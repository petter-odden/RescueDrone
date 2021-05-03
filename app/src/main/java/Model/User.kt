package Model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties

open class User(val fname: String? = null, val lname: String? = null, val email: String? = null) : Serializable {
}