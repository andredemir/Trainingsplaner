import React from 'react';
import "../App.css";
import searchIcon from "../img/png/magnifying-glass.png"
class SearchComponent extends React.Component {

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
            <div id={"searchContainer"}>
                <img src={searchIcon} id={"searchIcon"} alt={"Search Icon"}/>
                <input id={"searchInput"} placeholder={"Finde dein Produkt"}/>
                <button id={"searchButton"}>
                    Suchen
                </button>
            </div>
        )

    }

}

export default SearchComponent;