import { BASE_URL } from "./reply.js";
import { fetchInfScrollReplies } from "./getReply.js";
import { callApi } from "./api.js";

// 수정 이벤트 등록 함수
export function modifyReplyClickEvent() {

  // 버블링 (replyData에 event)
  document.getElementById('replyData').addEventListener('click', e => {
    e.preventDefault();

    if (!e.target.matches('#replyModBtn')) return;

    // 수정 전 텍스트 읽기
    const text = e.target.closest('.row').querySelector('.col-md-9').textContent;
    
    // 모달의 textArea에 넣기
    document.getElementById('modReplyText').value = text;

    // 댓글번호 구하기
    const rno = e.target.closest('#replyContent').dataset.replyId;

    // 모달에 클릭한 댓글번호 달아놓기
    document.querySelector('.modal').dataset.rno = rno;
});
}

// 수정 요청 처리 이벤트
document.getElementById('replyModBtn').addEventListener('click', e => {

  fetchReplyModify();
});


// 댓글 수정 비동기 요청 처리 함수
async function fetchReplyModify() {

  const payload = {
    // 댓글번호 구해서 모달에 달아놓아야 rno 가져올 수 있음
    rno: document.querySelector('.modal').dataset.rno,
    newText: document.getElementById('modReplyText').value,
    bno: document.getElementById('wrap').dataset.bno
  };

  await callApi(BASE_URL, 'PUT', payload);
  // const res = await fetch(BASE_URL, {
  //   method: 'PUT',
  //   headers: {
  //     'content-type': 'application/json'
  //   },
  //   body: JSON.stringify(payload)
  // });

  // if(!res.ok) {
  //   alert('수정 실패!');
  // }
  
  // 모달 닫기
  document.getElementById('modal-close').click();

  window.scrollTo(0, 0); // 삭제 후 페이지 상단으로 이동
  await fetchInfScrollReplies();
}