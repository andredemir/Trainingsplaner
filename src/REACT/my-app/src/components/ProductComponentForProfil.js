import React from 'react';
import "../App.css";
import productImage from "../img/png/no-image-icon-6.png"
class ProductComponenet extends React.Component {

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
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
                <div id={"productButtonSection"}>
                    <button id={"pauseButton"}>
                        pausieren
                    </button>
                    <button id={"deleteButton"}>
                        löschen
                    </button>
                </div>
            </div>
        )

    }

}

export default ProductComponenet;