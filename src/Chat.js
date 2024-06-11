import React, { useState, useEffect } from 'react';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

const Chat = () => {
    const [stompClient, setStompClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const client = over(socket);
        client.connect({}, () => {
            client.subscribe('/topic/messages', (msg) => {
                const newMessage = JSON.parse(msg.body);
                setMessages((prevMessages) => [...prevMessages, newMessage]);
            });
        });
        setStompClient(client);
    }, []);

    const sendMessage = () => {
        if (stompClient) {
            stompClient.send("/app/message", {}, JSON.stringify({ content: message }));
            setMessage("");
        }
    };

    return (
        <div>
            <h2>WebSocket Chat</h2>
            <div>
                {messages.map((msg, index) => (
                    <div key={index}>{msg.content}</div>
                ))}
            </div>
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
            />
            <button onClick={sendMessage}>Send</button>
        </div>
    );
};

export default Chat;
