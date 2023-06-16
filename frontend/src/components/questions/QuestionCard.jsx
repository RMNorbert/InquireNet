import React from "react";
import { MdQuestionAnswer } from "react-icons/md";
import { useNavigate } from "react-router";
export const QuestionCard = ({ id, title, description, created, numberOfAnswers }) => {
    const createdTime = new Date(created)
        .toISOString()
        .substring(0, 19)
        .replace("T", " ");
    const navigate = useNavigate();
    const handleAnswer = () => {
        navigate(`/question/${id}`);
    };
    return (
        <div
            className="text-black w-1/2 m-4 bg-slate-200 rounded flex justify-between p-4 rounded-xl"
            onClick={handleAnswer}
        >
            <div >
                <p>Title: {title}</p>
                <p>Description: {description}</p>
                <p>Created: {`${createdTime} `}</p>
            </div>
            <div >
                <button>
                    <MdQuestionAnswer className="text-3xl " /> {numberOfAnswers}
                </button>
            </div>
        </div>
    );
};
