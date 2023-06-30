import "./Answer.css";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { MdQuestionAnswer } from "react-icons/md";
import { RiDeleteBin2Fill } from "react-icons/ri";
import { ImArrowDown, ImArrowUp } from "react-icons/im";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import { createdTime } from "../../utils/TimeFormatter";
import {loggedInUserId} from "../../utils/TokenDecoder.jsx";
export const Answer = ({answerId, creatorId , questionCreatorId, questionId, description, created , numberOfReply , vote}) => {
    const navigate = useNavigate();
    const [voting, setVoting] = useState(false);
    const [deleting, setDeleting] = useState(false);
    const [currentVote, setCurrentVote] = useState(vote);
    const userId = loggedInUserId();
    const answersUrl = "/api/answers/";
    const voteUrl = "/api/answers/vote";
    const voteTo = async (up, answerId) => {
        const res = up ? await multiFetch(
                                    voteUrl,
                                   "PUT",
                              {
                                    vote: "upVoted",
                                    id:answerId ,
                                    userId: userId ,
                                    questionId: questionId
                                   })
                              :
                          await multiFetch(
                                   voteUrl,
                                  "PUT",
                             {
                                   vote:"downVoted",
                                   id:answerId,
                                   userId: userId ,
                                   questionId: questionId
                                 });
        const data = await res;
        if(data) {
            const currentVote = up ?  "upVoted" : "downVoted";
            setCurrentVote(currentVote);
            setVoting(!voting);
        }
    };
    const handleAnswer = () => { navigate(`/answer/${answerId}`);};
    const handleDelete = async (currentId) => {
        const response = await multiFetch(answersUrl ,
                                  "DELETE",
                                    {
                                          userId: userId,
                                          targetId:currentId
                                         });
        if(response) {
            setDeleting(!deleting);
        }
    };

    useEffect(() => {

    },[voting,currentVote]);

    if(deleting){
        return (<></>)
    }else {
        return (
            <>
                <div
                    className="object-cover flex gap-3 text-black rounded-lg mt-5 w-2/3 p-6 flex-col"
                    onClick={handleAnswer}
                    id={currentVote}
                >
                    <div className="gap-4">{createdTime(created)}</div>
                    <div className="text-4xl">{description}</div>
                </div>
                <div className="bg-slate-200  mt-[-4px] w-2/3 p-1 flex-col rounded-b">
                <div>
                    {userId === questionCreatorId &&
                    <button onClick={() => voteTo(true, answerId)}>
                        <ImArrowUp className="text-3xl text-green-900"/>
                    </button>
                    }
                    <button className="text-black">
                        <MdQuestionAnswer className="text-3xl text-black"/> {numberOfReply}
                    </button>
                    {userId === questionCreatorId &&
                    <button onClick={() => voteTo(false, answerId)}>
                        <ImArrowDown className="text-3xl text-red-900"/>
                    </button>
                    }
                </div>
                    {userId === creatorId &&
                <button onClick={() => handleDelete(answerId)}>
                    <RiDeleteBin2Fill className="text-2xl text-red-900"/>
                </button>
                    }
                </div>
            </>
        );
    }
};
