import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo
import com.mongodb.ConnectionString

fun main() {

    val users = mutableListOf(
    User("user1", "12345", "Pepa","Pig","10-12-1998"),
    User("Tomatoes 🍅", "54321", "Pepe","Pantoja","04-03-1886"),
    User("Orange Juice 🍊", "1234", "Gorou", "The General","15-07-2000")
    )

    /**
     * val shoppingList = mutableListOf(
    ShoppingListItem("Cucumbers 🥒", 1),
    ShoppingListItem("Tomatoes 🍅", 2),
    ShoppingListItem("Orange Juice 🍊", 3)
    )
     */


    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
            route(User.path) {
                get {
                    call.respond(users)
                }
                post {
                    users += call.receive<User>()
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                    users.removeIf { it.id == id }
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }.start(wait = true)
}