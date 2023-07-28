import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { authFetch } from "../../utils/MultiFetch.jsx";
export const Register = () => {
    const navigate = useNavigate();
    const [message, setMessage] = useState("");
    const [hidden, setHidden] = useState(true);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const registrationUrl = "/register";
        const data = {username: e.target[0].value, password:  e.target[1].value};
        const response = await authFetch(registrationUrl,data,false);
        if(response) {
            setHidden(false);
            setMessage(response.split(";").join("\n"));
        } else{
            navigate("/");
        }
    };

    return (
        <div>
            <div className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">Register</div>
            <br/>
            <div className="text-rose-800 text-2xl font-extrabold drop-shadow-lg shadow-black whitespace-pre-wrap"
                 hidden={hidden}>
                    {message}
            </div>
            <div className="flex justify-center flex-col items-center text-2xl ">
                <form
                    action=""
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="name">Username</label>
                    <input type="text" className="name" />
                    <label htmlFor="">Password</label>
                    <input type="password" className="password" />
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Register
                    </button>
                </form>
            </div>
        </div>
    );
};
