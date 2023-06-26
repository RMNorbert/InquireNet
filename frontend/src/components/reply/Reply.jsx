import { useState } from "react";
import { loggedInUserId } from "../../utils/TokenDecoder"
import { RiDeleteBin2Fill } from "react-icons/ri";
import { createdTime } from "../../utils/TimeFormatter";
import { multiFetch } from "../../utils/MultiFetch.jsx";
export const Reply = ({id, description, created }) => {
    const [deleting, setDeleting] = useState(false);
    const url = "/api/reply/";
    const handleDelete = async (currentId) => {
        const response = await multiFetch(url,"DELETE",{userId: loggedInUserId(), targetId: currentId});
        if(response) {
            setDeleting(!deleting);
        }
    };
    if(deleting){
        return (<></>)
    }
    return (
        <div className="object-cover flex gap-3 bg-slate-200 text-black rounded-lg my-5 w-2/3 p-6 flex-col">
            <div className="gap-4">{createdTime(created)}</div>
            <div className="text-4xl">{description}</div>
            <button onClick={() => handleDelete(id)}>
                <RiDeleteBin2Fill className="text-2xl text-red-900"/>
            </button>
        </div>
    );
};
