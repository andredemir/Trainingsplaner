import React from 'react';
import "../App.css";
import productImage from "../img/png/no-image-icon-6.png"
import {Link} from "react-router-dom";
class ProductComponenet extends React.Component {

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
            <div>
                <div id={"productBox"}>
                    <img src={productImage} alt={"Product Image"}></img>
                    <h5>
                        Elektronik
                    </h5>
                    <h1>
                        LG QLED 60 Zoll
                    </h1>
                    <h5>
                        Preis: 800€
                    </h5>
                    <p>
                        Aenean consectetur odio in condimentum tristique. Nam hendrerit urna ex, non pretium erat pellentesque eget. Sed ut risus nec augue sagittis convallis.
                    </p>
                    <div>
                        <button id={"toProductButton"}>
                            <Link to={"/productLandingPage"}>Zum Produkt</Link>
                        </button>
                        <button id={"makeOfferButton"}>
                            <a href={""}>Angebot machen</a>
                        </button>
                    </div>
                </div>
            </div>
        )

    }

}

export default ProductComponenet;