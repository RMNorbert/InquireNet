import { Configuration, OpenAIApi } from "openai";
import { submitAnswer } from "../../utils/submitAnswer";
import { API_KEY } from "../../Config";



let config = new Configuration({ apiKey: API_KEY,});
const openai = new OpenAIApi(config);

export const aiAnswerQuestion = async(title,question_id)=>{

    const res = await  openai.createChatCompletion({
        model: "gpt-3.5-turbo",
        messages: [{role: "user", content:title}]
    }).then( res => {
        //let responseOfAi = res.data.choices[0].message.content;
        submitAnswer(res.data.choices[0].message.content, question_id);
    });
}

const answerSpecifier = (responseOfAi) =>{
    let res = responseOfAi //split /use to remove repeating sentences
}

delete config.baseOptions.headers['User-Agent'];
