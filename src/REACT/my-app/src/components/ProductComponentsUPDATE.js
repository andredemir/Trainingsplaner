import React, {useState} from 'react';
import axios from "axios";


function ProductUpdateComponent() {
    const [titel, setTitel] = useState("");
    const [preis, setPreis] = useState("");
    const [beschreibung, setBeschreibung] = useState("");

    const currProductId = sessionStorage.getItem("ProductIdForEditing");
    const loginbox = document.getElementById("creatCustomerBox");
    const handleSubmit = async event => {
        event.preventDefault()
        const productToUpdate = {
            "name": titel,
            "preis": preis,
            "productCategory": document.getElementById("sortByElementCategory").value.toString().toUpperCase(),
            "beschreibung" : beschreibung
        }
        try {
            const res = await axios.put("http://localhost:8080/products/", {
                "productId": currProductId,
                "product": productToUpdate
            })
            if (res.status === 200) {
                loginbox.append("Daten erfolgreich geändert");
            }
        } catch (err) {
            console.log("fehlgeschlagen")
            loginbox.append("Änderung fehlgeschlagen")
        }
    };
        return (
            <div id={"creatCustomerBox"}>
                <h1>
                    Produkt updaten
                </h1>
                <form onSubmit={handleSubmit}>
                    <input placeholder={'Titel'} type="text" name="vornameInput"
                           onChange={event => setTitel(event.target.value)} required/>
                    <input placeholder={'Preis'} type="text" name="usernameInput"
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
                    <input placeholder={'Beschreibung'} type="text" name="usernameInput"
                           onChange={event => setBeschreibung(event.target.value)} required/>
                    <button value="Submit" id="toProductButton">Daten ändern</button>
                </form>
            </div>
        )
}

export default ProductUpdateComponent;