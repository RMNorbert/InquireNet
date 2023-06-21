import React, { useState } from "react";
export const getLastQuestionId = () => {
    const [question, setQuestion] = useState(0);
    const fetchData = async () => {
        const data = await fetch("/api/questions/last");
        const dataJSON = await data.json();
        setQuestion(dataJSON.question.question_id);
    };
    fetchData();
    return question;
}
