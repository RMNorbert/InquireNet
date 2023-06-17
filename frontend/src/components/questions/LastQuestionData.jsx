import React, { useState } from "react";
export const getLastQuestionId = () => {
    const [question, setQuestion] = useState(0);
    const fetchData = async () => {
        const data = await fetch("http://127.0.0.1:8080/questions/last");
        const dataJSON = await data.json();
        setQuestion(dataJSON.question.question_id);
    };
    fetchData();
    return question;
}
