import Cookies from "js-cookie";
import React from "react";
import { useEffect, useState } from "react";
import { RiUser3Fill } from "react-icons/ri";
export const Header = () => {
    const [open, setOpen] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState(null);
    let url = window.location.href.split("/");
    let currentUrlPath = url[url.length-1];

    const handleLogout = () => {
        setUsername(null);
        setIsLoggedIn(false);
        Cookies.remove("user");
    };

    const handleOpen = () => setOpen(!open);

    useEffect(() => {
        setUsername(Cookies.get("user"));
        if (username) setIsLoggedIn(true);
    });

    return (<>
        <div className="flex justify-between bg-white text-4xl text-black h-24 rounded-xl h-44">
            <a href="/" className="flex"><img className="t-0 left-230px " src={'/log.png'} alt={"logo"}/></a>
            <div className="pr-36 pt-16 text-center">
                {currentUrlPath !== "forum" ? <a href="/forum">
                <button className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 ">
                     Forum
                </button>
                </a> :
                    <a href="/chat">
                        <button className=" h-16 w-64 rounded-xl h-12  border-sky-600 hover:bg-sky-900 border-solid border-2 ">
                            Chat with AI
                        </button>
                    </a>
                }
            </div>
                <div className="flex justify-evenly gap-y-12">
                <div>
                    <RiUser3Fill className="text-blue-600" onClick={() => handleOpen()} />
                </div>
                {open ? (
                    isLoggedIn ? (
                        <div className="bg-cyan-800 rounded-xl">
                            <a href="/user">
                                <div>{username}</div>
                            </a>

                            <button onClick={handleLogout}>Logout</button>
                        </div>
                    ) : (
                        <ul>
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
