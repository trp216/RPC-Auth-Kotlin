import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.browser.document
import kotlinx.html.js.*
import kotlinx.coroutines.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

private val scope = MainScope()

val SignUpChild = functionalComponent<RProps> {

    val signInHandler: (Event) -> Unit = {
        render(document.getElementById("root")){
            child(SignInChild)
        }
    }

    nav {
        button {
            attrs.onClickFunction = signInHandler
            attrs.value = "Sign In"
        }
    }

    child(
        SignUpComponent,
        props = jsObject {
            onSubmit = { username, password, firstname, lastname, birthdate ->
                var newPassword = password.hashCode().toString() +"H4sh34d0"+ username.hashCode()
                val user = User(username, newPassword, firstname, lastname, birthdate)
                scope.launch {
                    addUsers(user)
                }
            }
        }
    )
}

val SignInChild = functionalComponent<RProps> {
    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }

    child(
        SignInComponent,
        props = jsObject{
            onSubmit = { username, password ->
                var tempUser = users.find { it.username == username }
                if(tempUser!=null){
                    if(tempUser.password == password)
                        render(document.getElementById("root")){
                            child(ListComponent)
                        }
                }
            }
        }
    )
}

val ListComponent = functionalComponent<RProps> {
    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }
    table {

        thead {
            tr {
                th {
                    +"Username"
                }
                th {
                    +"First name"
                }
                th {
                    +"Last name"
                }
                th {
                    +"Birthdate"
                }
            }
        }

        tbody {
            users.forEach { item ->
                tr {
                    key = item.toString()
                    td { +"${item.username}" }
                    td { +"${item.firstname}" }
                    td { +"${item.lastname}" }
                    td { +"${item.birthdate}" }
                }
                attrs.onClickFunction = {
                    scope.launch {
                        deleteUsers(item)
                        setUsers(getUsers())
                    }
                }

            }
        }
    }
}