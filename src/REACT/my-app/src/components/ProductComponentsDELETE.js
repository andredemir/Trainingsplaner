import React from 'react';
import FerryService from "../services/ProductService";
import ProductService from "../services/ProductService";


class ProductDeleteComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            productId: ''
        }
    }

    componentDidMount(){

    }

    idChange = event =>{
        this.setState({
            productId: event.target.value
        });
    }

    handleSubmit = event =>{
        event.preventDefault();

        const productId  = this.state.productId
        ProductService.deleteProduct(productId);
    }
    render(){
        return(
            <div id={'product_landing_page_box'}>
                <h6 className = "text-center">Delete</h6>
                <form onSubmit={this.handleSubmit}>
                        <input placeholder={'Bitte ID eingeben'} type="text" name="idInput" onChange={this.idChange}/>
                    <button id="guiButtons">Delete Ferry</button>
                </form>
            </div>
        )

    };

}

export default FerryDeleteComponent;