export const uploadQuestion = async(title, description, userID)=>{

    const response = await fetch("http://127.0.0.1:8080/questions/", {
        method: "POST",
        body: JSON.stringify({
            title,
            description,
            userID,
        }),
        mode: "cors",
        headers: { "Content-Type": "application/json" },
    });
    const data = await response.json();
}
