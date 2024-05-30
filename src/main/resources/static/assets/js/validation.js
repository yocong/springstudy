// 회원가입 입력 검증 처리

// 계정 중복검사 비동기 요청 보내기
async function fetchDuplicateCheck(idValue) {

  const res = await fetch(`http://localhost:8383/members/check?type=account&keyword=${idValue}`);
  const flag = await res.json();

  idFlag = flag;
}

// 계정 입력 검증
const $idInput = document.getElementById('user_id');
let idFlag = false;

$idInput.addEventListener('keyup', async (e) => {

  // 아이디 검사 정규표현식
  const accountPattern = /^[a-zA-Z0-9]{4,14}$/;

  // 입력값 읽어오기
  const idValue = $idInput.value;
  // console.log(idValue);

  // 검증 메시지를 입력할 span
  const $idChk = document.getElementById('idChk');

  if (idValue.trim() === '') {
    $idInput.style.borderColor = 'red';
    $idChk.innerHTML = '<b class="warning">[아이디는 필수값입니다.]</b>';
  } else if (!accountPattern.test(idValue)) {
    $idInput.style.borderColor = 'red';
    $idChk.innerHTML = '<b class="warning">[아이디는 4~14글자 사이 영문,숫자로 입력하세요]</b>';
  } else {

    // 아이디 중복검사
    await fetchDuplicateCheck(idValue);

    if (idFlag) {
      $idInput.style.borderColor = 'red';
      $idChk.innerHTML = '<b class="warning">[아이디가 중복되었습니다. 다른 아이디를 사용하세요!]</b>';
    } else {
      $idInput.style.borderColor = 'skyblue';
      $idChk.innerHTML = '<b class="success">[사용가능한 아이디입니다.]</b>';
    }
  }

});


// 패스워드 검사 정규표현식
const passwordPattern = /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;

// 이름 검사 정규표현식
const namePattern = /^[가-힣]+$/;

// 이메일 검사 정규표현식
const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
