import React, {useEffect, useState} from 'react';
import CustomerService from "../services/CustomerService";
import Button from "bootstrap/js/src/button";
import axios from "axios";

function TrainingsplanComponentPOST() {
    const [uebungen, setUebungen] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selected, setSelected] = useState({});
    const [trainingsplan, setTrainingsplan] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const res = await axios.get("http://localhost:8080/uebungen");
            setUebungen(res.data);
            setLoading(false);
        };
        fetchData();
    }, []);

    function handleClick(uebung) {
        const isSelected = selected[uebung.uebungId];
        if (isSelected) {
            // Remove the exercise from trainingsplan
            setSelected({ ...selected, [uebung.uebungId]: false });
            setTrainingsplan(prevTrainingsplan => prevTrainingsplan.filter(item => item.uebungId !== uebung.uebungId));
        } else {
            // Add the exercise to trainingsplan
            setSelected({ ...selected, [uebung.uebungId]: true });
            setTrainingsplan(prevTrainingsplan => [...prevTrainingsplan, uebung]);
        }
        localStorage.setItem("selectedBox", JSON.stringify({ ...selected, [uebung.uebungId]: !selected[uebung.uebungId] }));
    }

    function toggleClass() {
        const uebungsbox = document.getElementById("uebungsBox");
        uebungsbox.style.display = uebungsbox.style.display === "none" ? "flex" : "none";
    }

    return (
        <div id={"trainingsplans_Section"}>
            <div id={"trainingsplan"}>
                <h1>Trainingsplan</h1>
                {trainingsplan.map(uebung => (
                    <div key={uebung.uebungId} id={"uebung"}>
                        <h1>{uebung.name}</h1>
                        <h4>{uebung.category}</h4>
                        <h4>{uebung.bodypart}</h4>
                        <div id={"stats"}>
                            <label>Gewicht</label>
                            <input/>
                            <label>Sätze</label>
                            <input/>
                            <label>Wdh</label>
                            <input/>
                        </div>
                    </div>
                ))}
            </div>
            <button id={"createPlan_Btn"} onClick={toggleClass}>Übung hinzufügen</button>
            <div id={"uebungsBox"}>
                {!loading && uebungen.map(uebung => (
                    <div
                        className={`box ${selected[uebung.uebungId] ? "selected" : ""}`}
                        key={uebung.uebungId}
                        onClick={() => handleClick(uebung)}
                    >
                            <h1>{uebung.name}</h1>
                            <h4>{uebung.category}</h4>
                            <h4>{uebung.bodypart}</h4>
                        </div>
                    ))}
            </div>
        </div>
    );
}

export default TrainingsplanComponentPOST;