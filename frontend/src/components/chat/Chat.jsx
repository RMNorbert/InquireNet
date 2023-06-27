import { useState, useEffect } from "react";
import { FaUserSecret } from "react-icons/fa";
import { HiComputerDesktop } from "react-icons/hi2";
import { aiAnswerQuestion } from "../answers/AiAnswer.jsx";

export const Chat = ()=>{
    const [input, setInput] = useState('');
    const [message, setMessage] = useState(null);
    const [previousChats, setPreviousChats] = useState([]);
    const [currentTitle, setCurrentTitle] = useState(null);
    const chatId = 0;
    const getMessage = async () => {
        const res = await aiAnswerQuestion(input,chatId);
        setMessage(await res);
    }

    const createNewChat = () => {
        setMessage(null);
        setInput("");
        setCurrentTitle(null);
    }

    const handleClick = (uniqueTitle) => {
        setCurrentTitle(uniqueTitle);
        setMessage(null);
        setInput("");
    }

    useEffect(() => {
        if(!currentTitle && input && message) {
            setCurrentTitle(input);
        }
        if(currentTitle && input && message) {
            setPreviousChats(prevChats => (
                [...prevChats, {
                    title: currentTitle,
                    role: <FaUserSecret/>,
                    content: input
                }, {
                    title: currentTitle,
                    role: <HiComputerDesktop/>,
                    content: message
                }]
            ))
            setInput("");
        }
    },[message,currentTitle]);

    const currentChat = previousChats.filter(previousChats => previousChats.title === currentTitle);
    const uniqueTitle = Array.from(new Set(previousChats.map(previousChat => previousChat.title)));

    return(<>
        <div className="app">
        <section className="side-bar">
            <button className="chat" onClick={createNewChat}>+ New chat</button>
            <ul className="history">
                {uniqueTitle?.map((uniqueTitle, index) =>
                    <li key={index} onClick={() => handleClick(uniqueTitle)}>
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
                        <input value={input} onChange={(e) =>setInput(e.target.value)}/>
                        <div id="submit" onClick={getMessage}>
                            ➤
                        </div>
                    </div>
                </div>
            </section>
        </div></>
    );
}
