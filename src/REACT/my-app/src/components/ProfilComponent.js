import React, {useEffect, useState} from 'react';
import "../App.css";
import {Link, Redirect, Route} from "react-router-dom";
import axios from "axios";
import ProductService from "../services/ProductService";
import {data} from "express-session/session/cookie";


const customerProductsApi = axios.create({
    baseURL: "http://localhost:8080/customers"
})

function ProfilComponent(){
    const [authenticated, setauthenticated] = useState(sessionStorage.getItem("authenticated"));
    const user = sessionStorage.getItem("user");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [curUser, setCurUser] = useState(true);

    useEffect(() => {
        const loggedInUser = sessionStorage.getItem("authenticated");
        if (loggedInUser) {
            setauthenticated(loggedInUser);
        }
    }, []);

    useEffect(()=>{
        const fetchData = async () => {
            setLoading(true);
            const res = await customerProductsApi.get("/productsfrom:" + user);
            setProducts(res);
            setLoading(false);
        };
        fetchData();
    }, []);

    useEffect(()=>{
        const fetchData = async () => {
            setLoading(true);
            const res = await customerProductsApi.get("/usernamelist:" + user);
            setCurUser(res);
            setLoading(false);
        };
        fetchData();
    }, []);



    /**
     * variable to store the data in the state Array
     * @type {Promise<AxiosResponse<any>>}
     */
    //state.customerProducts = ProductService.getProductsFromUser(sessionStorage.getItem("user"));
    if (!authenticated) {
        return <Redirect to={"/login"}></Redirect>
    } else {
        return (
            <div id={"profilSection"}>
                {!loading && curUser.data?.map(user =>
                    <div key={user.customerId} id={"userProfilBox"}>
                        <h5>Verkäufer:</h5>
                        <h2 id={"seller"}>
                            {user.username}
                        </h2>
                        <h5 id={"bewertungen"}>
                            Vorname: {user.firstName}
                        </h5>
                        <h5 id={"gesamteAnzeigen"}>
                            Nachname: {user.lastName}
                        </h5>
                        <h5 id={"nutzerArt"}>
                            Telefon: {user.phoneNumber == null ? " " : user.phoneNumber.countryCode + " " + user.phoneNumber.subscriberNumber}
                        </h5>
                        <h5 id={"anzahlAnzeigenOnline"}>
                            Adresse: {user.address == null ? " " : user.address.street + " " + user.address.houseNumber}
                        </h5>
                    </div>
                )}
                <div id={"productSection"}>
                    {!loading && products.data?.map(product =>
                        <div key={product.productId} id={"productBox"}>
                            <img src={product.productImage} alt={"Product Image"}></img>
                            <h5>
                                {product.productCategory}
                            </h5>
                            <h1>
                                {product.name}
                            </h1>
                            <h5>
                                Preis: {product.preis}
                            </h5>
                            <p>
                                {product.beschreibung}
                            </p>
                            <div id={"productButtonSection"}>
                                <button onClick={function (){
                                    {sessionStorage.setItem("ProductIdForEditing", product.productId)}
                                }} id={"pauseButton"}>
                                    <Link to={"/updateproduct"}>
                                        bearbeiten
                                    </Link>
                                </button>
                                <button onClick={function (){
                                    axios.delete("http://localhost:8080/products/delete/",
                                        {data: {
                                                username: sessionStorage.getItem("user"),
                                                productid: product.productId
                                            }
                                }).then(r => console.log(r))
                                    window.location.reload();
                                }} id={"deleteButton"}>
                                    löschen
                                </button>
                            </div>
                        </div>
                    )
                    }
                </div>
            </div>
        )
    }
}

export default ProfilComponent;