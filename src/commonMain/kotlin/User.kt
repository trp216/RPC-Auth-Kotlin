import kotlinx.serialization.Serializable

@Serializable
data class User(var username: String, var password: String, var firstname:String, var lastname:String, var birthdate:String) {
    var id: Int = username.hashCode()

    companion object {
        const val path = "/register"
    }
}