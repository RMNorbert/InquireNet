import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import { uploadQuestion } from "../../utils/uploadQuestion";
import { aiAnswerQuestion } from "../answers/AiAnswer";

export const CreateQuestion = () => {
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        const userId = Cookies.get("id");
        const title = e.target[0].value;
        const description = e.target[1].value;
        uploadQuestion(title, description, userId);
        let aiAnswer = await fetchData(title);
        navigate("/forum")
    };

    const fetchData = async (title) => {
        const data = await fetch("/api/questions/last");
        const lastQuestionId = await data.json();
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
