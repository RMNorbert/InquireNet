import {useEffect, useState} from "react";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import {loggedInUserId, username} from "../../utils/TokenDecoder.jsx";
import {QuestionList} from "../questions/QuestionList.jsx";
import { Confirm } from "../confirm/Confirm.jsx";

export const User = ()=>{
    const [answerData, setAnswerData] = useState(null);
    const [questionData , setQuestionData] = useState([]);
    const [isDeletion, setIsDeletion] = useState(false);
    const fetchData = async () => {
        const answerResponse = await multiFetch(`/api/answers/user/${loggedInUserId()}`,"GET");
        setAnswerData(await answerResponse);
        const questionResponse = await multiFetch(`/api/questions/all/${loggedInUserId()}`,"GET");
        setQuestionData(await questionResponse);
    }

    const handleDelete = async () => {
        await multiFetch("/api/user/","DELETE")
        setTimeout(() => {
            localStorage.clear();
            window.location.href = "/register";
        },2 * 160);
    }

    useEffect(() => {
        fetchData();
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
                <br/>
                <div  className="text-yellow-300 text-2xl font-extrabold drop-shadow-lg shadow-black">
                    Questions of the user :
                </div>
                        <QuestionList questionData={questionData}/>
            </>

        </div>
    )
}
