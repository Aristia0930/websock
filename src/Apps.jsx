import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const Apps = () => {
    const [message, setMessage] = useState('');
    const [receivedMessages, setReceivedMessages] = useState([]);
    const [stompClient, setStompClient] = useState(null);
    const [name, setName] = useState('User123'); // 기본 사용자 ID 설정

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const client = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                user: name // 동적으로 설정된 사용자 ID
            },
            onConnect: () => {
                console.log('Connected');
                client.subscribe('/user/queue/reply', (message) => {
                  console.log(message.body)
                    setReceivedMessages(prevMessages => [...prevMessages, JSON.parse(message.body)]);
                });
            },
            onDisconnect: () => {
                console.log('Disconnected');
            }
        });

        client.activate();
        setStompClient(client);

        return () => {
            client.deactivate();
        };
    }, [name]);

    const sendMessage = () => {
        if (message.trim() !== '') {
            stompClient.publish({
                destination: '/app/sendMessage',
                body: JSON.stringify({ from: name, text: message }) // 보낸 사람 이름 포함
            });
            setMessage('');
        }
    };

    return (
        <div>
            <h1>WebSocket Chat</h1>
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
            />
            <button onClick={sendMessage}>Send</button>
            <div>
                <h2>Messages:</h2>
                {receivedMessages.map((msg, index) => (
                    <div key={index}>
                        <strong>{msg.from}</strong>: {msg.text}
                    </div>
                ))}
            </div>
            <input
                type='text'
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Enter your name"
            />
        </div>
    );
};

export default Apps;