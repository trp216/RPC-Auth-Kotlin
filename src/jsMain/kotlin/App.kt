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
        +"Put this into a table :D"
    }
    ul {
        users.sortedByDescending(User::username).forEach { item ->
            li {
                key = item.toString()
                +"${item.username} ${item.firstname} ${item.lastname} ${item.birthdate}"
            }
            attrs.onClickFunction = {
                scope.launch {
                    deleteUsers(item)
                    setUsers(getUsers())
                }
            }
        }
    }

    var cartItem = User("","","","","")
    h4 {
        +"Username:"
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                cartItem.username = input
                scope.launch {
                    username(input)
                }
            }
        }
    )

    h4 {
        +"Password:"
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                cartItem.password = input
                scope.launch {
                    password(input)
                }
            }
        }
    )

    h4 {
        +"First name:"
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                cartItem.firstname = input
                scope.launch {
                    firstname(input)
                }
            }
        }
    )

    h4 {
        +"Last name:"
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                cartItem.lastname = input
                scope.launch {
                    lastname(input)
                }
            }
        }
    )

    h4 {
        +"Birthdate (dd-mm-yyyy):"
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                cartItem.birthdate = input
                scope.launch {
                    birthdate(input)
                    addUsers()
                    setUsers(getUsers())
                }
            }
        }
    )
}