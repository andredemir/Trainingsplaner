import {useRef, useState, useEffect, useContext} from "react";
import AuthContext from "../context/AuthProvider";
import axios from "axios";
import { useHistory } from "react-router-dom"

const USERLOGIN_REST_API_URL =  "http://localhost:8080/api/auth/signin";

function Login (){
    let history
    const {setAuth} = useContext(AuthContext);
    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');

    //for Success Message later to redirect to another Page
    const [success, setSuccess] = useState(false);

    //set the focus on the first Input when the component loads
    useEffect(() => {
        userRef.current.focus();
    }, [])

    //to empty out any errorMessage if the user changes the state
    useEffect(() => {
        setErrMsg('');
    }, [user, pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(USERLOGIN_REST_API_URL,
                JSON.stringify({user, pwd}), {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: false
                }
            );
            console.log(JSON.stringify(response?.data));
            //console.log(JSON.stringify(response));
            const accessToken = response?.data?.accessToken;
            // roles should be an array of roles
            const roles = response?.data?.roles;
            setAuth({ user, pwd, roles, accessToken });
            setUser('');
            setPwd('');
            setSuccess(true);
        } catch (err) {
            if (!err?.response){
                setErrMsg('No Server Response');
            }else if(err.response?.status === 400){
                setErrMsg('Missing username or Password');
            }else if(err.response?.status === 401){
                setErrMsg('unauthorized')
            }else {
                setErrMsg('Login Failed')
            }
            errRef.current.focus();
        }
    }
    return (
        <>
            {success ? (
                <section>
                    <h1>You are logged in!</h1>
                    <br/>
                    <p>
                        <a href="#">Go to Home</a>
                    </p>
                </section>
            ) : (
                <div id={"creatCustomerBox"}>
                    //errorMessage Display
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Sign In</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="usernameInput">Username</label>
                        <input placeholder={'Username or Email'}
                               id={"username"}
                               ref={userRef}
                               autoComplete={"off"}
                               type="text"
                               name="usernameInput"
                               onChange={(e) => setUser(e.target.value)}
                               value={user}
                               required
                        />
                        <label htmlFor="passwordInput">Passwort</label>
                        <input placeholder={'passwort'}
                               id="password"
                               type="password"
                               name="passwordInput"
                               onChange={(e) => setPwd(e.target.value)}
                               value={pwd}

                        />
                        <button id="toProductButton">Sign In</button>
                    </form>
                </div>
            )}
        </>
    )
}

export default Login