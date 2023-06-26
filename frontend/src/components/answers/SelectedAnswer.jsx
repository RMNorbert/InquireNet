import { Reply } from "../reply/Reply.jsx";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import { loggedInUserId } from "../../utils/TokenDecoder.jsx";

export const SelectedAnswer = ({}) => {
    const { id } = useParams();
    const [replies, setReplies] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [reFetchData, setReFetchData] = useState(true);
    const [currentAnswer, setCurrentAnswer] = useState(null);
    const answersUrl = "/api/answers/" + id;
    const responsesUrl = "/api/reply/a/" + id;
    const getData = async () => {
        setIsLoading(true);
        const answerResponse = await multiFetch(answersUrl,"GET");
        const replyResponse = await multiFetch(responsesUrl,"GET");
        const answerData = await answerResponse;
        const replyData = await replyResponse;
        setCurrentAnswer(answerData);
        setReplies(replyData);
        setIsLoading(false);
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        let description = e.target[0].value;
        let data = {description: description, answerId: id, userId: loggedInUserId()}
        await multiFetch("/api/reply/", "POST",data);
        setReFetchData(true);
    };

    useEffect(() => {
        if (reFetchData) {
            getData();
            setReFetchData(false);
        }
    }, [id, reFetchData]);
    if (isLoading)
        return (
            <div>
                <div>Loading...</div>
            </div>
        );
    else
        return (
            <div onSubmit={()=>setReFetchData(true)}>
                <form
                    action="../questions"
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="reply">Reply</label>
                    <input type="text" className="answer" />
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Reply
                    </button>
                </form>
                <div className="flex justify-center">
                    <div className="object-cover bg-slate-200 my-5 rounded-xl w-2/3 text-black  ">
                        <p className="text-5xl flex justify-center">
                            {currentAnswer.title}
                        </p>
                        <p className="text-3xl gap-3">{currentAnswer.description}</p>
                    </div>
                </div>
                <div className="flex justify-center items-center flex-col">
                    {replies.map((currentReply, i) => (
                        <Reply
                            key={i}
                            id={currentReply.reply_id}
                            description={currentReply.description}
                            created={currentReply.created}
                        />
                    ))}
                </div>
            </div>
        );
};
