import React from 'react';
import FerryService from "../services/ProductService";


class FerryDeleteComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            ferryId: ''
        }
    }

    componentDidMount(){

    }

    idChange = event =>{
        this.setState({
            ferryId: event.target.value
        });
    }

    handleSubmit = event =>{
        event.preventDefault();

        const ferryId  = this.state.ferryId
        FerryService.deleteFerry(ferryId);
    }
    render(){
        return(
            <div id={'ferrydiv'}>
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