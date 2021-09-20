import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val App = functionalComponent<RProps> { _ ->
    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }
    h1 {
        +"It is into a table :D"
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

    child(
        InputComponent,
        props = jsObject {
            onSubmit = { username, password, firstname, lastname, birthdate ->
                val user = User(username, password, firstname, lastname, birthdate)
                scope.launch {
                    addUsers(user)
                    setUsers(getUsers())
                }
            }
        }
    )

}