import {useEffect, useState} from "react";
import {multiFetch} from "../../utils/MultiFetch.jsx";
import { ManageItem } from "../ManageItem.jsx";
import {role} from "../../utils/TokenDecoder.jsx";
import {useNavigate} from "react-router-dom";

export const EmployeePage = () => {
    const navigate = useNavigate();
    const [usersData, setUsersData] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const userUrl = "/api/user/";
    const fetchUserData = async () => {
        const response = await multiFetch("/api/user/all", "GET")
        setUsersData(await response);
    }

    useEffect(() => {
        if(role() !== "EMPLOYEE"){
            navigate("/");
        }
        if(!isLoaded) {
            fetchUserData();
            setIsLoaded(true);
        }
    },[])

    return (
            <div>
                <div> Manage users</div>
                {usersData.map((user) => {
                    return (
                        <ManageItem
                            dataTitle={user.username}
                            userId={user.id}
                            currentDataId={user.id}
                            url={userUrl}
                        />
                    );
                })}
            </div>
            )
}
