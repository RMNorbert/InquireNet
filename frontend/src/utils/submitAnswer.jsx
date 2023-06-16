export const submitAnswer = async(description, questionID  )=>{
    const response = await fetch("http://127.0.0.1:8080/answers/", {
        method: "POST",
        body: JSON.stringify({description, questionID}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
export const submitReply = async(description, answerId  )=>{
    const response = await fetch("http://127.0.0.1:8080/reply/", {
        method: "POST",
        body: JSON.stringify({description, answerId}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
}
