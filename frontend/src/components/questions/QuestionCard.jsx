import { useNavigate } from "react-router-dom";
import { MdQuestionAnswer } from "react-icons/md";
import { RiDeleteBin2Fill } from "react-icons/ri";
import { RxUpdate } from "react-icons/rx";
import { useEffect, useState } from "react";
import { multiFetch } from "../../utils/MultiFetch.jsx";
import { createdTime } from "../../utils/TimeFormatter";
import { loggedInUserId } from "../../utils/TokenDecoder.jsx";
export const QuestionCard = ({ id, userId, title, description, created, numberOfAnswers }) => {
    const [deleting, setDeleting] = useState(false);
    const navigate = useNavigate();
    const url = "/api/questions/";
    const handleAnswer = () => {
        navigate(`/question/${id}`);
    };

    useEffect(() => {

    },[]);
    const handleDelete = async (currentId) => {
        const data = await multiFetch(url,"DELETE",{userId: loggedInUserId(), targetId: currentId})
        if(data) {
            setDeleting(!deleting);
        }
    };

    if(deleting){
        return (<></>)
    } else {
        return (
            <>
                <div
                    className="text-black w-1/2 m-4 bg-slate-200 rounded flex justify-between p-4 rounded-xl"
                    onClick={handleAnswer}
                >
                    <div>
                        <p>Title: {title}</p>
                        <p>Description: {description}</p>
                        <p>Created: {`${createdTime(created)} `}</p>
                    </div>
                    <div>
                        <button>
                            <MdQuestionAnswer className="text-3xl "/> {numberOfAnswers}
                        </button>
                    </div>
                </div>
                {loggedInUserId() === userId &&
             <div>
                <button onClick={() => handleDelete(id)}>
                    <RiDeleteBin2Fill className="text-2xl text-red-900"/>
                </button>
                 <button onClick={() => navigate(`/question/update/${id}`)}>
                     <RxUpdate className="text-2xl text-red-900"/>
                 </button>
             </div>
                }
            </>
        );
    }
};
