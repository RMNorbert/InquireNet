import React from "react";
import { QuestionCard } from "./QuestionCard";

export const QuestionList = ({ questionData }) => {
    return (
        <div>
            {questionData.map((question) => {
                return (
                    <div className="flex justify-center items-center">
                        <QuestionCard
                            key={question.question_id + "a"}
                            id={question.question_id}
                            title={question.title}
                            description={question.description}
                            created={question.created}
                            numberOfAnswers={question.numberOfAnswers}
                        />
                    </div>
                );
            })}
        </div>
    );
};
