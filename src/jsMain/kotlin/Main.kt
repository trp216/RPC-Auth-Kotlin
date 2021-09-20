import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    console.log(document.URL)

    when(document.URL){
        "http://localhost:9090/" -> render(document.getElementById("root")) { child(SignInChild) }
        "http://localhost:9090/signup" -> render(document.getElementById("root")) { child(SignUpChild) }
    }


}