import React from 'react';
import image1 from "../img/png/benutzer.png"
import logo from "../img/png/logo.png"
import "../App.css";
import {Link, Redirect} from "react-router-dom";

function showNav(){
    var t = document.getElementById("menuButton");
    if(t.value==="YES"){
        t.value="NO";
        document.getElementById("navBox").style.display = "none";
        document.getElementById("menu").style.backgroundColor = "white";
    } else if(t.value==="NO"){
        t.value="YES";
        document.getElementById("navBox").style.display = "block";
        document.getElementById("menu").style.backgroundColor = "#f5f5f5";
    }
}
class HeaderComponent extends React.Component {
    logout = () =>{
        sessionStorage.clear();
        window.location.reload();
        return <Redirect to={"/"}/>
    }
    authenticated = sessionStorage.getItem("authenticated");
    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        if (this.authenticated){
            return (
                <div id={"header"}>
                    <Link to="/">
                        <img id="logoImg" src={logo} alt={"logo"}/>
                    </Link>
                    <div id={"subsection"}>
                        <button id={"erstellAnzeigeButton"}>
                            <Link to={"/createProduct"}>Anzeige erstellen</Link>
                        </button>
                        <div id={"menu"}>
                            <button id={"menuButton"} onClick={showNav} value="NO">
                                <img id={"userImg"} src={image1} alt={"user logo"}/>
                            </button>
                            <nav id={"navBox"}>
                                <ul>
                                    <li><Link to={"/profil"}>Mein Profil</Link></li>
                                    <li onClick={this.logout}><Link to={"/"}>LogOut</Link></li>
                                    <li><Link to={"/createProduct"}>Anzeige erstellen</Link></li>
                                    <li><Link to={"/basket"}>Warenkorb</Link></li>
                                    <li><Link to={"/einstellungen"}>Einstellungen</Link></li>
                                    <li><Link to={"/rechnungen"}>Rechnungen</Link></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            )
        }else {
            return (
                <div id={"header"}>
                    <Link to={"/"}><img id={"logoImg"} src={logo} alt={"logo"}/></Link>
                    <div id={"subsection"}>
                        <button id={"erstellAnzeigeButton"}>
                            <Link to={"/createProduct"}>Anzeige erstellen</Link>
                        </button>
                        <div id={"menu"}>
                            <button id={"menuButton"} onClick={showNav} value="NO">
                                <img id={"userImg"} src={image1} alt={"user logo"}/>
                            </button>
                            <nav id={"navBox"}>
                                <ul>
                                    <li><Link to={"/profil"}>Mein Profil</Link></li>
                                    <li><Link to={"/login"}>Anmelden</Link></li>
                                    <li><Link to={"/createProduct"}>Anzeige erstellen</Link></li>
                                    <li><Link to={"/basket"}>Warenkorb</Link></li>
                                    <li><Link to={"/einstellungen"}>Einstellungen</Link></li>
                                    <li><Link to={"/registration"}>Account erstellen</Link></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            )
        }
    }

}

export default HeaderComponent;