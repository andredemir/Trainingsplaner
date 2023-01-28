import axios from 'axios';
const CUSTOMER_REST_API_URL = 'http://localhost:8080/customers/';
const USER_REST_API_URL = 'http://localhost:8080/api/auth/signup';
const CUSTOMER_REST_API_URL_REGISTRATION = 'http://localhost:8080/api/auth/signup';
const USERLOGIN_REST_API_URL = 'http://localhost:8080/api/auth/signin';
const CUSTOMERLOGIN_REST_API_URL = 'http://localhost:8080/api/auth/signin';
const USERLOGIN_REST_API_CURRENTUSER_URL = "http://localhost:3000/userobject";

class CustomerService {

    createCustomer(customer) {
        axios.post(CUSTOMER_REST_API_URL_REGISTRATION, {
            "username": customer.username,
            "email": customer.email,
            "password": customer.password
        })
    }

    createUser(user) {
        axios.post(USER_REST_API_URL, {
            "username": user.username,
            "email": user.email,
            "password": user.password
        })
    }

    /*    userLogin(user) {
            fetch(USERLOGIN_REST_API_URL, {
                method: 'POST',
                credentials: 'include',
                body: JSON.stringify({
                        'usernameOrEmail': user.usernameOrEmail,
                        'password': user.password
                    }
                )
            }).then((r) => console.log(r))*/

    userLogin(user) {
        axios.post(USERLOGIN_REST_API_URL, {
            "usernameOrEmail": user.usernameOrEmail,
            "password": user.password
        }).then(r => console.log(r))
    }

    customerLogin(user) {
        return axios.post(USERLOGIN_REST_API_URL, {
            "usernameOrEmail": user.usernameOrEmail,
            "password": user.password
        }).then(r => console.log(r))
    }

    getCurrentUser(){
        return axios.get(USERLOGIN_REST_API_CURRENTUSER_URL);
    }

    getCustomers() {
        return axios.get(CUSTOMER_REST_API_URL)
    }

    getCustomer() {
        return axios.get(CUSTOMER_REST_API_URL + this.idInput)
    }

    deleteCustomer() {
        return axios.delete(CUSTOMER_REST_API_URL)
            .then(function (response) {
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
    }
}

export default new CustomerService();