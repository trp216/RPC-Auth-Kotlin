import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window
import kotlinx.serialization.UseSerializers

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved
var currentUser: User = User("","","","","")

suspend fun username(u:String){
    currentUser.username = u
}
suspend fun password(u:String){
    currentUser.password=u
}
suspend fun firstname(u:String){
    currentUser.firstname=u
}
suspend fun lastname(u:String){
    currentUser.lastname=u
}
suspend fun birthdate(u:String){
    currentUser.birthdate=u
}

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getUsers(): List<User> {
    return jsonClient.get(endpoint + User.path)
}

suspend fun addUsers() {
    jsonClient.post<Unit>(endpoint + User.path) {
        contentType(ContentType.Application.Json)
        body = currentUser
    }
}

suspend fun deleteUsers(user: User) {
    jsonClient.delete<Unit>(endpoint + User.path + "/${user.id}")
}