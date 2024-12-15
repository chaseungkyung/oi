<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OI</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
</head>
<body>
<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
<main>
		<div class="personal-info">
			<span>${member.nickName}님 오이에 오신 걸 환영합니다!</span>
			<h2>개인정보 등록</h2>
			<div>
        	<button class="btn btn-editps" type="button" onclick="editPersonal()">수정하기</button>
    		</div>
			<div>
				<label for="userId">아이디</label> <input type="text" id="userId"
					readonly value="${member.memberId}">
			</div>
			<div>
				<label for="userId">비밀번호</label> <input type="password" id="userId"
					readonly value="${member.memberPw}">
			</div>
			<div>
				<label for="userId">닉네임</label> <input type="text" id="userId"
					readonly value="${member.nickName}">
			</div>
			<div>
				<label for="birthDate">생년월일</label> <input type="date"
					id="birthDate" readonly value="${memberDetails.birth}">
			</div>
			<div>
				<label for="email">이메일</label> <input type="email" id="email"
					readonly value="${memberDetails.email}">
			</div>
			<div>
				<label for="zipCode">우편번호</label> <input type="text" id="zipCode"
					readonly value="04598" maxlength="5">
				<button class="btn btn-zipcode" type="button"
					onclick="daumPostcode();">우편번호 검색
				</button>
			</div>
			<div>
				<label for="address">주소</label> <input type="text" id="address"
					readonly value="이태원2동 221-1 3층">
			</div>
			<div>
				<label for="name">이름</label> <input type="text" id="name" readonly
					value="이승범">
			</div>
			<div>
				<label for="profileImage">프로필 사진</label> <input type="file"
					id="profileImage" readonly accept="image/*">
			</div>
		</div>

		<div class="physical-info">
			<h2>신체정보 등록</h2>
			<div>
        	<button class="btn btn-editps" type="button" onclick="editPhysical()">수정하기</button>
    		</div>
			<div>
				<label>성별</label>
				<div>
					<input type="radio" id="male" name="gender" value="male" readonly>
					<label for="male">남성</label> <input type="radio" id="female"
						name="gender" value="female" readonly> <label for="female">여성</label>
				</div>
			</div>
			<div>
				<label for="registerDate">등록날짜</label> <input type="date"
					id="registerDate" readonly value="${bodyRecord.bodyRecordDate}">
			</div>
			<div>
				<label for="height">키</label> <input type="number" id="height"
					readonly value="${bodyRecord.height}">
			</div>
			<div>
				<label for="weight">몸무게</label> <input type="number" id="weight"
					readonly value="${bodyRecord.weight}">
			</div>
			<div>
				<label for="bmr">기초대사량</label> <input type="number" id="bmr"
					readonly value="1500">
			</div>

			<div>
				<label for="bmi">체지방률</label> <input type="number" id="bmi"
					readonly value="${bodyRecord.bodyFat}">
			</div>
				
			<div>
				<label for="muscle">근육량</label> <input type="number" id="muscle"
					readonly value="${bodyRecord.bodyMuscle}">
			</div>
		</div>

		<p><span>나의 내역</span></p>
		<nav>
			<a href="#"><span>판매 내역</span></a>
			<a href="#"><span>댓글 내역</span></a>
			<a href="#"><span>내가 쓴 글 내역</span></a>
			<a href="#"><span>게시글 찜 내역</span></a>
			<a href="#"><span>오운완 좋아요 내역</span></a>
		</nav>
</main>
<footer><jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include></footer>
</body>
</html>