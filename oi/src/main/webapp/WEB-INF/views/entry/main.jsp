<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OI</title>

<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/main_layout.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/entry/admain.js"></script>
</head>

<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include></header>

	<div class="container-fluid">
		<br>
		<div id="carouselExample" class="carousel slide"
			data-bs-ride="carousel" data-bs-interval="3000">
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img
						src="${pageContext.request.contextPath}/resources/images/ad2.png"
						class="d-block w-100 adimage">
				</div>
				<div class="carousel-item">
					<img
						src="${pageContext.request.contextPath}/resources/images/ad1.png"
						class="d-block w-100 adimage">
				</div>
				<div class="carousel-item">
					<img
						src="${pageContext.request.contextPath}/resources/images/ad3.png"
						class="d-block w-100 adimage">
				</div>
				<div class="carousel-item">
					<img
						src="${pageContext.request.contextPath}/resources/images/ad4.jpg"
						class="d-block w-100 adimage">
				</div>
				<div class="carousel-item">
					<img
						src="${pageContext.request.contextPath}/resources/images/ad5.png"
						class="d-block w-100 adimage">
				</div>
			</div>

			<button class="carousel-control-prev" type="button"
				data-bs-target="#carouselExample" data-bs-slide="prev"
				aria-hidden="false">
				<span class="carousel-control-prev-icon" aria-hidden="false"></span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#carouselExample" data-bs-slide="next"
				aria-hidden="false">
				<span class="carousel-control-next-icon" aria-hidden="false"></span>
			</button>
		</div>
		<br>
		<br>
		<br>

		<div class="product-header-container container">
			<div class="row">
				<div class="product-header-margin col-1"></div>
				<div class="product-header col">실시간 중고 상품 &nbsp;&nbsp;<a href="${pageContext.request.contextPath}/marketplace/main" class="btn btn-light">더보기</a></div>
			</div>
		</div>
		<div class="product-container container">
			<div class="row g-4">
				<c:forEach var="dto" items="${list}" varStatus="status" begin="0"
					end="23">
					<!-- 이 부분은 최대 24회 반복 -->
					<div class="product col-4  col-sm-3 col-md-2 border rounded p-1 border-0 item"
						onclick="location.href='${articleUrl}&goodsListNum=${dto.goodsListNum}';">
						<div class="product-photo">
							<img
								src="${pageContext.request.contextPath}/uploads/photo/${dto.file.saveFileName[0]}">
						</div>
						<div class="product-disc">${dto.goodsName}</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>


	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>

</body>
</html>