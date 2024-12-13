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

	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
					<div id="slider1" class="carousel slide modalcarouel">
						<div class="carousel-inner">
						
								<div class="carousel-item ${status.index eq 0 ? 'active':'' }"
									data-primary="${map.key}">
									<img class="object-fit-scale d-block w-100" alt="운동인증" src="#"
										style="height: 600px; width: 800px;">
								</div>
							
						</div>
						<button class="carousel-control-prev" type="button"
							data-bs-target="#slider1" data-bs-slide="prev"></button>
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<button class="carousel-control-next" type="button"
							data-bs-target="#slider1" data-bs-slide="next"></button>
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
					</div>
					<table>
						<tbody>
							<tr>

							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
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
					<!-- 캐러셀 영역 -->
					<div id="slider1" class="carousel slide modalcarouel col-6">
						<div class="carousel-inner">
							<!-- 예시 슬라이드 -->
							<div class="carousel-item active" data-primary="1">
								<img class="object-fit-scale d-block w-100" alt="운동인증" src="${pageContext.request.contextPath}/resources/images/mango.jpg"
									style="height: 500px; width: 100%;">
							</div>
							<!-- 추가 슬라이드 필요 시 반복 -->
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
							<img alt="사진" src="#"
								style="width: 50px; height: 50px; border-radius: 50%;"><span
								style="margin-left: 10px; font-weight: bold;">닉네임</span>
							<div style="margin-top: 10px;">컨텐츠</div>
						</div>
						<hr>
						<!-- 댓글 목록 -->
						<div class="comments-section" style="min-height: 450px;">
							<!-- 개별 댓글 -->
							<div class="comment mb-3">
								<div class="d-flex align-items-center">
									<img alt="프로필" src="#"
										style="width: 40px; height: 40px; border-radius: 50%;">
									<span style="margin-left: 10px; font-weight: bold;">닉네임</span>
								</div>
								<div style="margin-left: 50px; margin-top: 5px;">
									<span>댓글 내용이 여기에 표시됩니다.</span>
								</div>
								<div style="margin-left: 50px; margin-top: 5px;">
									<button class="btn btn-sm btn-link">답글달기</button>
								</div>
							</div>
							<!-- 추가 댓글은 위와 동일하게 반복 -->
						</div>
						<!-- 댓글 입력란 -->
						<div class="input-container">
							<input type="text" placeholder="댓글을 남겨보세요">
							<button class="btn btn-primary" data-answer="num">등록</button>
						</div>
					</div>
				</div>
				<!-- 모달 푸터 (필요 시 추가) -->
			</div>
		</div>
	</div>

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

$(function () {
	$('#wtdmaincontent').on('click','.btnmodalshow',function(){
		let url
	});
});
</script>

</body>
</html>