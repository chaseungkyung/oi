<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OI</title>
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/mypage/mypage.css">
</head>
<body>
    <header><jsp:include page="/WEB-INF/views/layout/header.jsp"/></header>
    <!-- 왼쪽 사이드 바 -->
    <div class="mypage-container">
        <nav class="my-history-nav">
            <div><img src="${pageContext.request.contextPath}/resources/images/blank-profile.png" alt="프로필 사진" class="mypage-profile" style="height: 40px; width: auto;"></div>
            <p>
                <span>${member.nickName}님</span>
            </p>
            <button id="toggleButton" class="btn btn-light btn-sm">&lt;</button>
            <p>
                <i class="fa-solid fa-user"></i><span>나의 내역</span>
            </p>
            <a href="#"><i class="fa-solid fa-cart-shopping"></i><span>판매
                    내역</span></a> 
            <a href="#"><i class="fa-solid fa-pen"></i><span>댓글 내역</span></a> 
            <a href="#"><i class="fa-solid fa-circle-user"></i><span>내가 쓴 글 내역</span></a> 
            <a href="#"><i class="fa-solid fa-thumbs-up"></i><span>게시글 찜 내역</span></a> 
            <a href="#"><i class="fa-solid fa-heart"></i><span>오운완 좋아요 내역</span></a>
        </nav>

        <main>
            <!-- 여기서부터 하나의 form으로 합침 -->
            <form id="combinedForm" method="post" action="${pageContext.request.contextPath}/mypage/updatePersonal">
                <div class="content">
                    <div class="personal-info">
                        <div class="personal-header d-flex justify-content-between align-items-center">
                            <h2>개인정보 등록 및 수정</h2>
                            <button class="btn btn-primary btn-sm btn-editps" type="button"
                                id="editPersonal" data-bs-toggle="collapse"
                                data-bs-target="#personalInfoCollapse" aria-expanded="false"
                                aria-controls="personalInfoCollapse" onclick="editPersonal()">
                                수정하기
                            </button>
                        </div>
    
                        <!-- Collapse 영역 -->
                        <div class="collapse" id="personalInfoCollapse">
                            <div class="personal-id mb-3">
                                <label for="userId" class="form-label">아이디</label> 
                                <input type="text" id="userId" name="userId" class="form-control" readonly
                                    value="${member.memberId}">
                            </div>
                            <div class="personal-password mb-3">
                                <label for="userPwd" class="form-label">패스워드</label> 
                                <input type="password" id="userPwd" name="userPwd" class="form-control"
                                    value="${member.memberPw}">
                            </div>
                            <div class="personal-nickname mb-3">
                                <label for="usernickName" class="form-label">닉네임</label> 
                                <input type="text" id="usernickName" name="usernickName" class="form-control"
                                    value="${member.nickName}">
                            </div>
                            <div class="personal-birth mb-3">
                                <label for="birthDate" class="form-label">생년월일</label> 
                                <input type="date" id="birthDate" class="form-control" readonly
                                    value="${memberDetails.birth}">
                            </div>
                            <div class="personal-email mb-3">
                                <label for="email" class="form-label">이메일</label> 
                                <input type="email" id="email" name="email" class="form-control" readonly
                                    value="${memberDetails.email}">
                            </div>
                            <div class="personal-zipcode mb-3">
                                <label for="zipCode" class="form-label">우편번호</label> 
                                <input type="text" id="zipCode" name="zipCode" class="form-control" value="04598"
                                    maxlength="5">
                            </div>
                            <div class="personal-address mb-3">
                                <label for="address" class="form-label">주소</label> 
                                <input type="text" id="address" name="address" class="form-control"
                                    value="이태원2동 221-1 3층">
                                <button class="btn btn-primary btn-zipcode mt-2" type="button"
                                    onclick="daumPostcode();">우편번호 검색</button>
                            </div>
                            <div class="personal-name mb-3">
                                <label for="name" class="form-label">이름</label> 
                                <input type="text" id="name" class="form-control" readonly
                                    value="${memberDetails.name}">
                            </div>
                            <div class="personal-profileimage mb-3">
                                <label for="profileImage" class="form-label">프로필 사진</label> 
                                <input type="file" class="form-control" id="profileImage"
                                    accept="image/*">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="physical-info">
                    <div class="physical-header d-flex justify-content-between align-items-center">
                        <h2>신체정보 등록 및 수정</h2>
                        <button class="btn btn-primary btn-sm btn-editph" type="button"
                            id="editPhysical" data-bs-toggle="collapse"
                            data-bs-target="#physicalInfoCollapse" aria-expanded="false"
                            aria-controls="physicalInfoCollapse" onclick="editPhysical()">
                            수정하기
                        </button>
                    </div>

                    <!-- Collapse 영역 -->
                    <div class="collapse" id="physicalInfoCollapse">
                        <div class="physical-gender mb-3">
                            <label class="form-label">성별</label>
                            <div class="d-inline-flex align-items-center gap-3">
                                <div class="form-check ms-3">
                                    <input class="form-check-input" type="radio" id="male"
                                        name="gender" value="male" checked> 
                                    <label class="form-check-label" for="male">남성</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" id="female"
                                        name="gender" value="female"> 
                                    <label class="form-check-label" for="female">여성</label>
                                </div>
                            </div>
                        </div>
                        <div class="physical-date mb-3">
                            <label for="registerDate" class="form-label">등록날짜</label> 
                            <input type="date" id="registerDate" name="registerDate" class="form-control"
                                value="${bodyRecord.bodyRecordDate}">
                        </div>
                        <div class="physical-height mb-3">
                            <label for="height" class="form-label">키(cm)</label> 
                            <input type="number" id="height" name="height" class="form-control"
                                value="${bodyRecord.height}">
                        </div>
                        <div class="physical-weight mb-3">
                            <label for="weight" class="form-label">몸무게(kg)</label> 
                            <input type="number" id="weight" name="weight" class="form-control"
                                value="${bodyRecord.weight}">
                        </div>
                        <div class="physical-bmr mb-3">
                            <label for="bmr" class="form-label">기초대사량</label> 
                            <input type="number" id="bmr" name="bmr" class="form-control" value="1500">
                        </div>
                        <div class="physical-bmi mb-3">
                            <label for="bmi" class="form-label">체지방률(%)</label> 
                            <input type="number" id="bmi" name="bmi" class="form-control"
                                value="${bodyRecord.bodyFat}">
                        </div>
                        <div class="physical-muscle mb-3">
                            <label for="muscle" class="form-label">근육량(%)</label> 
                            <input type="number" id="muscle" name="muscle" class="form-control"
                                value="${bodyRecord.bodyMuscle}">
                        </div>
                    </div>
                </div>

                <div class="psbtn">
                    <button class="btn btn-primary btn-sm btn-phok" type="submit"
                        id="personalRegisterButton">
                        수정완료
                    </button>
                </div>
            </form>
        </main>
    </div>

    <footer><jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include></footer>
    <script
        src="${pageContext.request.contextPath}/resources/js/mypage/daumpost.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/js/mypage/mypage.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/js/mypage/mypageok.js"></script>
    <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
</body>
</html>
