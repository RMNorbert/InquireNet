import { submitAnswer } from "../../utils/submitAnswer";

export const SelfForm = ({ questionID }) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        let description = e.target[0].value;
        let a = await submitAnswer(description, questionID);
    };
    return (
        <form
            action=""
            onSubmit={(e) => {
                handleSubmit(e);
            }}
            className="flex justify-center flex-col items-center text-black"
        >
            <label htmlFor="answer">Answer</label>
            <input type="text" className="answer" />
            <button
                type="submit"
                className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
            >
                Reply
            </button>
        </form>
    );
};
