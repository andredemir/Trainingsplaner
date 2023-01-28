import axios from 'axios';
import FerryrComponentsPOST from "../components/FerryrComponentsPOST";
import ProductComponentsUPDATE from "../components/ProductComponentsUPDATE";
import ProductComponentsPOST from "../components/ProductComponentsPOST";

const PRODUCT_REST_API_URL = 'http://localhost:8080/products/';
const PRODUCT_CART = 'http://localhost:8080/customers/cart';
const PRODUCTS_FROM_USER = 'http://localhost:8080/customers/productsfrom:';
class ProductService {

    getProducts() {
        return axios.get(PRODUCT_REST_API_URL)
    }
    getProductsFromUser(user){
        return axios.get(PRODUCTS_FROM_USER+user).then(res => console.log(res))
    }

    createProduct(productwrapper) {
        axios.post(PRODUCT_REST_API_URL,
        productwrapper).then(r => console.log(r))
    }

    putIntoCart(productWrapper){
        axios.post(PRODUCT_CART,
            productWrapper).then(r => console.log(r))
    }

    createProductWithInput(product) {
        axios.post(PRODUCT_REST_API_URL, {
            'productName': product.productName
        })
            .then(res =>{
                console.log(res);
                console.log(res.data);
            })
        ProductComponentsPOST.render();
    }

    getProductByName(name) {
        return axios.get(PRODUCT_REST_API_URL + name)
    }

    getProduct(i) {
        return axios.get(PRODUCT_REST_API_URL + i)
    }

    deleteProduct(productID) {
        return axios.delete(PRODUCT_REST_API_URL + productID)
    }

    updateProduct(product) {
        axios.put(PRODUCT_REST_API_URL, {
            'productId' : product.productId,
            'productName': product.productId
        })
            .then(res =>{
                console.log(res);
                console.log(res.data);
            })
        ProductComponentsUPDATE.render();
    }
}
export default new ProductService();