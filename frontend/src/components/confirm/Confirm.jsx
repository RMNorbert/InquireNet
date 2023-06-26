import "./Confirm.css";
export const Confirm = ({ name, onClickMethod, onCloseMethod }) => {
    return (
        <>
            <div className={"Overlay"} />
            <div className={"DialogPanel"}>
                <div className={"DialogTitleBar"}>
                    <p className={"title"}>Delete {name}</p>
                    <p className={"exitButton"} onClick={() => onCloseMethod()}>X</p>
                </div>
                <hr className={"separatorLine"} />
                <div className={"DialogContent"}>
                    <p>You have to confirm the deletion</p>
                    <button className={"confirmInput"} onClick={() => onClickMethod()}>Confirm</button>
                </div>
            </div>
        </>
    );
};
