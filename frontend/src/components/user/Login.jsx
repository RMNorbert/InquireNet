import Cookies from "js-cookie";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import { checkAvailableUser } from "../../utils/checkAvailableuser";

export const Login = () => {
    const navigate = useNavigate();
    const [invalid, setInvalid] = useState(false);
    const handleSubmit = async (e) => {
        e.preventDefault();
        let username = e.target[0].value;
        let password = e.target[1].value;
        let user = await checkAvailableUser(username, password);

        if (user) {
            Cookies.set('user', username)
            Cookies.set('id', user.id)
            navigate("/");
        } else setInvalid(true);
    };


    return (
        <div>
            <div className="flex justify-center flex-col items-center text-2xl ">
                <div>Log in!</div>
                <div className="text-red-400">{invalid ? "This username/password combo doesn't work" : ""}</div>
                <form
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="name">Username</label>
                    <input type="text" className="name"/>
                    <label htmlFor="">Password</label>
                    <input type="password" className="password"/>
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Log in
                    </button>
                </form>
            </div>
        </div>
    );

};

