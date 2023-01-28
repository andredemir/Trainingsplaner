import React, {useState} from 'react';
import {Redirect, useHistory} from "react-router-dom";
import axios from "axios";
function ProductComponentsPOST() {
    const authenticated = sessionStorage.getItem("authenticated");
    const history = useHistory();
    const [name, setName] = useState("");
    const [beschreibung, setBeschreibung] = useState("");
    const [preis, setPreis] = useState("");
    const [postImage, setPostImage] = useState("");


    const optionValue = document.getElementById("sortByElementCategory");
    const loginbox = document.getElementById("productDetailsSection");
    const convertToBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(file);
            fileReader.onload = () => {
                resolve(fileReader.result);
            };
            fileReader.onerror = (error) => {
                reject(error);
            };
        });
    };
    const handleFileUpload = async (e) => {
        const file = e.target.files[0];
        const base64 = await convertToBase64(file);
        setPostImage(base64);
    };
    const handleSubmit = async event => {
        event.preventDefault()
        try {
            const res = await axios.post("http://localhost:8080/products", {
                "username": sessionStorage.getItem("user"),
                "product": {
                    "name": name,
                    "beschreibung": beschreibung,
                    "preis": preis,
                    "erstellungsdatum": new Date(),
                    "productImage": postImage,
                    "productCategory": optionValue.value.toString().toUpperCase()
                }
            })
            if (res.status === 201) {
                console.log("Daten erfolgreich geändert");
                loginbox.append("Registration erfolgreich");
                history.push("/profil");
            }
        } catch (err) {
            loginbox.append("Registrierung fehlgeschlagen");
        }
    }

    {
        if (!authenticated) {
            return <Redirect to={"/login"}></Redirect>
        } else
            return (
                <div id={"createProductSection"}>
                    <div id={"productDetailsSection"}>
                        <div id={"fileDisplayArea"}>

                        </div>
                        <h1>
                            Anzeige erstellen
                        </h1>
                        <form onSubmit={handleSubmit}>
                            <input placeholder={'Titel'} type="text" name="nameInput"
                                   onChange={event => setName(event.target.value)} required/>
                            <input placeholder={'Preis'} type="number" step={"0.01"} name="preisInput"
                                   onChange={event => setPreis(event.target.value)} required/>
                            <select id={"sortByElementCategory"} name={"productCategory"}>
                                <option value={"dienstleistung"}>Dienstleistung</option>
                                <option value={"fahrzeuge"}>Fahrzeuge</option>
                                <option value={"moebel"}>Möbel</option>
                                <option value={"sport"}>Sport</option>
                                <option value={"haus"}>Haus</option>
                                <option value={"elektronik"}>Elektronik</option>
                                <option value={"baumarkt"}>Baumarkt</option>
                                <option value={"buecher"}>Bücher</option>
                                <option value={"spiele"}>Spiele</option>
                                <option value={"filme"}>Filme</option>
                                <option value={"lebensmittel"}>Lebensmittel</option>
                                <option value={"textilien"}>Textilien</option>
                                <option value={"haustiere"}>Haustiere</option>
                                <option value={"spielzeug"}>Spielzeug</option>
                                <option value={"tickets"}>Tickets</option>
                                <option value={"kunst"}>Kunst</option>
                                <option value={"sonstiges"}>Sonstiges</option>
                            </select>
                            <textarea placeholder={'Beschreibung'} name="descriptionInput"
                                      onChange={event => setBeschreibung(event.target.value)} required/>
                            <button id="toProductButton">Produkt erstellen</button>
                            <input
                                type="file"
                                label="Image"
                                name="myFile"
                                accept=".jpeg, .png, .jpg, .gif"
                                onChange={(e) => handleFileUpload(e)}
                            />
                        </form>
                    </div>
                </div>
            )

    }
}

export default ProductComponentsPOST;