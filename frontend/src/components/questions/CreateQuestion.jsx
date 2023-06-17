import Cookies from "js-cookie";
import React, { useState } from "react";
import { useNavigate } from "react-router";
import { uploadQuestion } from "../../utils/uploadQuestion";
import { aiAnswerQuestion } from "../answers/AiAnswer";

export const CreateQuestion = () => {
    const [userID, setUserID] = useState();
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        setUserID(parseInt(Cookies.get("id")));
        let title = e.target[0].value;
        let description = e.target[1].value;
        uploadQuestion(title, description, Cookies.get("id"));
        let aiAnswer = await fetchData(title);

        navigate("/forum")

    };

    const fetchData = async (title) => {
        const data = await fetch("http://127.0.0.1:8080/questions/last");
        const dataJSON = await data.json();
        const lastQuestionId = dataJSON.question_id + 1;
        await aiAnswerQuestion(title,lastQuestionId);
    };
    return (
        <div className="text-black">
            <form onSubmit={(e) => handleSubmit(e)}>
                <label htmlFor="question">Question:</label>
                <input type="text" name="question" id="" />
                <label htmlFor="desc">Describe your question</label>
                <input type="text" name="desc" id="" />
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};
