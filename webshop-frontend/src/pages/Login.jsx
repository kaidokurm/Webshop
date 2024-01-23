import {useRef} from "react";
import {useNavigate} from "react-router-dom";

function Login() {
    const emailRef = useRef();
    const passwordRef = useRef();
    const navigate = useNavigate();

    function signIn() {

        const userData = {
            email: emailRef.current.value,
            password: passwordRef.current.value
        }

        fetch("http://localhost:8080/login", {
            method: "POST",
            body: JSON.stringify(userData),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status === 200) {
                return res.json();
            } else {
                console.log(res);
                throw Error();
            }
        })
            .then(body => {
                sessionStorage.setItem("authData", JSON.stringify(body));
                navigate("/");
                window.location.reload();
            });
    }

    return (
        <div>
            <label>Email</label> <br/>
            <input ref={emailRef} type="text"/> <br/>
            <label>Parool</label> <br/>
            <input ref={passwordRef} type="password"/> <br/>
            <button onClick={() => signIn()}>Logi sisse</button>
        </div>)
}

export default Login;