import React from 'react';
import FerryService from "../services/ProductService";


class FerryUpdateComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            ferryId: '',
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

    idChange = event =>{
        this.setState({
            ferryId: event.target.value
        });
    }

    handleSubmit = event =>{
        event.preventDefault();

        const ferry = {
            "ferryId" : this.state.ferryId,
            "ferryName" : this.state.ferryName
        };
        FerryService.updateFerry(ferry);
    }
    render(){
        return(
            <div id={'ferrydiv'}>
                <h6 className = "text-center">Update</h6>
                <form onSubmit={this.handleSubmit}>
                        <input placeholder={'Bitte ID eingeben'} type="text" name="iDInput" onChange={this.idChange}/>
                        <input placeholder={'Bitte Namen eingeben'} type="text" name="nameInput" onChange={this.nameChange}/>
                    <button id="guiButtons">Update Ferry</button>
                </form>
            </div>
        )

    };

}

export default FerryUpdateComponent;