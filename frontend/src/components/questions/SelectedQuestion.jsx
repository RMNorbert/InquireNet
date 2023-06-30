import { Answer } from "../answers/Answer";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import { loggedInUserId } from "../../utils/TokenDecoder.jsx";
export const SelectedQuestion = ({}) => {
    const { id } = useParams();
    const answerUrl = "/api/answers/";
    const questionUrl = "/api/questions/" + id;
    const answersToQuestionUrl = "/api/answers/q/" + id;
    const [answers, setAnswers] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [currentQuestion, setCurrentQuestion] = useState(null);
    const [shouldFetchData, setShouldFetchData] = useState(true);
    const getData = async () => {
        setIsLoading(true);
        const questionResponse = await multiFetch(questionUrl,"GET");
        const answersResponse = await multiFetch(answersToQuestionUrl,"GET");
        const questionData = await questionResponse;
        const answerData = await answersResponse;
        setCurrentQuestion(questionData);

        answerData.sort((a, b) => Number(b.created.substring(0,19)
                                                  .replace(/[T:-]/g, ""))
                                  -
                                  Number(a.created.substring(0,19)
                                                  .replace(/[T:-]/g, "")));

        setAnswers(answerData);
        setIsLoading(false);
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        let description = e.target[0].value;
        await multiFetch(answerUrl ,
                 "POST" ,
                   {
                         userId: loggedInUserId(),
                         description: description,
                         id: id
                        });

        setShouldFetchData(true);
    };

    useEffect(() => {
        if (shouldFetchData) {
            getData();
            setShouldFetchData(false);
        }
    }, [id, shouldFetchData]);
    if (isLoading)
        return (
            <div>
                <div>Loading...</div>
            </div>
        );
    else
        return (
            <div onSubmit={()=>setShouldFetchData(true)}>
                <form
                    action=""
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="answer">Answer</label>
                    <input type="text" className="answer" />
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Reply
                    </button>
                </form>
                <div className="flex justify-center object-cover">
                    <div className="object-cover bg-slate-200 my-5 rounded-xl w-2/3 text-black  ">
                        <p className="text-5xl flex justify-center">
                            {currentQuestion.title}
                        </p>
                        <p className="text-3xl gap-3">{currentQuestion.description}</p>
                    </div>
                </div>
                <div className=" object-cover flex justify-center items-center flex-col">
                    {answers.map((currentAnswer, i) => (
                        <Answer
                            key={i}
                            creatorId={currentAnswer.user_id}
                            questionCreatorId={currentQuestion.user_id}
                            answerId={currentAnswer.answer_id}
                            questionId={id}
                            description={currentAnswer.description}
                            created={currentAnswer.created}
                            numberOfReply={currentAnswer.numberOfReply}
                            vote={currentAnswer.vote}
                        />
                    ))}
                </div>
            </div>
        );
};
