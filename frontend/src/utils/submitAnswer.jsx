export const submitAnswer = async(userId,description, id  )=>{
    const response = await fetch("/api/answers/", {
        method: "POST",
        body: JSON.stringify({userId,description, id}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
export const submitReply = async(description, answerId , userId ) =>{
    const response = await fetch("/api/reply/", {
        method: "POST",
        body: JSON.stringify({description, answerId,  userId}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}

export const submitVote = async(vote, id  ) =>{
    const response = await fetch("/api/answers/vote", {
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
