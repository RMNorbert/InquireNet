import {multiFetch} from "../../utils/MultiFetch.jsx";
import {useParams} from "react-router-dom";
import {loggedInUserId} from "../../utils/TokenDecoder.jsx";
import { RxUpdate } from "react-icons/rx";
import {useEffect, useState} from "react";
export const UpdatePage = () => {
    const { id } = useParams();
    const [isLoading, setIsLoading] = useState(true);
    const [data, setData] = useState([]);
    const windowUrl = window.location.href.split("/");
    const currentUrlPath = windowUrl[3];
    const dataUrl = currentUrlPath === "question" ? `/questions/${id}` : currentUrlPath === "answer" ? `/answers/${id}` : `/reply/${id}`;
    const updateUrl = currentUrlPath === "question" ? "/questions/" : currentUrlPath === "answer" ? "/answers/" : "/reply";
    const getData = async() => {
        const response = await multiFetch(dataUrl, "GET");
        setData(await response);
        setIsLoading(false);
    }

    function isTextChanged(text) {
        return text.length > 0;
    }
    const update = async (e) => {
        e.preventDefault();
        const updatedTitle = isTextChanged(e.target.title.value) ? e.target.title.value : data.title;
        const updatedDesc = isTextChanged(e.target.desc.value) ? e.target.desc.value : data.description;
        const data = currentUrlPath === "reply" ?
            {id: id, userId: loggedInUserId(), description:  updatedDesc} :
            {id: id, userId: loggedInUserId(), title: updatedTitle, description:  updatedDesc};

        await multiFetch(updateUrl, "PUT", data);
    }

    useEffect(() => {
        if(isLoading){
            getData();
        }
    },[isLoading])
    return (  <div className="text-black">
            <div className="object-cover bg-slate-200 my-5 rounded-xl w-100">
            <label className="flex items-center justify-center text-3xl mb-5">
                Update content
                <RxUpdate className="text-red-900 ml-5"/>
            </label>
            <label htmlFor="update">{currentUrlPath === "reply" ? "Current reply" :
                currentUrlPath === "answer" ? "Current answer" : "Current question"
            }'s</label>
                {currentUrlPath === "question" &&
                    <div>Title: {data.title}</div>
                }
            <div>Text:{data.description}</div>
            </div>
            <form onSubmit={(e) => update(e)}>
                {currentUrlPath === "question" &&
                    <>
                    <label htmlFor="title">Update title</label>
                    <input
                        type="text"
                        id={"title"}
                        placeholder="Provide the new title here. If no input given the title won't change."
                    />
                    </>
                }
                <label htmlFor="desc">Update text</label>
                <input
                    type="text"
                    id={"desc"}
                    placeholder="Provide the new text here. If no input given the text won't change."
                />
                <button id={"update"} type="submit" >Submit</button>
            </form>
        </div>
        )
}
