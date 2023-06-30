import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FaUserSecret } from "react-icons/fa";
import { HiComputerDesktop } from "react-icons/hi2";
import { aiAnswerQuestion } from "../answers/AiAnswer.jsx";
import {loggedInUserId, username} from "../../utils/TokenDecoder.jsx";
import {multiFetch} from "../../utils/MultiFetch.jsx";

export const Chat = ()=>{
    const navigate = useNavigate();
    const [input, setInput] = useState('');
    const [message, setMessage] = useState(null);
    const [previousChats, setPreviousChats] = useState([]);
    const [storedChats, setStoredChats] = useState([]);
    const [currentTitle, setCurrentTitle] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const chatId = 0;

    //Send user provided input to the api and store response//
    const getMessage = async () => {
        const res = await aiAnswerQuestion(input,chatId);
        setMessage(await res);
    }
    //Clear current chat title and chat box//
    const createNewChat = () => {
        setMessage(null);
        setInput("");
        setCurrentTitle(null);
    }
    //Save chat to database, only new chats will be stored//
    const saveChat = async() => {
        const newChatsData = previousChats.filter((chat) => !storedChats.includes(chat))
        if(newChatsData.length > 0) {
            for (const chatData of newChatsData) {
                const data = {
                                   userId: loggedInUserId(),
                                   title: chatData.title,
                                   role: chatData.title === <FaUserSecret/> ? "user" : "ai",
                                   content: chatData.content
                                 }
                await multiFetch("/api/chat/","POST", data);
            }
        }
    }

    // Change stored string to JSX element
    const changeRoleSymbols = (role) => {
        return role === "user" ? <FaUserSecret/> : <HiComputerDesktop/>;
    }

    //Fetch previously stored chats//
    const fetchChatHistory = async () => {
        const response = await multiFetch(`/api/chat/${loggedInUserId()}`,"GET");
        setStoredChats(response);
        await response.forEach((chat) => chat.role = changeRoleSymbols(chat.role));
        setPreviousChats(response);
    }
    //Set the chat title to the selected title while clearing the chat box//
    const handleClick = (uniqueTitle) => {
        setCurrentTitle(uniqueTitle);
        setMessage(null);
        setInput("");
    }

    //Conditional navigate back to homepage if the user not logged in,
    //and store current chats temporarily in an array
    useEffect(() => {
        if(username() === null){
            navigate("/");
        }
        if(!isLoaded){
            fetchChatHistory()
            setIsLoaded(true);
        }

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

if(username()) {
    return (<>
            <div className="app">
                <section className="side-bar">
                    <button className="chat" onClick={createNewChat}>+ New chat</button>
                    <button className="store" onClick={saveChat}> Store chat history</button>
                    <ul className="history">
                        {uniqueTitle?.map((uniqueTitle, index) =>
                            <li key={index} onClick={() => handleClick(uniqueTitle)}>
                                {uniqueTitle}
                            </li>)}
                    </ul>
                    <nav>
                        <p>Made by InquireNet</p>
                    </nav>
                </section>
                <section className="main">
                    {!currentTitle && <h1>InquireGPT</h1>}
                    <ul className="feed">
                        {currentChat.map((chatMessage, index) =>
                            <li key={index}>
                                <p className="role">{chatMessage.role}</p>
                                <p>{chatMessage.content}</p>
                            </li>)}
                    </ul>
                    <div className="bottom-section">
                        <div className="input-container">
                            <input value={input} onChange={(e) => setInput(e.target.value)}/>
                            <div id="submit" onClick={getMessage}>
                                ➤
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </>
    );
} else {
    return (<></>);
}
}
