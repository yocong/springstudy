import { fetchInfScrollReplies, setupInfiniteScroll } from "./getReply.js";
import { fetchReplyPost} from "./postReply.js";
import { removeReplyClickEvent } from "./deleteReply.js";
import { modifyReplyClickEvent } from "./modifyReply.js";

// ========================= 전역 변수 ========================
export const BASE_URL = "http://localhost:8383/api/v1/replies";

// ========================= 함수 정의 =========================


// ========================= 실행 코드 ==========================

// 댓글 목록 서버에서 불러오기
// fetchReplies();
fetchInfScrollReplies(); // 일단 1페이지 데이터 그려놓기
setupInfiniteScroll();

// 댓글 작성 이벤트 등록 (POST)
document.getElementById('replyAddBtn').onclick = e => {
    // 댓글 등록 로직
    fetchReplyPost();
}

// 댓글 삭제 이벤트 등록
removeReplyClickEvent();
// 댓글 수정 이벤트 등록
modifyReplyClickEvent();

// 댓글 페이지 클릭이벤트 등록
// replyPageClickEvent();

