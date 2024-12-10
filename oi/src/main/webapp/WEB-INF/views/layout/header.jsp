<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<div class="container-fluid header-container">
	<nav class="navbar navbar-expand-lg navbar-light">
		<div class="container">


			<a class="navbar-brand me-auto" href="#" ><img
				alt="Brand"
				src="${pageContext.request.contextPath}/resources/images/logo3.svg"
				class="bi" style="height: 50px; width: auto;"></a>



			<ul class="navbar-nav flex-nowrap d-flex flex-row ms-auto">
				<c:if test="${empty sessionScope.member}">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${pageContext.request.contextPath}/access/login" id="login">로그인</a></li>
					<li class="nav-item ms-2"><a class="nav-link"
						aria-current="page" href="#" id="register">회원가입</a></li>
				</c:if>
				<c:if test="${not empty sessionScope.member}">
					<li class="nav-item dropdown">
						<button class="btn dropdown-toggle" data-bs-toggle="dropdown"
							aria-expanded="false">
							<img alt="사진" src="#"><span>${sessionScope.member.nickname}
							</span>
						</button>
						<ul class="dropdown-menu dropdown-menu-white">
							<li><a class="dropdown-item" 
							href="${pageContext.request.contextPath}/mypage/mypage">마이페이지</a></li>
							<c:if test="${sessionScope.member.userLevel > 50}">
								<li><a class="dropdown-item" href="#">관리자페이지</a></li>
							</c:if>
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/access/logout">로그아웃</a></li>
						</ul>
					</li>
				</c:if>


			</ul>

		</div>
	</nav>
	<nav class="navbar navbar-expand-lg navbar-light">

		<div class="container">
			<button class="navbar-toggler me-auto" type="button"
				data-bs-toggle="offcanvas" data-bs-target="#offcanvasMenu"
				aria-controls="offcanvasMenu" aria-expanded="false">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="offcanvas offcanvas-start" tabindex="-1"
				id="offcanvasMenu" aria-labelledby="offcanvasMenuLabel">
				<div class="offcanvas-header offcanvas-header-container">
					<a class="offcanvas-brand me-auto" href="#"><img alt="Brand"
						src="${pageContext.request.contextPath}/resources/images/logo3.svg"
						class="bi" style="height: 40px; width: auto;"></a>
					<button type="button" class="btn-close text-reset"
						data-bs-dismiss="offcanvas" aria-label="Close"></button>
				</div>
				<div class="offcanvas-body">
					<ul class="navbar-nav">
						<li class="nav-item me-5 mt-3"><a class="nav-link" href="${pageContext.request.contextPath}/completeworkout/main">오운완</a></li>
						<li class="nav-item me-5 mt-3"><a class="nav-link" href="${pageContext.request.contextPath}/findGymController/view">헬스장</a></li>
						<li class="nav-item me-5 mt-3"><a class="nav-link" href="#">러닝코스</a></li>
						<li class="nav-item me-5 mt-3"><a class="nav-link" href="#">QnA</a></li>
						<li class="nav-item me-5 mt-3"><a class="nav-link" href="${pageContext.request.contextPath}/justnotice/list">공지사항</a></li>
						<li class="nav-item dropdown me-5 mt-3"><a
							class="nav-link dropdown-toggle" href="#" id="dropdownMenu"
							data-bs-toggle="dropdown" aria-expanded="false">오이거래</a>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu">
								<li><a class="dropdown-item" href="#">같이운동해요</a></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/marketplace/main">중고거래</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="#">Something</a></li>
							</ul></li>
							<c:if test="${sessionScope.member.userLevel>=0}">
								<li class="nav-item me-5 mt-3"><a class="nav-link" href="${pageContext.request.contextPath}/record/main">나의 기록</a></li>
							</c:if>
					</ul>
				</div>
			</div>

		</div>

	</nav>
</div>