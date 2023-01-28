import React from 'react';
import FerryService from "../services/ProductService";

class FerryComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            ferries:[]
        }
    }

    componentDidMount(){
        FerryService.getFerrys().then(
            (response) => {
                this.setState({ ferries: response.data})
            }
        )
    }
    render(){
        return(
            <div id={'ferrydiv'}>
                <h1 className = "text-center">Ferry List</h1>
                <table className = "table table-striped">
                    <thead>
                    <tr>
                        <td>Ferry Id</td>
                        <td>Ferry Name</td>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        this.state.ferries.map(
                            ferry =>
                                <tr key = {ferry.ferryId}>
                                    <td>{ferry.ferryId}</td>
                                    <td>{ferry.ferryName}</td>
                                </tr>
                        )
                    }
                    </tbody>

                </table>
                <button id={'guiButtons'} onClick={FerryService.createFerry}>
                    Add without inputs
                </button>
            </div>
        )
    }
}

export default FerryComponent;