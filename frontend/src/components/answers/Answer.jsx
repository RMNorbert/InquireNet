import React, {useState} from "react";
import { useNavigate } from "react-router";
import {MdQuestionAnswer} from "react-icons/md";
import {ImArrowDown, ImArrowUp} from "react-icons/im";
export const Answer = ({id, description, created , numberOfReply}) => {
    const navigate = useNavigate();
    const [voted, setVoted] = useState(false);
    const voteTo = (up, e) => {
        const votedElement = e.target.closest(".bg-slate-200");
        console.log(e.target.closest(".bg-slate-200"));
        if (voted) {
            votedElement.classList.remove("bg-slate-200");

            if (up) {
                votedElement.classList.add("bg-green-900");
            } else {
                votedElement.classList.add("bg-red-900");
            }
        } else {
            votedElement.classList.add("bg-slate-200");
        }
    };

    const handleAnswer = () => {
        navigate(`/answer/${id}`);
    };
    return (
        <>
        <div
            className="flex gap-3 bg-slate-200 text-black rounded-lg my-5 w-2/3 p-6 flex-col"
            onClick={handleAnswer}
            id="voted"
        >
            <div className="gap-4">{created}</div>
            <div className="text-4xl">{description}</div>
        </div>
        <div >
            <button  onClick={(e) => voteTo(true, e)}>
                <ImArrowUp className="text-3xl text-green-900" />
            </button>
            <button>
                <MdQuestionAnswer className="text-3xl " /> {numberOfReply}
            </button>
            <button onClick={(e) => voteTo(false, e)}>
                <ImArrowDown className="text-3xl text-red-900" />
            </button>
        </div>
        </>
    );
};
