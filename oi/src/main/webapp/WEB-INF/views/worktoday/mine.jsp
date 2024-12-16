<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OI</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/wtd/wtdmain.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/wtd/mine.css">
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>
		<div id="wtdmain">
			<div id="wtdmainheader">
				<jsp:include page="/WEB-INF/views/worktoday/wtdtop_layout.jsp" />
			</div>
			<div class="container" style="width: 100%;">
				<div class="row profile-header">
					<div class="col-md-4 d-flex justify-content-center">
						<img src="#" alt="Profile" class="profile-img">
					</div>
					<div class="col-md-8 d-flex flex-column justify-content-center">
						<div class="profile-info d-flex align-items-center">
							<h2 class="me-3">username</h2>
							<button class="btn btn-outline-secondary btn-sm">프로필 편집</button>
						</div>
						<div class="profile-stats">
							<div>
								<span>10</span> 게시물
							</div>
						</div>
						<div class="profile-bio">
							<strong>닉네임</strong> <br /> 여기에 소개 문구를 넣을 수 있습니다. <br /> 예: 여행
							좋아하는 개발자, 맛집 탐방러
						</div>
					</div>
				</div>
				<hr>
				<div class="profile-posts row">
					<!-- 3열 그리드 예시 -->
					<div class="col-4">
						<div class="post post1"></div>
					</div>
					<!--  반복  -->
				</div>
			</div>
		</div>
	</main>
</body>
</html>