import { useEffect, useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import { TfiLayoutLineSolid } from "react-icons/tfi";
import {username, time, role} from "../utils/TokenDecoder.jsx";
export const Navbar = () => {
    const [open, setOpen] = useState(false);
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const handleLogout = () => {
        setUser(null);
        setIsLoggedIn(false);
        localStorage.clear();
        window.location.href = "/login";
    };
    const handleOpen = () => setOpen(!open);

    useEffect(() => {
        const loggedInUser = username();
        if (loggedInUser) {
            setUser(loggedInUser);
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
            <a href="/" className="flex"><img className="t-0 left-230px " src={'/log.png'} alt={"logo"}/></a>
            <div className="pr-36 pt-10 text-center">
                <a href="/forum">
                <button className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 ">
                     Forum
                </button>
                </a>
                    <a href="/chat">
                        <button className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 ">
                            Chat with AI
                        </button>
                    </a>
            </div>
                <div className="flex justify-evenly gap-y-12">
                <div>
                    <FaUserCircle className="text-blue-600" onClick={() => handleOpen()} />
                </div>
                {open ? (
                    isLoggedIn ? (
                        <button className="bg-cyan-800 rounded-xl border-sky-600 hover:bg-sky-900 border-solid border-2">
                            <a href="/user">
                                <div>{user}</div>
                            </a>
                            <TfiLayoutLineSolid className="w-full mt-[-15px]"></TfiLayoutLineSolid>
                            {role() === "EMPLOYEE"&&
                            <a href="/docs">
                                <div className="mt-[-15px]">Documentation</div>
                            </a>}
                            <TfiLayoutLineSolid className="w-full mt-[-15px]"></TfiLayoutLineSolid>
                            <div onClick={handleLogout} className="mt-[-15px]">Logout</div>
                        </button>
                    ) : (
                        <ul className="border-sky-600 hover:bg-sky-900 border-solid border-2">
                            <li>
                                <a href="/login">Log in</a>
                            </li>
                            <li>
                                <a href="/register">Register</a>
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
