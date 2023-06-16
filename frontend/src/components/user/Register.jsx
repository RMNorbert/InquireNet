import { useNavigate } from "react-router-dom";
import { checkAvailableUser } from "../../utils/checkAvailableuser";
import { createUser } from "../../utils/createUser";

export const Register = () => {
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        let username = e.target[0].value;
        let password = e.target[1].value;
        let user = await checkAvailableUser(username, password);
        if (user) console.log("This exists");
        else if(user == null){
            createUser(username, password);
        }
        navigate("/");
    };
    return (
        <div>
            <div>Register!</div>
            <div className="flex justify-center flex-col items-center text-2xl ">
                <form
                    action=""
                    onSubmit={(e) => {
                        handleSubmit(e);
                    }}
                    className="flex justify-center flex-col items-center text-black"
                >
                    <label htmlFor="name">Username</label>
                    <input type="text" className="name" />
                    <label htmlFor="">Password</label>
                    <input type="password" className="password" />
                    <button
                        type="submit"
                        className="bg-buttonBlue m-5 p-2 border-spacing-2 border-black border-4 hover:bg-blue-600"
                    >
                        Register
                    </button>
                </form>
            </div>
        </div>
    );
};
