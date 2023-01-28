import React, {useEffect, useState} from 'react';
import "../App.css";
import productImage from "../img/png/no-image-icon-6.png"
import {Link, Redirect} from "react-router-dom";
import axios from "axios";
import CustomerService from "../services/CustomerService";
import ProductService from "../services/ProductService";
import Button from "bootstrap/js/src/button";

const productsApi = axios.create({
    baseURL: "http://localhost:8080/customers"
})

function BasketComponent() {
    const [landingPageProducts, setLandingPageProducts] = useState([]);
    const [loadingProductPage, setLoadingProductPage] = useState(true);
    const auth = sessionStorage.getItem("authenticated");

    useEffect(() => {
        const fetchData = async () => {
            setLoadingProductPage(true);
            const res = await productsApi.get("cartfrom:" + sessionStorage.getItem("user"));
            setLandingPageProducts(res);
            setLoadingProductPage(false);
        };
        fetchData();
    }, []);

    {
        if (!auth) {
            return <Redirect to={"/login"}></Redirect>
        } else {
            return (
                <div id={"basketSection"}>
                    <h1>
                        Warenkorb:
                    </h1>
                    {!loadingProductPage && landingPageProducts.data?.map(product =>
                            <div key={product.productId} id={"warenkorbProducts"}>
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
                                    <button onClick={function () {
                                        axios.delete("http://localhost:8080/customers/cart/",
                                            {
                                                data: {
                                                    username: sessionStorage.getItem("user"),
                                                    productid: product.productId
                                                }
                                            }).then(r => console.log(r))
                                        window.location.reload();
                                    }} id={"deleteButton2"}>
                                        Löschen
                                    </button>
                                </div>
                        </div>
                    )}
                    <button id={"toProductButton"} onClick={function () {
                        try {
                            axios.post("http://localhost:8080/customers/buycartfrom:" + sessionStorage.getItem("user")
                            ).then(r => console.log(r))
                            window.location.reload()
                        }catch (err){
                            console.log(err)
                        }
                    }}>
                        Alles kaufen
                    </button>
                </div>
            )
        }
    }
}

export default BasketComponent;