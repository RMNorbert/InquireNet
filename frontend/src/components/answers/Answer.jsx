import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import {MdQuestionAnswer} from "react-icons/md";
import {ImArrowDown, ImArrowUp} from "react-icons/im";
import {RiDeleteBin2Fill} from "react-icons/ri";
import {submitVote, submitDelete} from "../../utils/submitAnswer.jsx";
import "./Answer.css";
export const Answer = ({id, description, created , numberOfReply , vote}) => {
    const navigate = useNavigate();
    const [voting, setVoting] = useState(false);
    const [deleting, setDeleting] = useState(false);
    const [currentVote, setCurrentVote] = useState(vote);
    const answersUrl = "/answers/";
    const voteTo = async (up, answerId) => {
        const res = up ? await submitVote("upVoted",answerId) :  await submitVote("downVoted",answerId) ;
        const data = await res;
        setCurrentVote(data.vote);
        setVoting(!voting);
    };

    const handleAnswer = () => {
        navigate(`/answer/${id}`);
    };
    const handleDelete = async (currentId) => {
        await submitDelete(0,currentId,answersUrl);
        setDeleting(!deleting);
    };

    useEffect(() => {

    },[voting,currentVote]);

    if(deleting){
        return (<></>)
    }else {
        return (
            <>
                <div
                    className="flex gap-3 text-black rounded-lg my-5 w-2/3 p-6 flex-col"
                    onClick={handleAnswer}
                    id={currentVote}
                >
                    <div className="gap-4">{created}</div>
                    <div className="text-4xl">{description}</div>
                </div>
                <div>
                    <button onClick={() => voteTo(true, id)}>
                        <ImArrowUp className="text-3xl text-green-900"/>
                    </button>
                    <button>
                        <MdQuestionAnswer className="text-3xl "/> {numberOfReply}
                    </button>
                    <button onClick={() => voteTo(false, id)}>
                        <ImArrowDown className="text-3xl text-red-900"/>
                    </button>
                </div>
                <button onClick={() => handleDelete(id)}>
                    <RiDeleteBin2Fill className="text-2xl text-red-900"/>
                </button>
            </>
        );
    }
};
