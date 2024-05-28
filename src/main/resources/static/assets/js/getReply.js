
import { BASE_URL } from "./reply.js";

function getRelativeTime(createAt) {
    // 현재 시간 구하기
    const now = new Date();
    // 등록 시간 날짜타입으로 변환
    const past = new Date(createAt);
    // 두개의 타입을 맞춰주면 연산이 가능해진다.

    // 사이 시간 구하기
    const diff = now - past;
    console.log(diff);

    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(diff / 1000 / 60);
    const hours = Math.floor(diff / 1000 / 60 / 60);
    const days = Math.floor(diff / 1000 / 60 / 60 / 24);
    const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
    const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);

    if (seconds < 60) {
        return '방금 전';
    } else if (minutes < 60) {
        return `${minutes}분 전`;
    } else if (hours < 24) {
        return `${hours}시간 전`;
    } else if (days < 7) {
        return `${days}일 전`;
    } else if (weeks < 52) {
        return `${weeks}주 전`;
    } else {
        return `${years}년 전`;
    }
}


function renderPage({ begin, end, pageInfo, prev, next }) {
    let tag = '';

    // prev 만들기
    if (prev) tag += `<li class='page-item'><a class='page-link page-active' href='${begin - 1}'>이전</a></li>`;

    // 페이지 번호 태그 만들기
    for (let i = begin; i <= end; i++) {

      // 페이지 태그 활성화
      let active = '';
      if (pageInfo.pageNo === i) active = 'p-active';

        tag += `
            <li class='page-item ${active}'>
                <a class='page-link page-custom' href='${i}'>${i}</a>
            </li>
        `;
    }

      // next 만들기
      if (next) tag += `<li class='page-item'><a class='page-link page-active' href='${end + 1}'>다음</a></li>`;


    // 페이지 태그 ul에 붙이기
    const $pageUl = document.querySelector('.pagination');
    $pageUl.innerHTML = tag;
}


export function renderReplies({ pageInfo, replies }) {

    // 댓글 수 렌더링
    document.getElementById('replyCnt').textContent = pageInfo.totalCount;

    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({ reply_no: rno, writer, text, createAt }) => {
            tag += `
        <div id='replyContent' class='card-body' data-reply-id='${rno}'>
            <div class='row user-block'>
                <span class='col-md-3'>
                    <b>${writer}</b>
                </span>
                <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(createAt)}</b></span>
            </div><br>
            <div class='row'>
                <div class='col-md-9'>${text}</div>
                <div class='col-md-3 text-right'>
                    <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
                </div>
            </div>
        </div>
        `;
        });


    } else {
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }

    document.getElementById('replyData').innerHTML = tag;

    // 페이지 태그 렌더링
    renderPage(pageInfo);

}


// 서버에서 댓글 목록 가져오는 비동기 요청 함수
export async function fetchReplies(pageNo=1) {

    const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호

    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replyResponse = await res.json();

    // 댓글 목록 렌더링
    renderReplies(replyResponse);
}


// 페이지 클릭 이벤트 생성 함수
export function replyPageClickEvent() {

    document.querySelector('.pagination').addEventListener('click', e => {
        e.preventDefault();
        // console.log(e.target.getAttribute('href'));
        fetchReplies(e.target.getAttribute('href'));
    })
}
