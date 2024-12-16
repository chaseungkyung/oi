<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식단 기록</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/record/recordmeal.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
	<main>

		<div class="title1">
			<a href="javascript:void(0);" onclick="moveWeek(-1)">&lt;</a> 
			<label id="currentWeekLabel"></label> <a href="javascript:void(0);" onclick="moveWeek(1)">&gt;</a>
		</div>

		<table class="rtable">
			<thead>
				<tr class="table1">
					<td class="day" data-day="SUN">SUN</td>
					<td class="day" data-day="MON">MON</td>
					<td class="day" data-day="TUE">TUE</td>
					<td class="day" data-day="WED">WED</td>
					<td class="day" data-day="THU">THU</td>
					<td class="day" data-day="FRI">FRI</td>
					<td class="day" data-day="SAT">SAT</td>
				</tr>
			</thead>
			<tbody id="calendarBody">
				<!-- 주 단위로 날짜가 여기에 출력됨 -->
			</tbody>
		</table>
		<br>
		<div class="meal-record" id="mealRecord">
			<div class="meal">
				<h2>아침</h2>
				<table id="breakfastTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('breakfast')">➕ 추가</button>
			</div>

			<div class="meal">
				<h2>점심</h2>
				<table id="lunchTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('lunch')">➕ 추가</button>

			</div>

			<div class="meal">
				<h2>저녁</h2>
				<table id="dinnerTable">
					<tr>
						<th>시간</th>
						<th>메뉴</th>
						<th>(인분)</th>
						<th>(g)</th>
						<th>(Kcal)</th>
					</tr>
				</table>
				<button class="btn mealinsertbtn" onclick="openModal('dinner')">➕ 추가</button>
			</div>

			<div class="memo">
				<h2>메모</h2>
				<textarea id="memoInput" placeholder="MEMO"></textarea>
			</div>
		</div>


		<div id="mealModal" class="modal">
			<div class="modal-content">
				<h3>식사 추가</h3>
				<input type="hidden" name="content" id="mealNum"> 
				<input type="text" name="content" id="mealTime" placeholder="식사 시간 (예: 7:00)"> 
				<input type="text" name="content" id="mealName" placeholder="식단 입력">
				<input type="text" name="content" id="mealDate" placeholder="식단 일자">
				<input type="text" name="content" id="mealUnit" placeholder="ex) 2 (인분)">
				<input type="text" name="content" id="mealCapacity" placeholder="용량(g)">
				<input type="text" name="content" id="mealKcal" placeholder="칼로리(Kcal)">
				<button class="mealinsertbtn" id="mealinsertbtn" onclick="saveMeal()">등록</button>
				<button class="mealinsertbtn" onclick="closeModal()">취소</button>
			</div>
		</div>


	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>

	<script type="text/javascript">
	
	function ajaxFun(url, method, formData, dataType, fn, file=false) {
		
		const settings = {
			type: method,
			data: formData,
			dataType: dataType,
			success: function(data) {
				fn(data);
			},
			beforeSend: function(jqXHR) {
				jqXHR.setRequestHeader('AJAX', true);		// 헤더한테 AJAX라고 넘김
			}
		};

		$.ajax(url, settings);
	}
	
	
		$(function() {
			$('#mealinsertbtn').click(function() {
				let mealTime = $('#mealTime').val();
				let mealName = $('#mealName').val();
				let mealDate = $('#mealDate').val();
				let mealUnit = $('#mealUnit').val();
				let mealCapacity = $('#mealCapacity').val();
				let mealKcal = $('#mealKcal').val();
				
				let formData = {
					dietFoodTime : mealTime,
					dietFoodName : mealName,
					dietFoodDate : mealDate, 
					dietFoodUnit: mealUnit,
					capacity: mealCapacity,
					kcal: mealKcal
				}
				//이건 내가 필요한 데이터를 모으는 거야		

				// url 이 서버에 요청보내는 거
				let url = '${pageContext.request.contextPath}/recordmeal/mealinsert';
				const fn = function(data) {
					alert(data.state);
				};
			ajaxFun(url, 'get', formData, 'json', fn);
			});
		});
	</script>

</body>
</html>