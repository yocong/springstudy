<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- header -->
<header>
  <div class="inner-header">
    <h1 class="logo">
      <a href="/board/list">
        <img src="/assets/img/mole.jpg" alt="로고이미지">
      </a>
    </h1>

    <div class="profile-box">
      <c:choose>
        <c:when test="${login != null && login.profile != null}">
          <img src="${login.profile}" alt="profile image">
        </c:when>
        <c:otherwise>
          <img src="/assets/img/anonymous.jpg" alt="profile image">
        </c:otherwise>
      </c:choose>
    </div>

    <h2 class="intro-text"> Welcome ${login.nickname}</h2>
    <a href="#" class="menu-open">
      <span class="menu-txt">MENU</span>
      <span class="lnr lnr-menu"></span>
    </a>
  </div>

  <nav class="gnb">
    <a href="#" class="close">
      <span class="lnr lnr-cross"></span>
    </a>
    <ul>
      <li><a href="/">Home</a></li>
      <li><a href="#">About</a></li>
      <li><a href="/board/list">Board</a></li>
      <li><a href="#">Contact</a></li>

      <!-- 로그인 안했을시 회원가입, 로그인 화면 -->
      <c:if test="${login == null}">
        <li><a href="/members/sign-up">Sign Up</a></li>
        <li><a href="/members/sign-in">Sign In</a></li>
      </c:if>

      <!-- 로그인시 마이페이지, 로그아웃 화면 -->
      <c:if test="${login != null}">
        <li><a href="#">My page</a></li>
        <li><a href="/members/sign-out">Sign Out</a></li>
      </c:if>

    </ul>
  </nav>

</header>
<!— //header —>