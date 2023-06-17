import React, {useEffect, useState} from "react";
import Cookies from "js-cookie";
import { MdQuestionAnswer } from "react-icons/md";
import { useNavigate } from "react-router";
import {RiDeleteBin2Fill} from "react-icons/ri";
import {submitDelete} from "../../utils/submitAnswer.jsx";
export const QuestionCard = ({ id, title, description, created, numberOfAnswers }) => {
    const [deleting, setDeleting] = useState(false);
    const createdTime = new Date(created)
        .toISOString()
        .substring(0, 19)
        .replace("T", " ");
    const navigate = useNavigate();
    const url = "http://127.0.0.1:8080/questions/";
    const handleAnswer = () => {
        navigate(`/question/${id}`);
    };
    useEffect(() => {

    },[]);
    const handleDelete = async (currentId) => {
        const data = await submitDelete(parseInt(Cookies.get("id")),currentId,url);
        if(data) {
            setDeleting(!deleting);
        }
    };
    if(deleting){
        return (<></>)
    }else {
        return (
            <>
                <div
                    className="text-black w-1/2 m-4 bg-slate-200 rounded flex justify-between p-4 rounded-xl"
                    onClick={handleAnswer}
                >
                    <div>
                        <p>Title: {title}</p>
                        <p>Description: {description}</p>
                        <p>Created: {`${createdTime} `}</p>
                    </div>
                    <div>
                        <button>
                            <MdQuestionAnswer className="text-3xl "/> {numberOfAnswers}
                        </button>
                    </div>
                </div>
                <button onClick={() => handleDelete(id)}>
                    <RiDeleteBin2Fill className="text-2xl text-red-900"/>
                </button>
            </>
        );
    }
};
