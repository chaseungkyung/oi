<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<div
	class="container-fluid d-flex align-items-center justify-content-center">
	<div class="logo">
		<img alt="로고" src="${pageContext.request.contextPath}/resources/images/logo3.svg">
	</div>
	<ul class="nav justify-content-center">
		<li class="nav-item"><a class="nav-link active"
			aria-current="page" href="#">오운완</a></li>
		<li class="nav-item dropdown">
			<button class="btn" style="color: #0d6efd;" data-bs-toggle="dropdown"
				aria-expanded="false">오이거래</button>
			<div class="dropdown-menu dropdown-menu-white">
				<div>
					<a class="dropdown-item" href="#">중고거래</a>
				</div>
				<div>
					<a class="dropdown-item" href="#">같이운동해요</a>
				</div>
				<div>
					<a class="dropdown-item" href="#">Something else here</a>
				</div>
			</div>
		</li>
		<li class="nav-item"><a class="nav-link" href="#">헬스장</a></li>
		<li class="nav-item"><a class="nav-link" href="#">러닝코스</a></li>
		<li class="nav-item" style="margin-right: 50px;"><a
			class="nav-link" href="#">QnA</a></li>

		<c:if test="${empty sessionScope.member}">
			<div>
				<ul class="nav justify-content-center">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="${pageContext.request.contextPath}/access/login" id="login">로그인</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#" id="register">회원가입</a></li>
				</ul>
			</div>
		</c:if>

		<c:if test="${not empty sessionScope.member}">
			<li class="nav-item dropdown">
				<button class="btn dropdown-toggle" data-bs-toggle="dropdown"
					aria-expanded="false">
					<img alt="사진" src="#"><span>${sessionScope.member.nickname}
						님 반갑습니다</span>
				</button>
				<ul class="dropdown-menu dropdown-menu-white">
					<li><a class="dropdown-item" href="#">마이페이지</a></li>
					<c:if test="${sessionScope.member.userLevel > 50}">
						<li><a class="dropdown-item" href="#">관리자페이지</a></li>
					</c:if>
					<li><a class="dropdown-item" href="${pageContext.request.contextPath}/access/logout">로그아웃</a></li>
				</ul>
			</li>
		</c:if>
	</ul>
</div>