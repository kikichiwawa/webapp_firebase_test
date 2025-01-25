import { signOut } from "firebase/auth";
import { auth } from "../lib/firebase";

const SignOutButton: React.FC = () =>{
    const handleSignOut = async() =>{
        try{
            await signOut(auth);
            alert("サインアウトしました．");
        }catch(e: any){
            console.error("サインアウトエラー:", e);
            alert("サインアウトに失敗しました．");
        }
    };

    return (
        <button onClick={handleSignOut} style={{marginTop: "20px", cursor: "pointer"}}>
            Sign out
        </button>
    );
};

export default SignOutButton;