

//최종버전
// import React, { useRef, useState, useEffect } from 'react';
// import { over } from 'stompjs';
// import SockJS from 'sockjs-client';

// function App() {
//   const $websocket = useRef(null);
//   const [mes, setMes] = useState([]);
//   const [name, setName] = useState("");
//   const [stompClient, setStompClient] = useState(null);
//   const [subscription, setSubscription] = useState(null);


//   useEffect(() => {
//     const socket = new SockJS('http://localhost:8080/ws');
//     const client = over(socket);
//     client.connect({}, () => {
//       const newSubscription=client.subscribe('/topic/sendTo', (msg) => {
//             const newMessage = JSON.parse(msg.body);
//             setMes((prevMessages) => [...prevMessages, newMessage]);
//         });
//         client.subscribe(`/topic/template/${name}`, (msg) => {
//             const newMessage = msg.body;
//             setMes((prevMessages) => [...prevMessages, { content: newMessage }]);
//         });
//         if (name) 
//           if (subscription) {
//             subscription.unsubscribe(); // 이전 구독 해제
//           }
//         setSubscription(newSubscription)

//     }
//   );
//     setStompClient(client);
//   }, [name]);

//   const handleClickSendTo = () => {
//     if (stompClient) {
//       stompClient.send("/app/sendTo", {}, JSON.stringify({}));
//     }
//   };

//   const handleClickSendTemplate = () => {
//     if (stompClient) {
//       stompClient.send("/app/Template", {}, JSON.stringify({ name: name }));
//     }
//   };

//   return (
//     <div>
//       <button onClick={handleClickSendTo}>SendTo</button>
//       <button onClick={handleClickSendTemplate}>SendTemplate</button>
//       <input type='text' onChange={(e) => setName(e.target.value)} />
//       {mes.map((list, index) => <p key={index}>{list.content}</p>)}
//     </div>
//   );
// }

// export default App;

//최종버전
import React, { useRef, useState, useEffect } from 'react';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import axios from 'axios'

function App() {
  const $websocket = useRef(null);
  const [message, setMessage] = useState('');
  const [mes, setMes] = useState([]);
  const [name, setName] = useState("");
  const [stompClient, setStompClient] = useState(null);
  const [names, setNames] = useState("user123");
  const [subscription, setSubscription] = useState(null);
  const [subscription2, setSubscription2] = useState(null);
  const [subscription3, setSubscription3] = useState(null);
  

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const client = over(socket);
    client.connect({ Authorization: 'Bearer aaa' } , () => {
      client.subscribe('/topic/sendTo', (msg) => {
        const newMessage = JSON.parse(msg.body);
        setMes((prevMessages) => [...prevMessages, newMessage]);
      });
      client.subscribe(`/topic/template/aaa`, (msg) => {
        const newMessage = msg.body;
        setMes((prevMessages) => [...prevMessages, { content: newMessage }]);
      });
      client.subscribe('/user/topic/sendMessage/user123', (msg) => {
        console.log(msg)
        const newMessage = JSON.parse(msg.body);
        setMes((prevMessages) => [...prevMessages, newMessage]);
      });
      setStompClient(client);
    });

    return () => {
      if (client) {
        client.disconnect(() => {
          console.log('Disconnected');
        });
      }
    };
  }, []);

  // const sed = () => {
  //   const socket = new SockJS('http://localhost:8080/ws');
  //   const client = over(socket);
  //   client.connect({ user: 'izdlhbls' }, () => {
  
  //     client.subscribe('/user/topic/sendMessage/user123', (msg) => {
  //       console.log(msg)
  //       const newMessage = JSON.parse(msg.body);
  //       setMes((prevMessages) => [...prevMessages, newMessage]);
  //     });
  //     setStompClient(client);
  //   });

  //   return () => {
  //     if (client) {
  //       client.disconnect(() => {
  //         console.log('Disconnected');
  //       });
  //     }
  //   };
  // };

  const handleClickSendTo = () => {
    if (stompClient) {
      stompClient.send("/app/sendTo", {}, JSON.stringify({}));
    }
  };

  const handleClickSendTemplate = () => {
    if (stompClient) {
      stompClient.send("/app/Template", {}, JSON.stringify({ name: name }));
    }
  };

  const sendMessage = () => {
    if (stompClient) {
        stompClient.send('/app/sendMessage',{},JSON.stringify({ from:name, content: message }) // 보낸 사람 이름 포함
        );
        setMessage('');
    }
};

//
// const onButton=async()=>{

//     const rs=await axios.post("/hello",{name:name})
// }

  return (
    <div>
      <button onClick={handleClickSendTo}>SendTo</button>
      <button onClick={handleClickSendTemplate}>SendTemplate</button>
      <input type='text' onChange={(e) => setName(e.target.value)} />
      
          <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
            />
            <button onClick={sendMessage}>Send</button>

      {mes.map((list, index) => <p key={index}>{list.content}</p>)}
{/* 
      <button onClick={onButton}>name</button> */}

      
    </div>
  );
}

export default App;