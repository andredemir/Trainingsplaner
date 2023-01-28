import React, {useEffect, useState} from 'react';
import "../App.css";
import {Link, Redirect} from "react-router-dom";
import axios from "axios";
import ProductService from "../services/ProductService";
import {data} from "express-session/session/cookie";


const customerProductsApi = axios.create({
    baseURL: "http://localhost:8080/customers"
})

const ownerApi = axios.create({
    baseURL: "http://localhost:8080/products"
})
function SellerProfilComponent(){
    const [loading, setLoading] = useState(true);
    const [products, setProducts] = useState(true);
    const [curUser, setCurUser] = useState(true);


    useEffect(()=>{
        const fetchData = async () => {
            setLoading(true);
            const res = await ownerApi.get("/getownerof/"+ sessionStorage.getItem("ProductID"));
            setCurUser(res);
            setLoading(false);
        };
        fetchData();
    }, []);

        useEffect(()=>{
            const fetchData = async () => {
                setLoading(true);
                const res = await customerProductsApi.get("/getallproductswithsameownerid:"+ sessionStorage.getItem("ProductID"));
                setProducts(res);
                setLoading(false);
            };
            fetchData();
        }, []);


    /**
     * variable to store the data in the state Array
     * @type {Promise<AxiosResponse<any>>}
     */
    //state.customerProducts = ProductService.getProductsFromUser(sessionStorage.getItem("user"));
        return (
            <div id={"profilSection"}>
                {!loading && curUser.data?.map(user =>
                    <div key={user.customerId} id={"userProfilBox"}>
                        <h5>Verkäufer:</h5>
                        <h2 id={"seller"}>
                            {user.username}
                        </h2>
                        <h5 id={"bewertungen"}>
                            Vorname: {user.firstName == null ? "" : user.firstName}
                        </h5>
                        <h5>

                        </h5>
                        <h5 id={"gesamteAnzeigen"}>
                            Nachname: {user.lastName == null ? "" : user.lastName}
                        </h5>
                        <h5 id={"nutzerArt"}>
                            Telefon: {user.phoneNumber == null ? user.phoneNumber: user.phoneNumber.countryCode + " " + user.phoneNumber.subscriberNumber}
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
                            <h5>
                                {product.productId}
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
                            <button onClick={function (){
                                sessionStorage.setItem("ProductID", product.productId)
                            }} id={"toProductButton"}>
                                <Link to={"/productLandingPage"}>Zum Produkt</Link>
                            </button>
                        </div>
                    )
                    }
                </div>
            </div>
        )
}

export default SellerProfilComponent;