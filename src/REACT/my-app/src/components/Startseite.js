import React, {useEffect, useState} from 'react';
import CustomerService from "../services/CustomerService";
import Button from "bootstrap/js/src/button";
import axios from "axios";


function StartseiteComponent () {
    const user = sessionStorage.getItem("user");
    const [trainingsplaene, setTrainingsplaene] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const res = await axios.get("http://localhost:8080/trainingsplaene");
            setTrainingsplaene(res);
            setLoading(false);
        };
        fetchData();
    }, []);
    return (
        <div id={"trainingsplans_Section"}>
            <button id={"createPlan_Btn"}>
                <a href="/Trainingsplan erstellen">Plan erstellen</a>
            </button>
            {!loading && trainingsplaene.data?.map(trainingsplan =>
                <div key={trainingsplan.trainingsplanId} id={"trainingsplan_section"}>
                    <div>
                        <div>
                            <h1>
                                {trainingsplan.name}
                            </h1>
                            <hr/>
                            {trainingsplan.uebungen.map(uebung =>
                                <div key={uebung.uebungForTrainingsPlanId} id={"uebungsDiv"}>
                                    <h3>
                                        {uebung.sets}x {uebung.name}
                                    </h3>
                                    <hr/>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    )
}




export default StartseiteComponent;