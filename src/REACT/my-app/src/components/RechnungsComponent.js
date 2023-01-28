import React, {useEffect, useState} from 'react';
import "../App.css";
import {Redirect} from "react-router-dom";
import axios from "axios";
import ProductService from "../services/ProductService";
import {data} from "express-session/session/cookie";


const customerProductsApi = axios.create({
    baseURL: "http://localhost:8080/customers"
})

function RechnungsComponent() {
    const user = sessionStorage.getItem("user");
    const [rechungen, setRechnungen] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const res = await customerProductsApi.get("/billsfrom:" + user);
            setRechnungen(res);
            setLoading(false);
        };
        fetchData();
    }, []);

    /**
     * variable to store the data in the state Array
     * @type {Promise<AxiosResponse<any>>}
     */
    return (
        <div id={"billClassSection"}>
            <h1>Alle Rechnungen auf einen Blick</h1>
            <div id={"rechnungSection"}>
                {!loading && rechungen.data?.map(rechnung =>
                    <div key={rechnung.billId} id={"billSection"}>
                        <h5>
                            RechnungsID: {rechnung.billId}
                        </h5>
                        <h5>
                            Rechnungsdatum: {rechnung.date}
                        </h5>
                        <h5>
                            Rechnungsinhalt:
                        </h5>
                        <p>
                            {rechnung.billContent}
                        </p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default RechnungsComponent;