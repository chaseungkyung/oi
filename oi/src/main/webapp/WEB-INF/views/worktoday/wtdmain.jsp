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
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>

	<main>

		<div id="wtdmain">
			<div id="wtdmainheader">
				<jsp:include page="/WEB-INF/views/worktoday/wtdtop_layout.jsp" />
				<div id="wtdheadertext" class="row">
				<h6 class="col-auto">오늘운동은 어땠나요 공유해보세요</h6>
				<button type="button" class="col-3">공유하기</button>
				</div>
			</div>

			<div id="wtdmaincontent">
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
									<td style="height: 400px;"><img class="object-fit-scale"
										alt="운동인증"
										src="${pageContext.request.contextPath}/resources/images/mango.jpg"
										style="height: 400px; width: 578px;"></td>
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
			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>
</body>
</html>