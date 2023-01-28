import React from 'react';
import "../App.css";
import axios from "axios";
import {Link} from "react-router-dom";
import searchIcon from "../img/png/magnifying-glass.png";
const productApi = axios.create({
    baseURL: "http://localhost:8080/products"
})
class ContentComponent extends React.Component {
    state = {
        products: []
    }
    constructor(props) {
        super(props);
    }
    componentDidMount() {
        this.getAllProducts()
    }
    getAllProducts = async () =>{
        let data = await productApi.get("/").then(({data}) => data);
        this.setState({products: data})
    }
    getProductsWithInput = async () =>{
        let searchInput = document.getElementById("searchInput");
        let data = await productApi.get("/suche/" +searchInput.value).then(({data}) => data);
        this.setState({products: data})
    }
    getProductsByValues = async () =>{
        let sortBy = document.getElementById("sortByElement");
        let option = sortBy.value;
        let data = await productApi.get("/sortBy" + option).then(({data}) => data);
        this.setState({products: data})
    }

    getProductsByCategory = async () =>{
        let sortBy = document.getElementById("sortByElementCategory");
        let option = sortBy.value;
        let data = await productApi.get("/searchByCategory" + option).then(({data}) => data);
        this.setState({products: data})
    }
    render() {
        return (
            <div id={"content"}>
                <h1>
                    Finde jetzt dein Produkt
                </h1>
                <div id={"searchContainer"}>
                    <img src={searchIcon} id={"searchIcon"} alt={"Search Icon"}/>
                    <input
                        defaultValue={""}
                        onChange={this.getProductsWithInput}
                        id={"searchInput"}
                        placeholder={"Finde dein Produkt"}/>
                </div>
                <div id="selectionSectionHome">
                    <select id={"sortByElement"} onChange={this.getProductsByValues}>
                        <option value="" defaultValue={"disabled hidden"}>Sortiere nach</option>
                        <option value="Name">Name</option>
                        <option value="Preis">Preis</option>
                        <option value="Erstellungsdatum">Erstellungsdatum</option>
                    </select>
                    <select onChange={this.getProductsByCategory} id={"sortByElementCategory"}>
                        <option value="" defaultValue={"disabled hidden"}>Wähle deine Kategorie</option>
                        <option value={"dienstleistung"}>Dienstleistung</option>
                        <option value={"fahrzeuge"}>Fahrzeuge</option>
                        <option value={"möbel"}>Möbel</option>
                        <option value={"sport"}>Sport</option>
                        <option value={"haus"}>Haus</option>
                        <option value={"elektronik"}>Elektronik</option>
                        <option value={"baumarkt"}>Baumarkt</option>
                        <option value={"bücher"}>Bücher</option>
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
                </div>
                <div id={"productSection"}>
                    {
                        this.state.products.map(product =>
                            <div key={product.productId} id={"productBox"}>
                                <img id={"productIMG"} src={product.productImage} alt={"product image"}/>
                                <h5>
                                    {product.productCategory}
                                </h5>
                                <h1>
                                    {product.name}
                                </h1>
                                <h5>
                                    Preis: {product.preis}
                                </h5>
                                <h5 id={"productIDValue"}>
                                     {product.productId}
                                </h5>
                                <p>
                                    {product.beschreibung}
                                </p>
                                <div>
                                    <button onClick={function (){
                                        let id = document.getElementById("productIDValue").innerText
                                        sessionStorage.setItem("ProductID", product.productId)
                                    }} id={"toProductButton"}>
                                        <Link to={"/productLandingPage"}>Zum Produkt</Link>
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

export default ContentComponent;