import React, {useState} from 'react';
import axios from "axios";
import {Link, useHistory} from "react-router-dom";


function UserComponentPOST(){
    const history = useHistory();
    const [username, setusername] = useState("");
    const [password, setpassword] = useState("");
    const [authenticated, setauthenticated] = useState(sessionStorage.getItem(sessionStorage.getItem("authenticated")|| false));
    const loginbox = document.getElementById("creatCustomerBox");
    const handleSubmit = async event =>{
        event.preventDefault()

        try {
            const res = await axios.post("http://localhost:8080/api/auth/signin", {
                "usernameOrEmail": username,
                "password": password
            })
            if (res.status === 200){
                setauthenticated("true");
                sessionStorage.setItem("authenticated", "true");
                sessionStorage.setItem("user", username);
                loginbox.append("Anmeldung erfolgreich");
                history.push("/");
                window.location.reload();
            }
        }catch (err){
            setauthenticated("false");
            sessionStorage.setItem("authenticated", "false");
            sessionStorage.setItem("user", "not authorized");
            console.log(err);
            loginbox.append("Anmeldung fehlgeschlagen");
        }
    };
    return (
        <div id={"creatCustomerBox"}>
            <form onSubmit={handleSubmit}>
                <input placeholder={'Username or Email'} type="text" name="usernameInput" onChange={event => setusername(event.target.value)} required/>
                <input placeholder={'Passwort'} type="password" name="passwordInput" onChange={event => setpassword( event.target.value)} required/>
                <button value="Submit" id="toProductButton">Anmelden</button>
                <button id="registerButton"><Link to="/registration">Jetzt registrieren!</Link></button>
            </form>
        </div>
    )
};

export default UserComponentPOST;