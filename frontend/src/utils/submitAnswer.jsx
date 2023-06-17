export const submitAnswer = async(description, questionID  )=>{
    const response = await fetch("http://127.0.0.1:8080/answers/", {
        method: "POST",
        body: JSON.stringify({description, questionID}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
export const submitReply = async(description, answerId  ) =>{
    const response = await fetch("http://127.0.0.1:8080/reply/", {
        method: "POST",
        body: JSON.stringify({description, answerId}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}

export const submitVote = async(vote, id  ) =>{
    const response = await fetch("http://127.0.0.1:8080/answers/vote", {
        method: "PUT",
        body: JSON.stringify({vote, id}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
export const submitDelete = async(userId, targetId, url  ) =>{
    const response = await fetch(url, {
        method: "DELETE",
        body: JSON.stringify({userId, targetId}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
