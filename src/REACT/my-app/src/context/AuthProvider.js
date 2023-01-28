import {createContext, useState} from "react";

const  AuthContext = createContext({});

//children represent what's insid the authProvider Component
export const AuthProvider = ({ children}) =>{
    const [auth, setAuth] = useState({});

    return(
        <AuthContext.Provider value={{auth, setAuth}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;