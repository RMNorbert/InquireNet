import { useEffect, useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import { TfiLayoutLineSolid } from "react-icons/tfi";
import { username, time, role } from "../utils/TokenDecoder.jsx";
import {useNavigate} from "react-router-dom";
export const Navbar = () => {
    const navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const handleLogout = () => {
        setUser(null);
        setIsLoggedIn(false);
        localStorage.clear();
        navigate("/");
    };
    const handleOpen = () => setOpen(!open);

    useEffect(() => {
        if (username()) {
            setUser(username());
            setTimeout(() => {
                handleLogout();
            }, 2 * 60 * 60 * 1000);

            const targetDate = new Date(time());
            const currentDate = new Date();
            if (currentDate > targetDate) {
                handleLogout();
            }
            setIsLoggedIn(true);
        } else {
            setUser(null);
            setIsLoggedIn(false);
        }
    }, [username()]);


    return (<>
        <div className="flex justify-between bg-slate-200 text-4xl text-black h-24 rounded-xl h-36 pl-10 ">
            <a onClick={() => navigate("/home")} className="flex"><img className="t-0 left-230px cursor-pointer" src={'/log.png'} alt={"logo"}/></a>
            <div className="pr-36 pt-10 text-center">
                <button
                    className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 "
                    onClick={() => navigate("/forum")}
                >
                     Forum
                </button>
                        <button
                            className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 "
                            onClick={() => navigate("/chat")}
                        >
                            Chat with AI
                        </button>
            </div>
                <div className="flex justify-evenly gap-y-12">
                <div>
                    <FaUserCircle className="text-blue-600" onClick={() => handleOpen()} />
                </div>
                {open ? (
                    isLoggedIn ? (
                        <button className="bg-cyan-800 rounded-xl border-sky-600 hover:bg-sky-900 border-solid border-2">
                            <a onClick={() => navigate("/user")}>
                                <div className="cursor-pointer">{user}</div>
                            </a>
                            <TfiLayoutLineSolid className="w-full mt-[-15px]"></TfiLayoutLineSolid>
                            {role() === "EMPLOYEE"&&
                                <a onClick={() => navigate("/docs")}>
                                    <div className="cursor-pointer mt-[-15px]">Documentation</div>
                                </a>}
                            <TfiLayoutLineSolid className="w-full mt-[-15px]"></TfiLayoutLineSolid>
                            <div onClick={() => handleLogout()} className="mt-[-15px]">Logout</div>
                        </button>
                    ) : (
                        <ul className="border-sky-600 hover:bg-sky-900 border-solid border-2">
                            <li className="cursor-pointer">
                                <a onClick={() => navigate("/")}>
                                    Log in
                                </a>
                            </li>
                            <li className="cursor-pointer">
                                <a onClick={() => navigate("/register")}>
                                    Register
                                </a>
                            </li>
                        </ul>
                    )
                ) : (
                    <></>
                )}
            </div>
        </div></>
    );
};
