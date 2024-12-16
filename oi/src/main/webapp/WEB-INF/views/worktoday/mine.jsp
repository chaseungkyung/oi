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
							<strong>닉네임</strong>
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
					<div id="slider1" class="carousel slide modalcarouel col-6">
						<div class="carousel-inner">

								<div class="carousel-item ${status.index == 0 ? 'active':''}"
									data-primary="1">
									<img class="object-fit-scale d-block w-100" alt="운동인증"
										src="${pageContext.request.contextPath}/uploads/photo/${file}"
										style="height: 500px; width: 100%;">
								</div>

						</div>
						<button class="carousel-control-prev" type="button"
							data-bs-target="#slider1" data-bs-slide="prev">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Previous</span>
						</button>
						<button class="carousel-control-next" type="button"
							data-bs-target="#slider1" data-bs-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Next</span>
						</button>
					</div>
					<!-- 콘텐츠 영역 -->
					<div class="table-container col-6">
						<!-- 콘텐츠 -->
						<div class="content">
							<c:set var="defaultfile"
								value="${pageContext.request.contextPath}/resources/images/blank-profile.png" />
							<c:set var="fileroot"
								value="${pageContext.request.contextPath}/uploads/photo/${article.profilePhoto}" />
							<img alt="사진"
								src="${article.profilePhoto == 'default'? defaultfile : fileroot }"
								style="width: 50px; height: 50px; border-radius: 50%;"><span
								style="margin-left: 10px; font-weight: bold;">${article.nickName}</span>
							<div style="margin-top: 10px; font-size: 14px; font-weight: 600;">${article.content}</div>
						</div>
						<hr>
						<!-- 댓글 목록 -->
						<div class="comments-section"
							style="min-height: 450px; max-height: 450px; overflow-y: auto; font-size: 14px; font-weight: 300;">

									<div class="empty">등록된 댓글이 없습니다</div>

										<div class="comment mb-3">
											<div class="d-flex align-items-center">
												<c:set var="profile"
													value="${pageContext.request.contextPath}/uploads/photo/${commentdto.memberPhoto}" />
												<img alt="프로필"
													src="${commentdto.memberPhoto == 'default' ? defaultfile : profile}"
													style="width: 40px; height: 40px; border-radius: 50%;">
												<span style="margin-left: 10px; font-weight: bold;">${commentdto.writernickname}</span>
											</div>
											<div style="margin-left: 50px; margin-top: 5px;">
												<span style="font-size: 14px; font-weight: 300;">${commentdto.innercontent}</span>
											</div>
										</div>


							<!-- 추가 댓글은 위와 동일하게 반복 -->
						</div>
						<!-- 댓글 입력란 -->
						<div class="input-container">
							<input type="text" class="contents" name="commentcontents"
								placeholder="댓글을 남겨보세요">
							<button type="button" class="btn btn-primary btn-answer">등록</button>
							<input type="hidden" name="wnum" class="getwnum"
								value="${article.wnum}">
						</div>
					</div>
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