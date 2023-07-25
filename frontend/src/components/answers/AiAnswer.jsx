import { multiFetch, aiFetch } from "../../utils/MultiFetch.jsx";

export const aiAnswerQuestion = async(title,question_id)=>{
     const aiUrl = 'https://api.openai.com/v1/chat/completions';

     try{
         const res = await aiFetch(aiUrl, "POST", title);
         const data = await res;
         const answer = await data.choices[0].message.content.replace(/As an AI language model,/g,'');
     if(question_id > 0) {
         const data = {userId: 0, description: await answer, id: question_id}
         await multiFetch("/answers/","POST",data);
      } else {
         return answer;
      }
     } catch (error) {
         console.error(error);
     }
}

