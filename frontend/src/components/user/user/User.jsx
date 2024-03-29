import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import { multiFetch } from "../../../utils/MultiFetch.jsx";
import { loggedInUserId, username } from "../../../utils/TokenDecoder.jsx";
import { QuestionList } from "../../questions/QuestionList.jsx";
import { Confirm } from "../../confirm/Confirm.jsx";
import { ManageItem } from "../../elements/ManageItem.jsx";
import { getRank } from "./reputation/ReputationCalculator.js";

export const User = ()=>{
    const navigate = useNavigate();
    const [answerData, setAnswerData] = useState(null);
    const [questionData , setQuestionData] = useState([]);
    const [statistics , setStatistics] = useState([]);
    const [isDeletion, setIsDeletion] = useState(false);
    const [chatsData, setChatsData] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const [rank, setRank] = useState('');
    const upVoteIndex = 0;
    const downVoteIndex = 1;
    const fetchData = async () => {
        const answerResponse = await multiFetch(`/answers/user/${loggedInUserId()}`,"GET");
        setAnswerData(await answerResponse);

        const questionResponse = await multiFetch(`/questions/all/${loggedInUserId()}`,"GET");
        setQuestionData(await questionResponse);

        const statisticResponse = await multiFetch(`/answers/statistic/${loggedInUserId()}`,"GET");
        setStatistics(await statisticResponse);
        setRank(getRank(await statisticResponse));

        const chatResponse = await multiFetch(`/chat/${loggedInUserId()}`,"GET");

        const uniqueArray = await chatResponse.reduce((accumulator, value) => {
            const found = accumulator.find(item => item.title === value.title);
            if (!found) {
                accumulator.push(value);
            }
            return accumulator;
        }, []);
        setChatsData(await uniqueArray);
    }

    const chatList = () => {
        const chatUrl = "/chat/"
        return (
            <div>
                {chatsData.map((chat,index) => (
                    <div key={index}>
                    <ManageItem
                        dataTitle={chat.title}
                        userId={loggedInUserId()}
                        currentDataId={chat.title}
                        url={chatUrl}
                    />
                    </div>
                ))}
            </div>
        )
    }

    const handleDelete = async () => {
        await multiFetch("/user/","DELETE")
        setTimeout(() => {
            localStorage.clear();
            navigate("/register");
        },2 * 160);
    }

    useEffect(() => {
        if(username() === null){
            navigate("/");
        }
        if(!isLoaded) {
            fetchData();
            setIsLoaded(true);
        }
    },[isDeletion]);

    return (
        <div className="object-cover">
            <div  className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                Username :
            </div>
            <br/>
            <div className="text-2xl drop-shadow-lg shadow-black">
                {username()}
            </div>
            <br/>
            <button className=" text-2xl w-56 rounded-xl h-12  text-yellow-300 hover:bg-sky-100 hover:text-yellow-900 hover:border-solid border-2 "
                    type="button"
                    onClick={() => setIsDeletion(true)}
            >
                        Delete User
            </button>
            {isDeletion && (
                <Confirm
                    name={`${username()}`}
                    onClickMethod={() => handleDelete()}
                    onCloseMethod={() => setIsDeletion(false)}
                />
            )}
            <br/>
            <br/>
            <>
                <div  className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                    Number of answers :
                </div>
                <div className="text-2xl drop-shadow-lg shadow-black">
                    { answerData }
                </div>
                <div  className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                    Statistic :
                </div>
                <div className="text-2xl drop-shadow-lg shadow-black">
                    Your current rank is:  { rank }
                </div>
                <div className="text-2xl drop-shadow-lg shadow-black">
                    Positive : { statistics[upVoteIndex] }  /
                    Negative: {statistics[downVoteIndex]}
                </div>
                <br/>
                <div  className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                    Questions of the user :
                </div>
                        <QuestionList questionData={questionData}/>
                <div className="grid justify-center items-center ">
                    <div className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                        Chat history
                    </div>
                        {chatList()}
                </div>
            </>
        </div>
    )
}
