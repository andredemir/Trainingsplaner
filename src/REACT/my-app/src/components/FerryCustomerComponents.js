import React from 'react';
import FerryCustomerService from "../services/CustomerService";

class FerryCustomerComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            customers:[]
        }
    }

    componentDidMount(){
        FerryCustomerService.getFerryCustomers().then(
            (response) => {
                this.setState({ customers: response.data})
            }
        )
    }

    render(){
        return(
            <div>

            </div>
        )

    }

}

export default FerryCustomerComponent;