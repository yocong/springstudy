// 회원가입 입력 검증 처리


// 회원가입 버튼 활성화 함수
function button() {
  const $successInput = document.querySelectorAll('.success');
  const $button = document.getElementById('signup-btn');
  $button.disabled = [...$successInput].length !== 5;
}

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
      idValid = true;
    }
  }
  button();
});

/////////////////////////////////////////////////////////
// 비밀번호 입력 검증
const $pwInput = document.getElementById('password');

$pwInput.addEventListener('keyup', async (e) => {
  // 패스워드 검사 정규표현식
  const passwordPattern = /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;

  // 입력값 읽어오기
  const pwValue = $pwInput.value;

  // 검증 메시지를 입력할 span
  const $pwChk = document.getElementById('pwChk');
  // const $pwChk2 = document.getElementById('pwChk2');

  if (pwValue.trim() === '' ) {
    $pwInput.style.borderColor = 'red';
    $pwChk.innerHTML = '<b class="warning">[비밀번호는 필수값입니다.]</b>';
  } else if (!passwordPattern.test(pwValue)) {
    $pwInput.style.borderColor = 'red';
    $pwChk.innerHTML = '<b class="warning">[비밀번호는 8글자 이상, 영문,숫자,특수문자가 포함되게 입력하세요]</b>';
  } else {
    $pwInput.style.borderColor = 'skyblue';
    $pwChk.innerHTML = '<b class="success">[사용가능한 비밀번호입니다.]</b>';
    passwordValid = true;
  }
  button();
})

// 비밀번호확인 입력 검증
const $pwCheckInput = document.getElementById('password_check');

$pwCheckInput.addEventListener('keyup', async (e) => {
  // 패스워드 검사 정규표현식
  const passwordPattern = /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;

  // 입력값 읽어오기
  const pwValue = $pwInput.value;
  const pwCheckValue = $pwCheckInput.value;

  // 검증 메시지를 입력할 span
  const $pwChk2 = document.getElementById('pwChk2');

  // 비밀번호 확인에서는 비밀번호와 일치하는지 검증
  if (pwValue !== pwCheckValue) {
    $pwInput.style.borderColor = 'red';
    $pwChk2.innerHTML = '<b class="warning">[비밀번호가 일치하지 않습니다.]</b>';
  } else {
    $pwInput.style.borderColor = 'skyblue';
    $pwChk2.innerHTML = '<b class="success">[비밀번호가 일치합니다.]</b>';
    passwordMatchValid = true;
  }
  button();
})


/////////////////////////////////////////////////////////



// 이름 입력 검증
const $nameInput = document.getElementById('user_name');

$nameInput.addEventListener('keyup', async (e) => {
  // 이름 검사 정규표현식\
  const namePattern = /^[가-힣]+$/;

  // 입력값 읽어오기
  const nameValue = $nameInput.value;

  // 검증 메시지를 입력할 span
  const $nameChk = document.getElementById('nameChk');

  if (nameValue.trim() === '' ) {
    $nameInput.style.borderColor = 'red';
    $nameChk.innerHTML = '<b class="warning">[이름은 필수값입니다.]</b>';
  } else if (!namePattern.test(nameValue)) {
    $nameInput.style.borderColor = 'red';
    $nameChk.innerHTML = '<b class="warning">[이름은 한글이여야 합니다.]</b>';
  } else {
    $nameInput.style.borderColor = 'skyblue';
    $nameChk.innerHTML = '<b class="success">[사용가능한 이름입니다.]</b>';
    nameValid = true;
  }
  button();
});

////////////////////////////////////////////////////////

// 이메일 중복검사 비동기 요청 보내기
async function fetchDuplicateCheckEmail(emailValue) {

  const res = await fetch(`http://localhost:8383/members/check?type=email&keyword=${emailValue}`);
  const flag = await res.json();

  emailFlag = flag;

}

// 이메일 입력 검증
const $emailInput = document.getElementById('user_email');
let emailFlag = false;

$emailInput.addEventListener('keyup', async (e) => {

  // 이메일 검사 정규표현식
  const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

  // 입력값 읽어오기
  const emailValue = $emailInput.value;

  // 검증 메시지를 입력할 span
  const $emailChk = document.getElementById('emailChk');

  if (emailValue.trim() === '') {
    $emailInput.style.borderColor = 'red';
    $emailChk.innerHTML = '<b class="warning">[이메일은 필수값입니다.]</b>';
  } else if (!emailPattern.test(emailValue)) {
    $emailInput.style.borderColor = 'red';
    $emailChk.innerHTML = '<b class="warning">[이메일은 내용@내용.내용의 형태여야 합니다. ]</b>';
  } else {

    // 이메일 중복검사
    await fetchDuplicateCheckEmail(emailValue);

    if (emailFlag) {
      $emailInput.style.borderColor = 'red';
      $emailChk.innerHTML = '<b class="warning">[이메일이 중복되었습니다. 다른 이메일을 사용하세요!]</b>';
    } else {
      $emailInput.style.borderColor = 'skyblue';
      $emailChk.innerHTML = '<b class="success">[사용가능한 이메일입니다.]</b>';
      emailValid = true;
    }
  }
  button();
});

/////////////////////////////////////////////////////////




