
import { BASE_URL } from "./reply.js";
import { showSpinner, hideSpinner } from "./spinner.js";

function getRelativeTime(createAt) {
    // 현재 시간 구하기
    const now = new Date();
    // 등록 시간 날짜타입으로 변환
    const past = new Date(createAt);
    // 두개의 타입을 맞춰주면 연산이 가능해진다.

    // 사이 시간 구하기
    const diff = now - past;
    // console.log(diff);

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


function appendActivePage(replyResponse) {

    // 1. 현재 위치한 페이지 번호를 알아낸다.
    //  -> 주소창에 묻어있는 페이지 파라미터 숫자를 읽거나
    //     서버에서 내려준 페이지번호를 읽는다.
    const currentPage = replyResponse.pageInfo.pageInfo.pageNo;

    // 2. 해당 페이지번호와 일치하는 a태그를 탐색한다.
    const $a = document.querySelector(`.pagination a[href="${currentPage}"]`);
    $a?.parentElement.classList.add('p-active');
}



function renderPage({ begin, end, finalPage, pageInfo }) {
    let tag = '';

    // 페이지 번호 태그 만들기
    const prev = `<a class='page-link page-custom' href=1>prev</a>`
    const next = `<a class='page-link page-custom' href="${end + 1}">next</a>`
    for (let i = begin; i <= end; i++) {
        tag += `
            <li class='page-item'>
                <a class='page-link page-custom' href='${i}'>${i}</a>
            </li>
        `;


    }



    if (pageInfo.pageNo === 1) {
        tag = `${tag}${next}`
    } else if (pageInfo.pageNo === finalPage) {
        tag = `${prev}${tag}`
    } else {
        tag = `${prev}${tag}${next}`
    }

    // 페이지 태그 ul에 붙이기
    const $pageUl = document.querySelector('.pagination');
    $pageUl.innerHTML = tag;
}





export function renderReplies({pageInfo, replies }) {

    // 댓글 수 렌더링
    document.getElementById('replyCnt').textContent = pageInfo.totalCount;

    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({ rno, writer, text, createAt }) => {
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
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='${rno}'>삭제</a>
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


// =============== 무한 스크롤 전용 함수 ============= //

// 전역 변수
let currentPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetching = false; // 데이터를 불러오는 중에는 더 가져오지 않게 제어하는 변수
let totalReplies = 0; // 총 댓글 수
let loadedReplies = 0; // 로딩된 댓글 수


function appendReplies({ replies }) {

    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({ rno, writer, text, createAt }) => {
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
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='${rno}'>삭제</a>
                </div>
            </div>
        </div>
        `;
        });



    } else {
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }



    document.getElementById('replyData').innerHTML += tag;

    // 로드된 댓글 수 업데이트
    loadedReplies += replies.length;

    // 페이지 태그 렌더링
    // renderPage(pageInfo);
}



// 서버에서 댓글 데이터를 페칭
export async function fetchInfScrollReplies(pageNo=1) {

    if (isFetching) return; // 서버에서 데이터를 가져오는 중이면 return

    isFetching = true;


    const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호
    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replyResponse = await res.json();

    if (pageNo === 1) {
        // 총 댓글 수 전역 변수 값 세팅
        totalReplies = replyResponse.pageInfo.totalCount;
        loadedReplies = 0; // 댓글 입력, 삭제시 다시 1페이지 로딩시 초기값으로 만든다.
        // 댓글 수 렌더링
        document.getElementById('replyCnt').textContent = totalReplies;
        // 초기 댓글 reset
        document.getElementById('replyData').innerHTML = '';

        setupInfiniteScroll();
    }

    // 댓글 목록 렌더링
    // console.log(replyResponse)
    appendReplies(replyResponse);
    currentPage = pageNo;
    isFetching = false;
    hideSpinner();

    // 댓글을 전부 가져올 시 스크롤 이벤트 제거하기
    if (loadedReplies >= totalReplies) {
        window.removeEventListener('scroll', scrollHandler);
    }
}

// 스크롤 이벤트 핸들러 함수


async function scrollHandler(e) {

    // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
    //  현재창에 보이는 세로길이 + 스크롤을 내린 길이 >= 브라우저 전체 세로길이
    if (
        window.innerHeight + window.scrollY >= document.body.offsetHeight + 100
        && !isFetching
    ) {
        // console.log(e);

        // 서버에서 데이터를 비동기로 불러와야 함
        // 2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함.
        // async, await 으로 대기열 무한 생성을 막는다.
        showSpinner();
        await new Promise(resolve => setTimeout(resolve, 500));
        fetchInfScrollReplies(currentPage + 1);
    }
}

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
    window.addEventListener('scroll', scrollHandler);
}

