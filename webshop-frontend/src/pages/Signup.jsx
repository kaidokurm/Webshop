import {useRef, useState} from "react";
import {Alert} from "react-bootstrap";
import {useNavigate} from "react-router-dom";


function Signup() {
    const personCodeRef = useRef();
    const emailRef = useRef();
    const firstNameRef = useRef();
    const lastNameRef = useRef();
    const phoneRef = useRef();
    const passwordRef = useRef();
    const passwordRepeatRef = useRef();
    const [errorMessage, setError] = useState("");
    const navigate = useNavigate();

    function createNewUser() {
        if (passwordRef.current.value !== passwordRepeatRef.current.value) {
            setError("Paroolid ei ühti!");
            return;
        }
        setError("");

        if (personCodeRef.current.value === "" ||
            emailRef.current.value === "" ||
            firstNameRef.current.value === "" ||
            lastNameRef.current.value === "" ||
            passwordRef.current.value === "") {
            setError("Kohustuslikud väljad on täitmata!");
            return;
        }

        const newUser = {
            personCode: personCodeRef.current.value,
            email: emailRef.current.value,
            firstName: firstNameRef.current.value,
            lastName: lastNameRef.current.value,
            phone: phoneRef.current.value,
            password: passwordRef.current.value,
        }

        fetch("http://localhost:8080/signup", {
            method: "POST",
            body: JSON.stringify(newUser),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status === 200) {
                navigate("/logi-sisse");
            } else {
                return res.json();
            }
        }).then(body => setError("E-mail on juba kasutusel"));
    }


    return (
        <div>
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <label>Isikukood*</label> <br/>
            <input ref={personCodeRef} type="number"/> <br/>
            <label>Email*</label> <br/>
            <input ref={emailRef} type="text"/> <br/>
            <label>Eesnimi*</label> <br/>
            <input ref={firstNameRef} type="text"/> <br/>
            <label>Perenimi*</label> <br/>
            <input ref={lastNameRef} type="text"/> <br/>
            <label>Telefon</label> <br/>
            <input ref={phoneRef} type="text"/> <br/>
            <label>Parool*</label> <br/>
            <input ref={passwordRef} type="password"/> <br/>
            <label>Korda parooli*</label> <br/>
            <input ref={passwordRepeatRef} type="password"/> <br/>
            <button onClick={() => createNewUser()}>Registreeru</button>
        </div>)
}

export default Signup;