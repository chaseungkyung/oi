<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>OI</title>
<jsp:include page="/WEB-INF/views/layout/headimported.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout/main_layout.css">
</head>
<body>
<header><jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include></header>
	<div class="container-fluid">
		<div class="ad-container container-fluid">
			<div class="ad-photo container "></div>
		</div>
		<div class="menu-banner-container container">
			<div class="row g-4">
				<div class="menu-banner col">
					<div class="menu-banner-photo"></div>
					<div class="menu-banner-disc">러닝코스 추천</div>
				</div>
				<div class="menu-banner col">
					<div class="menu-banner-photo"></div>
					<div class="menu-banner-disc">러닝코스 추천</div>
				</div>
				<div class="menu-banner col">
					<div class="menu-banner-photo"></div>
					<div class="menu-banner-disc">러닝코스 추천</div>
				</div>
				<div class="menu-banner col">
					<div class="menu-banner-photo"></div>
					<div class="menu-banner-disc">러닝코스 추천</div>
				</div>
				<div class="menu-banner col">
					<div class="menu-banner-photo"></div>
					<div class="menu-banner-disc">러닝코스 추천</div>
				</div>
			</div>
		</div>
		<div class="product-header-container container">
			<div class="row">
				<div class="product-header-margin col-1"></div>
				<div class="product-header col">실시간 중고 상품</div>
			</div>
		</div>
		<div class="product-container container">
			<div class="row g-4">
				<div class="product col">
					<div class="product-photo"></div>
					<div class="product-disc">상품 1</div>
				</div>
				<div class="product col">
					<div class="product-photo"></div>
					<div class="product-disc">상품 2</div>
				</div>
				<div class="product col">
					<div class="product-photo"></div>
					<div class="product-disc">상품 3</div>
				</div>	
				<div class="product col">
					<div class="product-photo"></div>
					<div class="product-disc">상품 4</div>
				</div>
				<div class="product col">
					<div class="product-photo"></div>
					<div class="product-disc">상품 5</div>
				</div>
			</div>
		</div>
	</div>
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>