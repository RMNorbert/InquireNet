import { useNavigate } from "react-router-dom";
import { aiAnswerQuestion } from "../answers/AiAnswer";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import {loggedInUserId} from "../../utils/TokenDecoder.jsx";
export const CreateQuestion = () => {
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        const questionUrl = "/questions/";
        const title = e.target[0].value
        const description = e.target[1].value;
        const data = {title: title, description: description, userID: loggedInUserId()}
        await multiFetch(questionUrl,"POST", data);
        await fetchData(title);
        navigate("/forum");
    };

    const fetchData = async (title) => {
        const data = await multiFetch("/questions/last","GET");
        const lastQuestionId = await data;
        await aiAnswerQuestion(title, lastQuestionId);
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
