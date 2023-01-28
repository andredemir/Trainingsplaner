import React, {useEffect, useState} from 'react';
import CustomerService from "../services/CustomerService";
import {Redirect, useHistory} from "react-router-dom";
import axios from "axios";


function UserComponentPOST() {
    const history = useHistory();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [paswword, setPassword] = useState("");
    const loginbox = document.getElementById("creatCustomerBox");
    const handleSubmit = async event =>{
        event.preventDefault()
        try {
            const res = await axios.post("http://localhost:8080/api/auth/signup/", {
                "username": username,
                "email": email,
                "password": paswword

            })
            if (res.status === 200){
                console.log("Erfolgreich registriert");
                loginbox.append("Registration erfolgreich");
                history.push("/login");
            }
        }catch (err){
            loginbox.append("Registrierung fehlgeschlagen");
        }
    };
        return(
            <div id={"creatCustomerBox"}>
            <form onSubmit={handleSubmit}>
                <input placeholder={'Username'} type="text" name="usernameInput" onChange={event => setUsername(event.target.value)} required/>
                <input placeholder={'E-Mail'} type="email" name="emailInput" onChange={event => setEmail(event.target.value)} required/>
                <input placeholder={'Passwort'} type="password" name="passwordInput" onChange={event => setPassword(event.target.value)} required/>
                <button id="toProductButton">Account erstellen</button>
            </form>
            </div>
        )
}

export default UserComponentPOST;