import react.*
import react.dom.*
import kotlinx.html.js.*
import kotlinx.html.InputType
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

external interface SignInProps : RProps {
    var onSubmit: (String, String) -> Unit
}

val SignInComponent = functionalComponent<SignInProps> { props ->
    val (username, setUsername) = useState("")
    val (password, setPassword) = useState("")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        props.onSubmit(username, password)
        setUsername("")
        setPassword("")
    }

    val usernameHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setUsername(value)
    }
    val passwordHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setPassword(value)
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
        br {  }
        br {  }
        input(InputType.button) {
            attrs.onClickFunction = submitHandler
            attrs.value = "Sign in"
        }

    }
}