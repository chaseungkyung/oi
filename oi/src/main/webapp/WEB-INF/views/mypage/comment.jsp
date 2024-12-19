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
            <a href="#"><i class="fa-solid fa-cart-shopping"></i><span>판매내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/comment"><i class="fa-solid fa-pen"></i><span>댓글 내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/mycomment"><i class="fa-solid fa-circle-user"></i><span>내가 쓴 글 내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/boardlike"><i class="fa-solid fa-thumbs-up"></i><span>게시글 찜 내역</span></a> 
            <a href="${pageContext.request.contextPath}/mypage/todayworklike"><i class="fa-solid fa-heart"></i><span>오운완 좋아요 내역</span></a>
        </nav>
		
		<!-- 메인 필드 -->
		<main class="col-md-9">
                <h2 class="mb-4">작성한 댓글 목록</h2>

                <h3 class="mt-3">WOTD 댓글</h3>
                <c:forEach var="comment" items="${commentMap.wotdComments}">
                    <div class="border p-3 mb-3 rounded">
                        <p><strong>댓글 내용:</strong> ${comment.commentContent}</p>
                        <p><strong>작성 날짜:</strong> ${comment.commentDate}</p>
                    </div>
                </c:forEach>

				<h3 class="mt-4">중고거래 댓글</h3>
				
				<!-- 현재 페이지 파라미터의 기본값 설정 -->
				<c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
				
				<c:forEach var="comment" items="${commentMap.goodsComments}">
				    <div class="border p-3 mb-3 rounded">
				        <a href="${pageContext.request.contextPath}/marketplace/article?page=${currentPage}&goodsListNum=${comment.goodsListNum}" 
				           class="text-decoration-none fw-bold fs-5">
				            ${comment.postTitle}
				        </a>
				        <p><strong>댓글 내용:</strong> ${comment.commentContent}</p>
				        <p><strong>작성 날짜:</strong> ${comment.commentDate}</p>
				    </div>
				</c:forEach>

                <c:if test="${empty commentMap.wotdComments && empty commentMap.goodsComments}">
                    <p class="text-center text-muted">작성한 댓글이 없습니다.</p>
                </c:if>

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