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
			<a href="#" onclick="moveWeek(-1)">&lt;</a> <label
				id="currentWeekLabel"></label> <a href="#" onclick="moveWeek(1)">&gt;</a>
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
		<div class="mealTotal">
			<div class="meal-record" id="mealRecord">
				<div class="meal">
					<h2>아침</h2>
					<table id="breakfastTable">
						<tr>
							<th>시간</th>
							<th>메뉴</th>
							<th>(g)</th>
							<th>(Kcal)</th>
						</tr>
						<c:forEach var="meal" items="${mealList}">
							<tr>
								<td>${meal.dietFoodDate}</td>
								<td>${meal.dietFoodName}</td>
								<td>${meal.capacity}</td>
								<td>${meal.kcal}</td>
							</tr>
						</c:forEach>

					</table>
					<button class="btn mealinsertbtn" onclick="openModal('breakfast')">➕추가</button>
				</div>

				<div class="meal">
					<h2>점심</h2>
					<table id="lunchTable">
						<tr>
							<th>시간</th>
							<th>메뉴</th>
							<th>(g)</th>
							<th>(Kcal)</th>
						</tr>
						<c:forEach var="meal" items="${mealList}">
							<tr>
								<td>${meal.dietFoodDate}</td>
								<td>${meal.dietFoodName}</td>
								<td>${meal.capacity}</td>
								<td>${meal.kcal}</td>
							</tr>
						</c:forEach>

					</table>
					<button class="btn mealinsertbtn" onclick="openModal('lunch')">➕추가</button>

				</div>

				<div class="meal">
					<h2>저녁</h2>
					<table id="dinnerTable">
						<tr>
							<th>시간</th>
							<th>메뉴</th>
							<th>(g)</th>
							<th>(Kcal)</th>
						</tr>
						<c:forEach var="meal" items="${mealList}">
							<tr>
								<td>${meal.dietFoodDate}</td>
								<td>${meal.dietFoodName}</td>
								<td>${meal.capacity}</td>
								<td>${meal.kcal}</td>
							</tr>
						</c:forEach>

					</table>
					<button class="btn mealinsertbtn" onclick="openModal('dinner')">➕추가</button>
				</div>

				<div class="memo">
					<h2>메모</h2>
					<textarea id="memoInput" placeholder="MEMO"></textarea>
				</div>
			</div>
			<div class="mealapi">
				<div class="list-header">
					<ul class="list-content">
						<li>식단 정보</li>
					</ul>
					<span class="list-header-left"> 
					<input type="text" id="keyword" class="form-control">
						<button type="button" class="btn btn-search">검색</button>
					</span> <span class="list-header-right"></span>
				</div>
			</div>
		</div>
		<form name="mealForm" method="post">
			<div id="mealModal" class="modal">
				<div class="modal-content">
					<h3>식사 추가</h3>
					<input type="hidden" name="content" id="mealNum"> <select
						name="mealTime" class="form-select">
						<option value="">선 택</option>
						<option value="아침">아침</option>
						<option value="점심">점심</option>
						<option value="저녁">저녁</option>
						<option value="간식">간식</option>
					</select> <input type="text" name="content" id="mealName"
						placeholder="식단 입력"> <input type="text" name="content"
						id="mealDate" placeholder="식단 일자"> <input type="text"
						name="content" id="mealCapacity" placeholder="용량(g)"> <input
						type="text" name="content" id="mealKcal" placeholder="칼로리(Kcal)">
					<button class="mealinsertbtn" id="mealinsertbtn"
						onclick="mealInsertOk()">등록</button>
					<button class="mealinsertbtn" onclick="closeModal()">취소</button>
				</div>
			</div>
		</form>

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>


<script>

function ajaxFun(url, method, formData, dataType, fn, file = false) {
	const settings = {
			type: method, 
			data: formData,
			dataType:dataType,
			success:function(data) {
				fn(data);
			},
			beforeSend: function(jqXHR) {
			},
			complete: function () {
			},
			error: function(jqXHR) {
				console.log(jqXHR.responseText);
			}
	};
	
	if(file) {
		settings.processData = false;  // file 전송시 필수. 서버로전송할 데이터를 쿼리문자열로 변환여부
		settings.contentType = false;  // file 전송시 필수. 서버에전송할 데이터의 Content-Type. 기본:application/x-www-urlencoded
	}
	
	$.ajax(url, settings);
}

$(function(){
	$(".btn-search").click(function(){
	let kwd = $('#keyword').val().trim();
		if( ! kwd) {
		return false;
		}
	searchMeal(kwd);
	});
		
	function searchMeal(food_Name) {		
		let spec = "http://apis.data.go.kr/1390802/AgriFood/MzenFoodCode/getKoreanFoodList";
		let serviceKey = "XEaV5QgvI1upo1GKGdV%2BXBduU4VuKBUFLpSNAL313umn9xxXhWopCvRCu3c2MXliK2sPxFa6Ba11YnlQJV6uNw%3D%3D";
//		let serviceKey = "XEaV5QgvI1upo1GKGdV+XBduU4VuKBUFLpSNAL313umn9xxXhWopCvRCu3c2MXliK2sPxFa6Ba11YnlQJV6uNw==";
		let Page_No = 1;
		let Page_Size = 10;
		
		let qs = "serviceKey="+ encodeURIComponent(serviceKey);
		qs += "&Page_No="+ Page_No;
		qs += "&Page_Size="+ Page_Size;
		qs += "&keyword=" + encodeURIComponent(keyword);
		qs += "&food_Name=" + food_Name;
		
		const fn = function(data) {
			$('.list-header-right').empty();
			$('.list-content').empty();
			
			printXML(data);
		};
		
		ajaxFun(spec, 'GET', qs, 'xml', fn);	
	}
	
	function printXML(data) {
		console.log(data);

	}
});
</script>

</body>
</html>