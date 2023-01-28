import React from 'react';

class RoutePlanningComponents extends React.Component {
    constructor(props, context) {
        super(props, context);
    }

    componentDidMount(){

    }

    render(){
        return(
            <div className="App">
                <section className="content">
                    <header>
                        <a href={'index.html'}>
                            <img id={'arrowimg'} src={require('../img/png_old/right-arrow.png')} alt={'arrow icon'}/>
                        </a>
                    </header>
                    <div className="startHafenSuche">
                        <h6>
                            Starthafen
                        </h6>
                        <div id={'destinationdiv'}>
                            <input/>
                            <img id={'destinationimg'} src={require('../img/png_old/pin.png')} alt={'pin icon'}/>
                        </div>
                    </div>
                    <div className="zielHafenSuche">
                        <h6>
                            Zielhafen
                        </h6>
                        <div id={'locationdiv'}>
                            <input/>
                            <img id={'loactionimg'} src={require('../img/png_old/destinations.png')} alt={'destination icon'}/>
                        </div>
                    </div>
                    <div className="datumSuche">
                        <h6>
                            Abfahrt
                        </h6>
                        <input type="datetime-local" name="choosenDate"/>
                    </div>
                    <button id="guiButtons">
                        Route Planen
                    </button>
                </section>
                <div className={'footerNav'}>

                    <div id={'navigation'}>
                        <a href={'navigation.html'}>
                            <img id={'boatimg'} src={require('../img/png_old/boatblue.png')} alt={'boaticon'}/>
                        </a>
                        <p>
                            Navigation
                        </p>
                    </div>
                    <div id={'meineTickets'}>
                        <a href={'tickets.html'}>
                            <img id={'ticketimg'} src={require('../img/png_old/ticketwhite.png')} alt={'ticketicon'}/>
                        </a>
                        <p>
                            Meine Tickets
                        </p>
                    </div>
                    <div id={'konto'}>
                        <a href={'konto.html'}>
                            <img id={'kontoimg'} src={require('../img/png_old/kontowhite.png')} alt={'kontoicon'}/>
                        </a>
                        <p>
                            Konto
                        </p>
                    </div>

                </div>
            </div>
        )

    }

}

export default RoutePlanningComponents;