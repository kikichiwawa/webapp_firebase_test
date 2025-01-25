import { createUserWithEmailAndPassword } from "firebase/auth";
import React, {useState} from "react";
import { auth } from "../lib/firebase";

const SignUp = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSignUp = async(e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try{
            await createUserWithEmailAndPassword(auth, email, password);
            alert("アカウント作成に成功しました！");
        }catch(err: any){
            setError(err.message);
        }
    };

    return(
        <div>
            <h2>Sign Up</h2>
            {error && <p style={{ color: "red"}}>{error}</p>}
            <form onSubmit={handleSignUp}>
                <input 
                    type="email" 
                    placeholder="Email" 
                    value={email} 
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input 
                    type="password" 
                    placeholder="Password" 
                    value={password} 
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Sign Up</button>
            </form>
        </div>
    );
}

export default SignUp;