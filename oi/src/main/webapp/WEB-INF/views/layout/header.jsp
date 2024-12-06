<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<nav class="navbar navbar-expand-lg">
	<div class="container-fluid">
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarcenter" aria-controls="navbarcenter"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/"
				style="padding: 15px 10px;"> <img alt="Brand"
				src="${pageContext.request.contextPath}/resources/images/logo3.svg" class="img-responsive"
				style="height: 30px; width: auto;">
			</a>
		</div>
		<div class="collapse navbar-collapse justify-content-md-center"
			id="navbarcenter">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" aria-current="page"
					href="#">오운완</a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown"
					aria-expanded="false">오이거래</a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="#">같이운동해요</a></li>
						<li><a class="dropdown-item" href="#">중고거래</a></li>
						<li><a class="dropdown-item" href="#">Something else here</a></li>
					</ul></li>
				<li class="nav-item"><a class="nav-link" href="#">헬스장</a></li>
				<li class="nav-item"><a class="nav-link" href="#">러닝코스</a></li>
				<li class="nav-item"><a class="nav-link" href="#">QnA</a></li>

				<c:if test="${empty sessionScope.member}">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${pageContext.request.contextPath}/access/login" id="login">로그인</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="#" id="register">회원가입</a></li>
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
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/access/logout">로그아웃</a></li>
						</ul>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>