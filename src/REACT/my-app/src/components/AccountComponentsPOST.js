import React from 'react';
import CustomerService from "../services/CustomerService";


class AccountComponentPOST extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            email: props.email,
            firstName: props.firstName,
            lastName: props.lastName,
            phoneNumber: props.phoneNumber,
            password: props.password
        }
    }

    handleSubmit = event =>{
        event.preventDefault()
        const customer = {
            "email": this.state.email,
            "firstName": this.state.firstName,
            "lastName": this.state.lastName,
            "phoneNumber": this.state.phoneNumber,
            "password": this.state.password
        };
        CustomerService.createCustomer(customer);
    }
    render(){
        return(
            <div id={"creatCustomerBox"}>
            <form onSubmit={this.handleSubmit}>
                <input placeholder={'Vorname'} type="text" name="firstnameInput" onChange={e => this.setState({ firstName: e.target.value })} required/>
                <input placeholder={'Nachname'} type="text" name="lastnameInput" onChange={e => this.setState({ lastName: e.target.value })} required/>
                <input placeholder={'E-Mail'} type="email" name="emailInput" onChange={e => this.setState({ email: e.target.value })} required/>
                <input placeholder={'Passwort'} type="password" name="passwordInput" onChange={e => this.setState({ password: e.target.value })} required/>
                <input placeholder={'Straße, Hausnummer'} type="text" name="adressInput" onChange={e => this.setState({ address: e.target.value })} required/>
                <input placeholder={'Telefonnummer'} type="tel" name="phonenumberInput" onChange={e => this.setState({ phoneNumber: e.target.value })}/>
                <select>
                    <option value={"Male"}>Male</option>
                    <option value={"Female"}>Female</option>
                    <option value={"Diverse"}>Diverse</option>
                </select>
                <button id="toProductButton">Account erstellen</button>
            </form>
            </div>
        )

    };

}

export default AccountComponentPOST;