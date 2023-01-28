import React, {useEffect, useState} from 'react';
import "../App.css";
import {Link, useHistory} from "react-router-dom";
import axios from "axios";
import CustomerService from "../services/CustomerService";
import ProductService from "../services/ProductService";

const productsApi = axios.create({
    baseURL: "http://localhost:8080/products"
})

const customersApi = axios.create({
    baseURL: "http://localhost:8080/customers"
})

const authenticated = sessionStorage.getItem("authenticated");

function ProductLandingPageComponent(){
    const [landingPageProducts, setLandingPageProducts] = useState([]);
    const [loadingProductPage, setLoadingProductPage] = useState(true);
    const [owner, setOwner] = useState(true);
    const navigate = useHistory();

    useEffect(() => {
        const fetchData = async () => {
            setLoadingProductPage(true);
            const res = await productsApi.get("/id/" + sessionStorage.getItem("ProductID"));
            setLandingPageProducts(res);
            setLoadingProductPage(false);
        };
        fetchData();
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            setLoadingProductPage(true);
            const res = await customersApi.get("/getcustomerbyproductid:" + sessionStorage.getItem("ProductID"));
            setOwner(res);
            setLoadingProductPage(false);
        };
        fetchData();
    }, []);


    {
        return (
            <div id={"product_landing_page_box"}>
                {!loadingProductPage && landingPageProducts.data?.map(product =>
                    <div key={product.productId} id={"product_section_box"}>
                        <img id={"productImageLandingPage"} src={product.productImage} alt={"Product Image"}></img>
                        <h5 id={"productCategory"}>
                            {product.productCategory}
                        </h5>
                        <h1 id={"productTitle"}>
                            {product.name}
                        </h1>
                        <h5>
                            {product.productId}
                        </h5>
                        <h5 id={"productPrice"}>
                            Preis: {product.preis}
                        </h5>
                        <p id={"productDescription"}>
                            {product.beschreibung}
                        </p>
                        <div id={"productDIV"}>
                            <button onClick={async function () {
                                if (!authenticated){
                                    navigate.push("/login");
                                }
                                const productWrapper =
                                    {
                                        "username": sessionStorage.getItem("user"),
                                        "productid": product.productId

                                    }
                                console.log(productWrapper);
                                try {
                                    const res = await axios.post("http://localhost:8080/customers/cart", productWrapper);
                                    console.log(res);
                                    if (res.status === 201){
                                        document.getElementById("productDIV").append("Erfolgreich hinzugefügt");
                                        navigate.push("/basket");
                                    }
                                } catch (err) {
                                    console.log(err);
                                    document.getElementById("productDIV").append("Produkt schon im Warenkorb oder eigenes!");
                                }
                            }} id={"toProductButton"}>
                                In den Warenkorb
                            </button>
                        </div>
                    </div>
                )}
                <div id={"userBox"}>
                    {!loadingProductPage && owner.data?.map(owner =>
                        <div key={owner.customerId}>
                            <h5>Verkäufer: {owner.username}</h5>
                            <h2 id={"seller"}>
                            </h2>
                            <h5 id={"bewertungen"}>
                                Vorname: {owner.firstName === null? "": owner.firstName}
                            </h5>
                            <h5 id={"gesamteAnzeigen"}>
                                Nachname: {owner.lastName === null? "": owner.lastName}
                            </h5>
                            <h5 id={"anzahlAnzeigenOnline"}>
                                Tel.: {owner.phoneNumber == null ? " " : owner.phoneNumber.countryCode + " " + owner.phoneNumber.subscriberNumber}
                            </h5>
                            <button id={"toProfilButton"}>
                                <Link to={"/sellerprofil"}>Zum Profil</Link>
                            </button>
                        </div>
                    )}
                </div>
            </div>
        )

    }

}

export default ProductLandingPageComponent;