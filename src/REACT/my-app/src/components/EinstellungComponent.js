import React, {useState} from 'react';
import CustomerService from "../services/CustomerService";
import {Redirect, useHistory} from "react-router-dom";
import HeaderComponent from "./HeaderComponent";
import axios from "axios";


function UserComponentPOST(){
    const [vorname, setVorname] = useState("");
    const [nachname, setNachname] = useState("");
    const [adresse, setAdresse] = useState("");
    const [houseNumber, sethouseNumber] = useState("");
    const [postalCode, setpostalCode] = useState("");
    const [city, setCity] = useState("");
    const [telefonnummer, setTelefonnummer] = useState("");
    const [countryCode, setCountryCode] = useState("");

    const authenticated = sessionStorage.getItem("authenticated");
    const currUser = sessionStorage.getItem("user");
    const loginbox = document.getElementById("creatCustomerBox");
    const handleSubmit = async event =>{
        event.preventDefault()
        const userToUpdate = {
            "firstName": vorname,
            "lastName": nachname,
            "phoneNumber": {
                "countryCode": countryCode,
                "subscriberNumber": telefonnummer,
                "restNumber" : ""
            },
            "address" : {
                "street" : adresse,
                "houseNumber" : houseNumber,
                "postalCode" : postalCode,
                "city" : city
            }
        }
        try {
            const res = await axios.put("http://localhost:8080/customers/", {
                "username": currUser,
                "customer": userToUpdate
            })
            if (res.status === 200){
                loginbox.append("Daten erfolgreich geändert");
            }
        }catch (err){
            console.log("fehlgeschlagen")
            loginbox.append("Änderung fehlgeschlagen")
        }
    };
    if (!authenticated){
        return <Redirect to={"/login"}></Redirect>
    }else {
        return (
            <div id={"creatCustomerBox"}>
                <h1>
                    Persönliche Daten ergänzen
                </h1>
                <form onSubmit={handleSubmit}>
                    <input placeholder={'Vorname'} type="text" name="vornameInput" onChange={event => setVorname(event.target.value)} />
                    <input placeholder={'Nachname'} type="text" name="usernameInput" onChange={event => setNachname(event.target.value)} />
                    <input placeholder={'Straße'} type="text" name="usernameInput" onChange={event => setAdresse(event.target.value)} />
                    <input placeholder={'Hausnummer'} type="text" name="usernameInput" onChange={event => sethouseNumber(event.target.value)} />
                    <input placeholder={'Postleitzahl'} type="text" name="usernameInput" onChange={event => setpostalCode(event.target.value)} />
                    <input placeholder={'Stadt'} type="text" name="usernameInput" onChange={event => setCity(event.target.value)} />
                    <select onChange={event => setCountryCode(event.target.value)} >
                        <option value="" defaultValue={"disabled hidden"}>Wähle dein Country Code</option>
                        <option value="+49" defaultValue={"disabled hidden"}>Deutschland</option>
                        <option value="+60" defaultValue={"disabled hidden"}>Ungarn</option>
                        <option value="+50" defaultValue={"disabled hidden"}>Gustavland</option>
                    </select>
                    <input placeholder={'Telefonnummer'} type="tel" name="passwordInput" onChange={event => setTelefonnummer( event.target.value)} />
                    <button value="Submit" id="toProductButton">Daten ändern</button>
                </form>
            </div>
        )
    }
};

export default UserComponentPOST;