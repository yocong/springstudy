import { renderReplies } from "./getReply.js";
import { BASE_URL } from "./reply.js"

// 서버에 댓글 등록을 요청하는 비동기 함수
export const fetchReplyPost = async () => {
  
  const textInput = document.getElementById('newReplyText');
  const writerInput = document.getElementById('newReplyWriter');

  // 서버로 보낼 데이터
  const payload = {
    text: textInput.value,
    author: writerInput.value,
    bno: document.getElementById("wrap").dataset.bno
  }
  console.log(payload);

  const res = await fetch(`${BASE_URL}`, {
    method: 'POST',
    headers: {
        'content-type': 'application/json'
    },
    body: JSON.stringify(payload)
});


const replies = await res.json();

// 댓글 입력 후 초기화
textInput.value = '';
writerInput.value = '';

renderReplies(replies);
};


// // 서버에 댓글 삭제를 요청하는 비동기 함수
// export const DeleteReplyPost = async () => {

//   const rno = 784;
//   fetch(`http://localhost:8383/api/v1/replies/${rno}`, {
//     method: 'DELETE'
//   })
//     .then(res => res.json())
//     .then(json => {
//       console.log(json);
//     })

//     renderReplies();
// }

