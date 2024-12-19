<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/entry/register.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/entry/register.js"></script>
</head>
<body>
	<header><jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include></header>
	<div class="registercontainer">
		<form name="signupForm" class="signup-form" id="signupForm"
			method="post">
			<h2>회원가입</h2>

			<!-- 이름 입력칸 -->
			<div class="form-group">
				<label for="username">이름</label> <input type="text" id="username"
					name="username" placeholder="이름을 입력하세요" required>
			</div>
			
			<div class="form-group nickname-group">
				<label for="nickname">닉네임</label>
				<div class="nickname-input">
					<input type="text" id="nickname" name="nickname"
						placeholder="닉네임을 입력하세요" required>
				</div>
				<small id="nicknameFeedback" class="feedback" data-valid="false"></small>
			</div>
			
			<!-- 아이디 입력칸과 중복검사 버튼 -->
			<div class="form-group id-group">
				<label for="userid">아이디</label>
				<div class="id-input">
					<input type="text" id="userid" name="userid"
						placeholder="아이디를 입력하세요" required>
					<button type="button" id="checkIdBtn" class="checkbtn">중복검사</button>
				</div>
				<small id="idFeedback" class="feedback" data-valid="false"></small>
			</div>


			<div class="form-group">
				<label for="password">비밀번호</label> <input type="password"
					id="password" name="password" placeholder="비밀번호를 입력하세요" required>
				<div class="feedback" id="password-feedback">비밀번호는 영어와 숫자만 가능하며, 최소 8자 이상, 12자 이하 이어야
					합니다.</div>
			</div>

			<div class="form-group">
				<label for="confirmpassword">비밀번호 확인</label> <input type="password"
					id="confirmpassword" name="confirmpassword"
					placeholder="비밀번호를 다시 입력하세요" required>
				<div class="feedback" id="confirm-password-feedback"
					style="color: red;">비밀번호가 일치하지 않습니다.</div>
			</div>

			<!-- 생년월일 -->
			<div class="form-group">
				<label for="dob">생년월일</label> <input type="date" id="dob" name="dob"
					required>
			</div>

			<!-- 전화번호 -->
			<div class="form-group">
				<label for="phone">전화번호</label>
				<div class="phone-input" id="phone">
					<select id="phone1" name="phone1" required>
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="016">016</option>
						<option value="017">017</option>
						<option value="018">018</option>
						<option value="019">019</option>
					</select> <input type="text" id="phone2" name="phone2" placeholder="1234"
						pattern="\d{3,4}" required> <input type="text" id="phone3"
						name="phone3" placeholder="5678" pattern="\d{4}" required>
				</div>
			</div>

			<!-- 이메일 -->
			<div class="form-group">
				<label for="email">이메일</label>
				<div class="email-input">
					<input type="text" id="email1" name="email1" placeholder="example"
						required> <span>@</span> <input type="text" id="email2"
						name="email2" placeholder="domain.com" required> <select
						id="emailDomain" name="emailDomain">
						<option value="self">직접 입력</option>
						<option value="gmail.com">gmail.com</option>
						<option value="naver.com">naver.com</option>
						<option value="daum.net">daum.net</option>
						<option value="hotmail.com">hotmail.com</option>
						<option value="outlook.com">outlook.com</option>
					</select>
				</div>
			</div>

			<!-- 주소 -->
			<div class="form-group">
				<label for="zipcode">주소</label>
				<div class="address-input">
					<input type="text" id="zipcode" name="zipcode" placeholder="우편번호"
						readonly required>
					<button name="findZipBtn" type="button" id="findZipBtn"
						onclick="searchPost();">우편번호 찾기</button>
				</div>
				<input type="text" id="address1" name="address1" readonly required>
				<input type="text" id="address2" name="address2"
					placeholder="상세주소를 입력하세요" required>
			</div>

			<button type="button" name="submitbtn" onclick="submitOk();">가입하기</button>
		</form>
	</div>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
	</footer>
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/entry/post.js"></script>
</body>
</html>