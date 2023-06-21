import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Reply } from "../reply/Reply";
import {submitReply} from "../../utils/submitAnswer.jsx";

export const SelectedAnswer = ({}) => {
    const [currentAnswer, setCurrentAnswer] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [replys, setReplys] = useState([]);
    const [params] = useState(useParams());
    const [shouldFetchData, setShouldFetchData] = useState(true);
    const getData = async () => {
        setIsLoading(true);
        const answerResponse = await fetch(
            "/api/answers/" + params.id
        );
        const replyResponse = await fetch(
            "/api/reply/a/" + params.id
        );
        const answerData = await answerResponse.json();
        const replyData = await replyResponse.json();
        setCurrentAnswer(answerData);
        setReplys(replyData);
        setIsLoading(false);
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        let description = e.target[0].value;
        let answer = await submitReply(description, params.id);
        setShouldFetchData(true);
    };

    useEffect(() => {
        if (shouldFetchData) {
            getData();
            setShouldFetchData(false);
        }
    }, [params.id, shouldFetchData]);
    if (isLoading)
        return (
            <div>
                <div>Loading...</div>
            </div>
        );
    else
        return (
            <div onSubmit={()=>setShouldFetchData(true)}>
                <form
                    action=""
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="reply">Reply</label>
                    <input type="text" className="answer" />
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Reply
                    </button>
                </form>
                <div className="flex justify-center">
                    <div className="h-64 bg-slate-200 my-5 rounded-xl w-2/3 text-black  ">
                        <p className="text-5xl flex justify-center">
                            {currentAnswer.title}
                        </p>
                        <p className="text-3xl gap-3">{currentAnswer.description}</p>
                    </div>
                </div>
                <div className="flex justify-center items-center flex-col">
                    {replys.map((currentReply, i) => (
                        <Reply
                            key={i}
                            description={currentReply.description}
                            created={currentReply.created}

                        />
                    ))}
                </div>
            </div>
        );
};
