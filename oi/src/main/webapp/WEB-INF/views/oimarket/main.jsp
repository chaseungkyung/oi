<%--
  Created by IntelliJ IDEA.
  User: ujinjo
  Date: 2024. 12. 9.
  Time: 오후 3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
    <jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/record/recordmain.css">
    <style type="text/css">
        .item {
            cursor: pointer;
        }

        .item img {
            display: block;
            width: 100%;
            height: 100%; /* 높이 설정 */
            object-fit: cover; /* 비율에 맞게 자르기 */
            border-radius: 10px; /* 모서리를 둥글게 (값은 원하는 정도로 조절) */
            position: absolute;
            top: 0;
            left: 0;
        }

        .item .img-container {
            position: relative;
            width: 100%;
            padding-top: 100%; /* 정사각형 비율 유지 */
            overflow: hidden; /* 넘치는 이미지 숨기기 */
            border-radius: 10px; /* 모서리를 둥글게 */
        }



        .item .item-title {
            font-size: 14px;
            font-weight: 500;
            padding: 10px 2px 0;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            text-align: center; /* 텍스트 가운데 정렬 */
        }
        main {
            max-width: 900px;
            margin: 0 auto;
            padding: 10px;
        }

        @media (max-width: 768px) {
            main {
                max-width: 100%; /* 작은 화면에서는 전체 너비로 */
                padding: 5px; /* 여백도 줄임 */
            }
        }
    </style>
</head>
<body>
<header><jsp:include page="/WEB-INF/views/layout/header.jsp" /></header>
<main>
    <div class="container">
        <div class="body-container">
            <div class="body-title">
                <h3><i class="bi bi-image"></i> 오이 거래 </h3>
            </div>

            <div class="body-main">
                <div class="row mb-2 list-header">
                    <div class="col-auto me-auto">
<%--                        <p class="form-control-plaintext">--%>
<%--                            ${dataCount}개(${page}/${total_page} 페이지)--%>
<%--                        </p>--%>
                    </div>
                    <div class="col-auto">
                        <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/marketplace/registration';">사진올리기</button>
                    </div>

                </div>
                <div class="row row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-lg-5 px-1 py-1 g-2">
                    <c:forEach var="dto" items="${list}" varStatus="status">
                        <div class="col">
                            <div class="col border rounded p-1 border-0 item"
                                 onclick="location.href='${articleUrl}&goodsListNum=${dto.goodsListNum}';">
                                <div class="img-container">
                                    <img src="${pageContext.request.contextPath}/uploads/photo/${dto.file.saveFileName[0]}">
                                </div>
                                <p class="item-title">${dto.goodsName}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="page-navigation">
                    ${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
                </div>

            </div>
        </div>
    </div>



</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</footer>
</body>
</html>
