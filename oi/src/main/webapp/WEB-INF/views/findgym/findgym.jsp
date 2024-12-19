<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주변 헬스장</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/calendar/calendarmain.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/findgym/findGym.css">
</head>
<body>
	
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<div class = "container d-flex justify-content-evenly">
		<div class = "info my-5">
		</div>
		<div class = "mapContainer my-5">
		<div id="map" style="width: 400px; height: 400px;"></div>
		</div>
	
	</div>
	<script type="text/javascript"
		src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=7vmwl6ghhv&submodules=geocoder"></script>
	<script type="text/javascript">
	function ajaxFun(url, method, formData, dataType, fn, file=false) {
		const sentinelNode = document.querySelector('.sentinel');
		
		const settings = {
				type: method,
				data: formData,
				dataType: dataType,
				success: function(data) {
					fn(data);
				},
				beforeSend: function(jqXHR) {
					jqXHR.setRequestHeader('AJAX', true);				
				},
				complete:function() {
				},
				error: function(jqXHR) {
					if(jqXHR.status === 403) {
						return false;
					} else if(jqXHR.status === 406) {
						alert('요청 처리가 실패했습니다.');
						return false;
					}
					
					console.log(jqXHR.responseText);
				}
		};
		
		if(file) {
			settings.processData = false; // 파일 전송시 필수. 서버로 보낼 데이터를 쿼리문자열로 변환 여부
			settings.contentType = false; // 파일 전송시 필수. contentType. 기본은 application/x-www-urlencoded
		}
		
		$.ajax(url, settings);
	}
		$(function() {
			let url = '${pageContext.request.contextPath}/findGymController/address';
			
			var map = new naver.maps.Map('map', {
				center : new naver.maps.LatLng(37.5565452, 126.9195189),
				zoom : 14,
				draggable: false,            // 드래그 비활성화
			    pinchZoom: false,            // 핀치줌 비활성화 (모바일 환경)
			    scrollWheel: false,          // 스크롤 휠 줌 비활성화
			    keyboardShortcuts: false,    // 키보드 단축키 이동 비활성화
			    disableDoubleTapZoom: true,  // 더블탭 줌 비활성화 (모바일 환경)
			    disableDoubleClickZoom: true,// 더블클릭 줌 비활성화
			    disableTwoFingerTapZoom: true// 두 손가락 탭줌 비활성화 (모바일 환경)
			});
			
			const fn = function(data) {
				let address = "";
				if(!data.address){
					address = "서울 마포구 월드컵북로 21";
				} else {
					address = data.address;
				}
				
				
				naver.maps.Service.geocode({
					query : address
				}, function(status, response) {
					if (status === naver.maps.Service.Status.ERROR) {
						console.error('네트워크 또는 파라미터 오류');
						return;
					}

					if (response.v2.meta.totalCount === 0) {
						console.log('해당 주소로 찾은 결과가 없습니다.');
						return;
					}

					
					var item = response.v2.addresses[0];
					var lng = item.x;
					var lat = item.y;
					map.setCenter(new naver.maps.LatLng(lat, lng));
					
					let innerUrl = '${pageContext.request.contextPath}/findGymController/list';
					let innerQuery = 'lng=' + lng + '&lat=' + lat;
					
					let htmltext = '';
					const marker = {};
					
					const innerFn = function(data){
						console.log(data.gymList);
						Object.entries(data.gymList).forEach(([name, coords], idx) => {
							htmltext += '<div class = "infoData" id="infoData' + idx + '"  >' + name + '</div>';
							
							marker["marker"+idx] = new naver.maps.Marker({
							      position: new naver.maps.LatLng(coords[1], coords[0]),
							      map: map
							    });
							
							marker["marker"+idx].setIcon({

					            url: '${pageContext.request.contextPath}/resources/images/marker.svg' // 다른 아이콘 URL

							});
							
							$('.info').on('mouseenter', '#infoData'+idx , function() {
						        marker["marker"+idx].setIcon({
						            url: '${pageContext.request.contextPath}/resources/images/marker_spot.svg' // 다른 아이콘 URL

						        });
						    });

						    $('.info').on('mouseleave', '#infoData'+idx, function() {
						    	marker["marker"+idx].setIcon({
						            url: '${pageContext.request.contextPath}/resources/images/marker.svg' // 다른 아이콘 URL

						        });
						    });
							
							
						});
						
						$('div.info').html(htmltext);
						
					};
					
					ajaxFun(innerUrl, 'get', innerQuery, 'json', innerFn);
				});
				
				
			}
			
			ajaxFun(url, 'get', null, 'json', fn);
			
			
		});
	
	</script>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>

</body>
</html>