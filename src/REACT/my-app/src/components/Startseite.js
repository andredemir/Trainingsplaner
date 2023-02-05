import React, {useEffect, useState} from 'react';
import CustomerService from "../services/CustomerService";
import Button from "bootstrap/js/src/button";
import axios from "axios";


function StartseiteComponent () {
    const user = sessionStorage.getItem("user");
    const [trainingsplaene, setTrainingsplaene] = useState([]);
    const [uebungen, setUebungen] = useState([]);
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
    {
        return (
            <div>
                <button>
                    <a href="/Trainingsplan erstellen">Plan erstellen</a>
                </button>

                {!loading && trainingsplaene.data?.map(trainingsplan =>
                    <div key={trainingsplan.trainingsplanId} id={"billSection"}>
                        <div>
                            {
                                <div>
                                    <h1>
                                        {trainingsplan.name}
                                    </h1>
                                    {trainingsplaene.data?.map(trainingsplan =>
                                        <div key={trainingsplan.trainingsplanId}>
                                            <h2>
                                                {trainingsplan.uebungen.forEach(uebung => {
                                                    console.log(uebung.name)
                                                })}
                                            </h2>

                                        </div>
                                    )}
                                </div>
                            }
                        </div>
                    </div>
                )}
            </div>
        )
    }
}

export default StartseiteComponent;