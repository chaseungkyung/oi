<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
              <div>
            	<c:set var="fileroot" value="${pageContext.request.contextPath}/uploads/photo/${sessionScope.member.saveprofile}" />
               	<c:set var="defaultfile" value="${pageContext.request.contextPath}/resources/images/blank-profile.png"/>
            	<img src="${sessionScope.member.saveprofile == 'default' ? defaultfile: fileroot }" alt="프로필 사진" class="mypage-profile" style="height: 40px; width: auto;">
            </div>
            <p>
                <span>${member.nickname}님</span>
            </p>
            <button id="toggleButton" class="btn btn-light btn-sm">&lt;</button>
            <p>
                <i class="fa-solid fa-user"></i><span>나의 내역</span>
            </p>
            <a href="${pageContext.request.contextPath}/mypage/comment"><i class="fa-solid fa-pen"></i><span>댓글 내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/mygoods"><i class="fa-solid fa-circle-user"></i><span>내가 쓴 글 내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/boardlike"><i class="fa-solid fa-thumbs-up"></i><span>게시글 찜 내역</span></a> 
        </nav>
        
        <!-- 메인 필드 -->
<main class="col-md-9">
    <h2 class="mb-4">나의 중고 게시글</h2>
    <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
    <table border="1" class="table">
        <thead>
            <tr>
                <th>글 번호</th>
                <th>상품 이름</th>
                <th>가격</th>
                <th>카테고리</th>
                <th>게시판 분류</th>
                <th>작성 날짜</th>
                <th>상세 보기</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="goods" items="${myGoodsList}">
                <tr>
                    <td>${goods.goodsListNum}</td>
                    <td>${goods.goodsName}</td>
                    <td><fmt:formatNumber value="${goods.goodsPrice}" type="number" />원</td>
                    <td>${goods.categoryName}</td>
                    <td>${goods.postCateName}</td>
                    <td>${goods.goodsDate}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/marketplace/article?goodsListNum=${goods.goodsListNum}"
                           class="btn btn-primary btn-sm">
                            상세 보기
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty myGoodsList}">
                <tr>
                    <td colspan="7" class="text-center">작성한 중고거래 게시물이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 페이징 처리 부분 -->
    <div class="page-navigation mt-4">
        <c:if test="${dataCount != 0}">${paging}</c:if>
        <c:if test="${dataCount == 0}"><p>페이지 정보가 없습니다.</p></c:if>
    </div>
</main>
	</div>
 	
 	<footer><jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include></footer>
    <script src="${pageContext.request.contextPath}/resources/js/mypage/mypage.js"></script>
</body>
</html>