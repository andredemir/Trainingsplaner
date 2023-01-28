import './App.css';
import './normalize.css'
import HeaderComponent from "./components/HeaderComponent";
import ContentComponent from "./components/ContentComponent";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import ProductComponentsPOST from "./components/ProductComponentsPOST";
import AccountComponentPOST from "./components/AccountComponentsPOST";
import ProductLandingPageComponent from "./components/ProductLandingPageComponent";
import ProfilComponent from "./components/ProfilComponent";
import BasketComponent from "./components/BasketComponent";
import UserComponentsPOST from "./components/UserSignUpComponentsPOST";
import UserLoginComponentsPOST from "./components/UserLoginComponentsPOST";
import Login from "./components/LogInComponent";
import EinstellungComponent from "./components/EinstellungComponent";
import SellerProfilComponent from "./components/SellerProfilComponent";
import RechnungsComponent from "./components/RechnungsComponent";
import ProductComponentsUPDATE from "./components/ProductComponentsUPDATE";

function App() {
    return (
        <Router>
            <div className="App" id={"App"}>
                <HeaderComponent/>
                <div className={"content"}>
                    <Switch>
                        <Route exact path={"/"}>
                            <ContentComponent />
                        </Route>
                        <Route exact path={"/createProduct"}>
                            <ProductComponentsPOST/>
                        </Route>
                        <Route exact path={"/createAccount"}>
                            <AccountComponentPOST/>
                        </Route>
                        <Route exact path={"/productLandingPage"}>
                            <ProductLandingPageComponent/>
                        </Route>
                        <Route exact path={"/profil"}>
                            <ProfilComponent/>
                        </Route>
                        <Route exact path={"/basket"}>
                            <BasketComponent/>
                        </Route>
                        <Route exact path={"/registration"}>
                            <UserComponentsPOST/>
                        </Route>
                        <Route exact path={"/login"}>
                            <UserLoginComponentsPOST/>
                        </Route>
                        <Route exact path={"/loginTest"}>
                            <Login/>
                        </Route>
                        <Route exact path={"/einstellungen"}>
                            <EinstellungComponent/>
                        </Route>
                        <Route exact path={"/sellerprofil"}>
                            <SellerProfilComponent/>
                        </Route>
                        <Route exact path={"/rechnungen"}>
                            <RechnungsComponent/>
                        </Route>
                        <Route exact path={"/updateproduct"}>
                            <ProductComponentsUPDATE/>
                        </Route>
                    </Switch>
                </div>
            </div>
        </Router>
    );
}

export default App;