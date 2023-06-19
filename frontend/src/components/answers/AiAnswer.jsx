import { API_KEY } from "../../Config";
import {submitAnswer} from "../../utils/submitAnswer.jsx";
const url = 'https://api.openai.com/v1/chat/completions';

export const aiAnswerQuestion = async(title,question_id)=>{
     const options = {
         method: "POST",
         headers: {
             "Authorization": `Bearer ${API_KEY}`,
             "Content-Type": "application/json"
         },
         body: JSON.stringify({
             model : "gpt-3.5-turbo",
             messages: [{ role: "user", content: title}],
             max_tokens : 200,
         })
     }
     try{
     const res = await fetch(url, options);
     const data = await res.json();
     const answer = await data.choices[0].message.content.replace(/As an AI language model,/g,'');
     if(question_id > 0) {
         await submitAnswer(await answer, question_id);
     }
     return answer;
     } catch (error) {
         console.error(error);
     }
}

