import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./App.css";
import {QuestionList} from "./components/questions/QuestionList";


function App() {
    const [questions, setQuestions] = useState([]);
    const navigate = useNavigate();
    const handleCreate = () => {
        navigate('/createquestion')
    };
    const fetchData = async () => {
        const data = await fetch("http://127.0.0.1:8080/questions/all");
        const dataJSON = await data.json();
        setQuestions(dataJSON);
    };
    useEffect(() => {
        fetchData();
    }, []);
    return (
        <div className="App">
            <div className="w-full flex justify-center items-center">
                <button
                    className=" h-16  bg-blue-400 hover:bg-blue-700 text-white rounded-xl text-lg"
                    onClick={handleCreate}
                >
                    Create new question
                </button>
            </div>
            <QuestionList questionData={questions}/>
        </div>
    );
}

export default App;
