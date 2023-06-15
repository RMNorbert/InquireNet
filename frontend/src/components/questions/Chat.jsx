import { aiAnswerQuestion } from "../answers/AiAnswer";
import {useState, useEffect} from "react";
import { FaUserSecret } from "react-icons/fa";
import { HiComputerDesktop } from "react-icons/hi2";

export const Chat = ()=>{
    const [message, setMessage] = useState(null);
    const [value, setValue] = useState(null);
    const [previousChats, setPreviousChats] = useState([]);
    const [currentTitle, setCurrentTitle] = useState(null);
    const getMessage = async () => {
        let chatId = 0;
        const res = await aiAnswerQuestion(value,chatId);
        setMessage(res);
    }

    const createNewChat = () => {
        setMessage(null);
        setValue("");
        setCurrentTitle(null);
    }

    const handleClick = (uniqueTitle) => {
        setCurrentTitle(uniqueTitle);
        setMessage(null);
        setValue("");
    }

    useEffect(() => {
        if(!currentTitle && value && message) {
            setCurrentTitle(value);
        }
        if(currentTitle && value && message) {
            setPreviousChats(prevChats => (
                [...prevChats, {
                    title: currentTitle,
                    role: <FaUserSecret/>,
                    content: value
                }, {
                    title: currentTitle,
                    role: <HiComputerDesktop/>,
                    content: message
                }]
            ))
        }
    },[message,currentTitle]);

    const currentChat = previousChats.filter(previousChats => previousChats.title === currentTitle)
    const uniqueTitle = Array.from(new Set(previousChats.map(previousChat => previousChat.title)));

    return(<>
        <div className="app">
        <section className="side-bar">
            <button className="chat" onClick={createNewChat}>+ New chat</button>
            <ul className="history">
                {uniqueTitle?.map((uniqueTitle, index) =>
                    <li
                        key={index}
                        onClick={() => handleClick(uniqueTitle)}
                    >
                        {uniqueTitle}
                    </li>)}
            </ul>
            <nav>
                <p >Made by InquireNet</p>
            </nav>
        </section>
            <section className="main">
                { !currentTitle && <h1>InquireGPT</h1> }
                <ul className="feed" >
                    {currentChat.map((chatMessage, index) =>
                    <li key={index}>
                        <p className="role">{chatMessage.role}</p>
                        <p >{chatMessage.content}</p>
                    </li>)}
                </ul>
                <div className="bottom-section">
                    <div className="input-container">
                        <input value={value} onChange={(e) =>setValue(e.target.value)}/>
                        <div id="submit" onClick={getMessage}>
                            ➤
                        </div>
                    </div>
                </div>
            </section>
        </div></>
    );
}
