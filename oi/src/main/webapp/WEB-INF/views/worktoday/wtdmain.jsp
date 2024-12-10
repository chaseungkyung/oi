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
	
	/*
	const $sensor = $('div#sensor');

	//컨텐츠 불러오기 
	function loading(page) {

	}
*/
	// 처음 로딩 
	$(function() {
		let url = "${pageContext.request.contextPath}/completeworkout/list";
		
		$.ajax({
			type:'get',
			url: url,
			data: {page:1},
			success : function (data) {
				console.log('false');
				document.querySelector('#wtdmaincontent').insertAdjacentHTML('beforeend',data);
			},
			error: function (e) {
				console.log(e.responseText);
			}
		});
	});

/*
	// 감지 콜백 함수 
	function callback() {

	}

	// 감지 등록 
	const io = new IntersectionObserver(callback);
	io.observe($sensor);
*/
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
			</div>
			<div id="sensor" data-loading="false"></div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>
</body>
</html>