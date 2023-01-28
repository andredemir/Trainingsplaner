import React from 'react';
import FerryService from "../services/ProductService";


class FerryComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            ferryName: ''
        }
    }

    componentDidMount(){

    }

    nameChange = event =>{
        this.setState({
            ferryName: event.target.value
        });
    }

    handleSubmit = event =>{
        event.preventDefault();

        const ferry = {
            "ferryName": this.state.ferryName
        };
        FerryService.createFerryWithInput(ferry);
    }
    render(){
        return(
            <div id={'ferrydiv'}>
                <h6 className = "text-center">Add</h6>
                <form onSubmit={this.handleSubmit}>
                        <input placeholder={'Bitte Namen eingeben'} type="text" name="nameInput" onChange={this.nameChange}/>
                    <button id="toProductButton">Anmelden</button>
                </form>
            </div>
        )

    };

}

export default FerryComponent;