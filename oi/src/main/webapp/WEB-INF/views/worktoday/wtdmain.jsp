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
	
	<script type="text/javascript">
	function intotheForm() {
		location.href = "${pageContext.request.contextPath}/completeworkout/insertwtd";
	}

	const $sensor = document.querySelector('#sensor');
	const $contentcover = document.querySelector('#wtdmaincontent');
	
	
	// AJAX 통신 성공했을때 콜백함수 
	const successFn = function(data) {
		
		document.querySelector('#wtdmaincontent').insertAdjacentHTML('beforeend', data);
		
		total = $('#wtdmaincontent').find('input.total_page:last-child').attr('total_page');
		page = $('#wtdmaincontent').find('input.page:last-child').attr('page');
		
		if(page === total){
			$sensor.style.display = 'none';
		}else if(page < total){
			io.observe($sensor);
		}
		
		if(total === 0 ){
			$contentcover.innerHTML = '';
		}
		
		
		$('#wtdmaincontent').attr('data-page',page);
		$('#wtdmaincontent').attr('data-total',total);
		$('div#sensor').attr('data-loading','false');
	}
	
	//컨텐츠 불러오기 
	function loading($page) {
		let url = "${pageContext.request.contextPath}/completeworkout/list";

		$.ajax({
			type : 'get',
			url : url,
			data : {
				page : $page
			},
			success : function(data) {
				successFn(data);
			},
			beforeSend : function(jqXHR) {
				$sensor.setAttribute('data-loading', true);
				jqXHR.setRequestHeader('AJAX', true);
			},
			error : function(e) {
				console.log(e.responseText);
			}
		});
	}

	// 처음 로딩 
	$(function() {
		loading(1);
	});
	

	// 감지 콜백 함수 
	const callback = (entries, io) => {
		entries.forEach((entry)=>{
			if(entry.isIntersecting){
				let loading = $sensor.getAttribute('data-loading');
				if(loading !== 'false'){
					return ;
				}
				io.unobserve(entry.target);
				
				let page = parseInt($contentcover.getAttribute('data-page'));
				let total = parseInt($contentcover.getAttribute('data-total'));
				
				if(page === 0 || page < total){
					page++;
					loading(page);
				}
			}
		});
	};

	// 감지 등록 
	const io = new IntersectionObserver(callback);
	io.observe($sensor);
</script>


<script type="text/javascript">
$(function () {
	$('#wtdmaincontent').on('click','.emotion',function(){
		if($(this).hasClass('like')){
			
			// 이미 좋아요 눌른건에 대해서는 취소 필요 
			const num = $(this).closest('table.bodytable').attr('data-num');
			const $obj = $(this).find('i');
			let mode = $(this).find('i').hasClass('bi-heart-fill') ? 'delete':'insert';
			let url = '${pageContext.request.contextPath}/completeworkout/insertlike';
			let query = {num : num, mode : mode };
			
			const fx = function (data) {
				if(mode == 'insert'){
					$($obj).removeClass('bi-heart');
					$($obj).addClass('bi-heart-fill');
				}else {
					$($obj).removeClass('bi-heart-fill');
					$($obj).addClass('bi-heart');
				}
				$($obj).parent().next('span').html(data.count);
				console.log($($obj).next('span'));
			};
			
			$.ajax({
				type : 'get',
				url : url,
				data : query,
				dataType : 'json',
				success : function (data) {
					fx(data);
				},
				beforeSend : function(jqXHR) {
					jqXHR.setRequestHeader('AJAX', true);
				},
				error : function (e) {
					console.log(e.responseText);
				}
			});
		}
	});
});
</script>

</body>
</html>