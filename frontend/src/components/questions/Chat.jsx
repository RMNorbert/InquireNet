export const Chat = ()=>{
    return(<>
        <div className="app">
        <section className="side-bar">
            <button className="chat">+ New chat</button>
            <ul className="history">
                <li>BLUGH</li>
            </ul>
            <nav>
                <p>Made by InquireNet</p>
            </nav>
        </section>
            <section className="main">
                <h1>InquireGPT</h1>
                <ul className="feed" >

                </ul>
                <div className="bottom-section">
                    <div className="input-container">
                        <input/>
                        <div id="submit">
                            ➤
                        </div>
                    </div>
                </div>
            </section>
        </div></>
    );
}
