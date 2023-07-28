import { useState } from "react";
import { useNavigate } from "react-router-dom";

export const Login = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState("");
    const [hidden, setHidden] = useState(true);
    const handleSubmit = async (e) => {
        e.preventDefault();
        const loginUrl = "/authenticate";
        const response =
            await fetch(loginUrl, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    username: username,
                    password: password
                }),
            });

        if (response.ok) {
            const data = await response.json();
            const token = data.token;
            const time = data.time;
            localStorage.setItem('time', time);
            localStorage.setItem('token', token);
            navigate("/forum");
        }
        else {
            setHidden(false);
            setMessage(await response.text().split(";").join("\n"));
        }
    };

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };
    return (
        <div>
            <div className="flex justify-center flex-col items-center text-2xl ">
                <div className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">Log in</div>
                <br/>
                <div className="text-rose-800 text-2xl font-extrabold drop-shadow-lg shadow-black whitespace-pre-wrap"
                     hidden={hidden}>
                    {message}
                </div>
                <form
                    onSubmit={handleSubmit}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="name">Username</label>
                    <input type="text"
                           className="name"
                           value={username}
                           onChange={handleUsernameChange}
                    />
                    <label htmlFor="">Password</label>
                    <input type="password"
                           className="password"
                           value={password}
                           onChange={handlePasswordChange}
                    />
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

