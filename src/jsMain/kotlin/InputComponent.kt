import react.*
import react.dom.*
import kotlinx.html.js.*
import kotlinx.html.InputType
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

external interface InputProps : RProps {
    var onSubmit: (String, String, String, String, String) -> Unit
}

val InputComponent = functionalComponent<InputProps> { props ->
    val (username, setUsername) = useState("")
    val (password, setPassword) = useState("")
    val (firstname, setFirstname) = useState("")
    val (lastname, setLastname) = useState("")
    val (birthdate, setBirthdate) = useState("")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        props.onSubmit(username, password, firstname, lastname, birthdate)
        setUsername("")
        setPassword("")
        setFirstname("")
        setLastname("")
        setBirthdate("")
    }

    val usernameHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setUsername(value)
    }
    val passwordHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setPassword(value)
    }
    val firstnameHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setFirstname(value)
    }
    val lastnameHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setLastname(value)
    }
    val birthdateHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setBirthdate(value)
    }


    form {
        h4 { +"Username" }
        input(InputType.text) {
            attrs.onChangeFunction = usernameHandler
            attrs.value = username
            attrs.placeholder = "Username"
        }
        h4 { +"Password" }
        input(InputType.password) {
            attrs.onChangeFunction = passwordHandler
            attrs.value = password
            attrs.placeholder = "Password"
        }
        h4 { +"Firstname" }
        input(InputType.text) {
            attrs.onChangeFunction = firstnameHandler
            attrs.value = firstname
            attrs.placeholder = "Firstname"
        }
        h4 { +"Last name" }
        input(InputType.text) {
            attrs.onChangeFunction = lastnameHandler
            attrs.value = lastname
            attrs.placeholder = "Lastname"
        }
        h4 { +"Birthdate" }
        input(InputType.date) {
            attrs.onChangeFunction = birthdateHandler
            attrs.value = birthdate
        }
        br {  }
        br {  }
        input(InputType.button) {
            attrs.onClickFunction = submitHandler
            attrs.value = "Sign in"
        }

    }
}