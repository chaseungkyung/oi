<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/wtd/wtdmain.css">

<script type="text/javascript">
	function intotheForm() {
		location.href = "${pageContext.request.contextPath}/completeworkout/insertwtd";
	}
</script>
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>

	<main>

		<div id="wtdmain">
			<div id="wtdmainheader">
				<jsp:include page="/WEB-INF/views/worktoday/wtdtop_layout.jsp" />
				<div id="wtdheadertext" class="row">
					<h6 class="col-9">오늘운동은 어땠나요 공유해보세요</h6>
					<button id="sharebtn" type="button" class="col-3 btn"
						onclick="intotheForm();">공유하기</button>
				</div>
			</div>

			<div id="wtdmaincontent" data-page="0" data-total="0">
				<!--  여기서 부터 for -->
				<div class="contentborder">
					<div class="contentpadding">
						<table class="headtable">
							<thead>
								<tr>
									<td style="width: 23px;"><img
										class="rounded-circle object-fit-scale" alt="사진"
										src="${pageContext.request.contextPath}/resources/images/mango.jpg"
										style="width: 20px; height: 20px;"></td>
									<td style="width: auto;">imsocute</td>
									<td>2024-12-07</td>
								</tr>
							</thead>
						</table>
						<table class="bodytable">
							<tbody>
								<tr>
									<td style="height: 400px;">
										<div style="margin-top: 20px;" id="slider"
											class="carousel slide">
											<div class="carousel-inner">
												<div class="carousel-item active">
													<img class="object-fit-scale d-block w-100" alt="운동인증"
														src="${pageContext.request.contextPath}/resources/images/mango.jpg"
														style="height: 400px; width: 570px;">
												</div>
												<div class="carousel-item">
													<img class="object-fit-scale d-block w-100" alt="운동인증"
														src="${pageContext.request.contextPath}/resources/images/mango.jpg"
														style="height: 400px; width: 570px;">
												</div>
											</div>
											<button class="carousel-control-prev" type="button"
												data-bs-target="#slider" data-bs-slide="prev" ></button>
											<span class="carousel-control-prev-icon" aria-hidden="true"></span>
											<button class="carousel-control-next" type="button"
												data-bs-target="#slider" data-bs-slide="next" ></button>
											<span class="carousel-control-next-icon" aria-hidden="true"></span>
										</div>
									</td>
								</tr>
								<tr>
									<td><a style="color: black;" href="#"><i
											class="bi bi-heart"></i></a> 0 <a style="color: black;" href="#"><i
											class="bi bi-chat-dots"></i></a> 0</td>
								</tr>
							</tbody>
						</table>
						<div class="contentdiv">컨텐츠</div>
					</div>
				</div>
				<div id="sensor"></div>
			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>
</body>
</html>