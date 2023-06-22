import React, {useState} from "react";
import { createdTime } from "../../utils/TimeFormatter";
import {RiDeleteBin2Fill} from "react-icons/ri";
import {submitDelete} from "../../utils/submitAnswer.jsx";
import Cookies from "js-cookie";
export const Reply = ({id, description, created }) => {
    const [deleting, setDeleting] = useState(false);
    const url = "/api/reply/";
    const handleDelete = async (currentId) => {
        const response = await submitDelete(parseInt(Cookies.get("id")),currentId,url);
        if(response) {
            setDeleting(!deleting);
        }
    };
    if(deleting){
        return (<></>)
    }
    return (
        <div className="flex gap-3 bg-slate-200 text-black rounded-lg my-5 w-2/3 p-6 flex-col">
            <div className="gap-4">{createdTime(created)}</div>
            <div className="text-4xl">{description}</div>
            <button onClick={() => handleDelete(id)}>
                <RiDeleteBin2Fill className="text-2xl text-red-900"/>
            </button>
        </div>
    );
};
