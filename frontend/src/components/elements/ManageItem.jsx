import {multiFetch} from "../../utils/MultiFetch.jsx";
import {useState} from "react";

export const ManageItem = ({ dataTitle, userId,currentDataId, url }) => {
    const [deleting, setDeleting] = useState(false);

    const handleDelete = async () => {
        await multiFetch(url,"DELETE",{ userId: userId, targetID:currentDataId})
        setDeleting(true);
    }
    if(deleting){
        return (<></>)
    } else {
        return (
            <div>
                <div
                    className="object-cover text-black w-80 m-4 bg-slate-200 rounded flex justify-between p-4 rounded-xl">
                    <h1>{dataTitle}</h1>
                    <button
                        name={dataTitle}
                        className="text-2xl text-red-900"
                        onClick={handleDelete}
                    >
                        Delete
                    </button>
                </div>
            </div>
        );
    }
};
