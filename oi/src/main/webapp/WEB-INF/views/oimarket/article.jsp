<%--
  Created by IntelliJ IDEA.
  User: ujinjo
  Date: 2024. 12. 9.
  Time: 오후 4:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
    <jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
    <style type="text/css">
        .item {cursor: pointer; }
        .item img { display: block; width: 100%; height: 200px; border-radius: 5px; }
        .item img:hover { scale: 100.7%; }
        .item .item-title {
            font-size: 14px;
            font-weight: 500;
            padding: 10px 2px 0;

            width: 175px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }

        .main-image img {
            max-width: 100%;
            height: auto;
        }

        .thumb-img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border: 1px solid #ddd;
            border-radius: 5px;
            cursor: pointer;
            transition: transform 0.2s;
        }

        .thumb-img:hover {
            transform: scale(1.1);
        }

        .card {
            cursor: pointer;
            transition: box-shadow 0.3s ease;
        }

        .card:hover {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        main.container {
            max-width: 900px; /* 원하는 너비로 조정 */
            margin: 0 auto;   /* 가운데 정렬 */
        }
        .heart-icon {
            cursor: pointer; /* 마우스 커서를 손 모양으로 변경 */
        }
    </style>
</head>
<body>
<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>

    <main class="container mt-4">
        <div class="row">
            <!-- 이미지 슬라이더 -->
            <div class="col-md-6">
                <div class="main-image mb-3">
                    <img id="mainImg" src="https://via.placeholder.com/500" alt="상품 이미지" class="img-fluid rounded">
                </div>
                <div class="thumbnail-images d-flex">
                    <img src="https://via.placeholder.com/100" class="thumb-img me-2" onclick="changeImage(this)">
                    <img src="https://via.placeholder.com/100" class="thumb-img me-2" onclick="changeImage(this)">
                    <img src="https://via.placeholder.com/100" class="thumb-img me-2" onclick="changeImage(this)">
                </div>
            </div>

            <!-- 상품 정보 -->
            <div class="col-md-6">
                <h2 class="fw-bold">${dto.goodsName}</h2>
                <p class="text-danger fs-4 fw-bold">${dto.goodsPrice}</p>
                <p class="text-muted">작성자:${dto.memberId}</p>
                <p class="mt-3">${dto.goodsExp}</p>
                <i id="heartIcon" class="bi bi-suit-heart heart-icon fs-4 me-3" onclick="toggleHeart()"></i>
<%--                <i class="bi bi-suit-heart-fill heart-icon fs-4 me-3"></i>--%>
                <button class="btn btn-dark w-30 mb-2">댓글달기</button>


                <c:choose>
                    <c:when test="${sessionScope.member.userId==dto.memberId}">
                        <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/album/update?num=${dto.goodsListNum}&page=${page}';">수정</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-light" disabled>수정</button>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${sessionScope.member.userId==dto.memberId}">
                        <button type="button" class="btn btn-light" onclick="deleteOk();">삭제</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-light" disabled>삭제</button>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <!-- 관련 상품 추천 섹션 -->
        <section class="mt-5">
            <h4 class="mb-3">관련 상품</h4>
            <div class="row">
                <div class="col-6 col-md-3 mb-3">
                    <div class="card">
                        <img src="https://via.placeholder.com/200" class="card-img-top" alt="관련 상품">
                        <div class="card-body">
                            <p class="card-title">관련 상품 1</p>
                            <p class="card-text fw-bold text-danger">₩20,000</p>
                        </div>
                    </div>
                </div>
                <div class="col-6 col-md-3 mb-3">
                    <div class="card">
                        <img src="https://via.placeholder.com/200" class="card-img-top" alt="관련 상품">
                        <div class="card-body">
                            <p class="card-title">관련 상품 2</p>
                            <p class="card-text fw-bold text-danger">₩30,000</p>
                        </div>
                    </div>
                </div>
                <!-- 추가 상품 카드 -->
            </div>
        </section>
    </main>
<script type="text/javascript">
    function toggleHeart() {
        const heartIcon = document.getElementById("heartIcon");
        if (heartIcon.classList.contains("bi-suit-heart")) {
            heartIcon.classList.remove("bi-suit-heart");
            heartIcon.classList.add("bi-suit-heart-fill");
        } else {
            heartIcon.classList.remove("bi-suit-heart-fill");
            heartIcon.classList.add("bi-suit-heart");
        }
    }
</script>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</footer>
</body>


</html>
