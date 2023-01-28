import React from 'react';
import CustomerService from '../services/CustomerService';

class CustomerComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            customers:[]
        }
    }

    componentDidMount(){
        CustomerService.getCustomers().then(
            (response) => {
                this.setState({ customers: response.data})
            }
        )
    }

    render(){
        return(
            <div>
                <h1 className = "text-center">Customers List</h1>
                <table className = "table table-striped">
                    <thead>
                    <tr>
                        <td>Customer Id</td>
                        <td>Customer First Name</td>
                        <td>Customer Last Name</td>
                        <td>Customer Email</td>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        this.state.customers.map(
                            customer =>
                                <tr key = {customer.id}>
                                    <td>{customer.id}</td>
                                    <td>{customer.firstName}</td>
                                    <td>{customer.lastName}</td>
                                    <td>{customer.email}</td>
                                </tr>
                        )
                    }
                    </tbody>

                </table>
                <button onClick={CustomerService.createCustomer2}>
                    add without input
                </button>
                <h6>
                    firstName
                </h6>
                <input name={'firstNameInput'}></input>
                <h6>
                    lastName
                </h6>
                <input name={'lastNameInput'}></input>
                <h6>
                    E-Mail
                </h6>
                <input name={'emailInput'}></input>

                <button onClick={CustomerService.createCustomer}>
                    add
                </button>
                <h6>
                    show Customer
                </h6>
                <input name={'idInput'}></input>
                <button onClick={CustomerService.getCustomer}>
                    show customer with ID
                </button>
            </div>
        )

    }

}

export default CustomerComponent;