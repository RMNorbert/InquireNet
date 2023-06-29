import "./App.css";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { multiFetch } from "./utils/MultiFetch.jsx";
import { QuestionList } from "./components/questions/QuestionList";
import {username} from "./utils/TokenDecoder.jsx";


function App() {
    const [questions, setQuestions] = useState([]);
    const [filteredData, setFilteredData] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const navigate = useNavigate();
    const handleCreate = () => {
        navigate('/createquestion')
    };

    const search = (event) => {
        const searchText = event.target.value;
        const filteredQuestions = questions.filter((question) =>
              question.title.toLowerCase().includes(searchText.toLowerCase())
              );
        setFilteredData(filteredQuestions);
    }

    const fetchData = async () => {
        const data = await multiFetch("/api/questions/all","GET");
        const dataJSON = await data;
        dataJSON.sort(
            (a, b) => Number(b.created.substring(0,19)
                                      .replace(/[T:-]/g, ""))
                      -
                      Number(a.created.substring(0,19)
                                      .replace(/[T:-]/g, "")));

        setQuestions(dataJSON);
        setFilteredData(dataJSON);
        setIsLoaded(true);
    };

    useEffect(() => {
        if(username() === null){
            navigate("/");
        }
        if(!isLoaded) {
            fetchData();
        }
    }, [isLoaded,filteredData]);
    if(username()) {
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
                <div>
                    <input
                        className={"searchBar"}
                        type={"text"}
                        placeholder={"Filter questions"}
                        onChange={(event) => search(event)}
                    />
                </div>
                <QuestionList questionData={filteredData}/>
                </div>
        );
    } else {
        return (<></>);
    }
}

export default App;
