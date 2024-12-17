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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wtd/mine.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wtd/mine.js"></script>
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
						<c:set var="defaultprofile"
							value="${pageContext.request.contextPath}/resources/images/blank-profile.png" />
						<c:set var="photo"
							value="${pageContext.request.contextPath}/uploads/photo/${sessionScope.member.saveprofile}" />
						<img
							src="${sessionScope.member.saveprofile ? photo : defaultprofile }"
							alt="Profile" class="profile-img">
					</div>
					<div class="col-md-8 d-flex flex-column justify-content-center">
						<div class="profile-info d-flex align-items-center">
							<h2 class="me-3">${sessionScope.member.nickname}</h2>
							<button class="btn btn-outline-secondary btn-sm">프로필 편집</button>
						</div>
						<div class="profile-stats">
							<div>
								<span id="dataCount"></span> 게시물
							</div>
						</div>
						<div class="profile-bio">
							<strong>${sessionScope.member.nickname}</strong>
						</div>
					</div>
				</div>
				<hr>
				<div class="profile-posts row" data-page="0" data-total="0"></div>
				<button class="btn seemore" style="display: none;">더보기</button>
			</div>
		</div>
	</main>


	<div class="modal fade" id="modal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body" style="display: flex; min-height: 600px;">
					<!-- 여기 -->
				</div>
			</div>
		</div>
	</div>
	
	
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>
</body>
</html>