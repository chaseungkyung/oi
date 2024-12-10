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
                        <p class="form-control-plaintext">
<%--                            ${dataCount}개(${page}/${total_page} 페이지)--%>
                        </p>
                    </div>
                    <div class="col-auto">
                        <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/photo/write';">사진올리기</button>
                    </div>
                </div>

                <div class="row row-cols-5 px-1 py-1 g-2">
                    <c:forEach var="dto" items="" varStatus="status">
                        <div>
                            <div class="col border rounded p-1 item"
                                 onclick="location.href='${articleUrl}&num=${dto.num}';">
                                <img src="${pageContext.request.contextPath}/uploads/photo/${dto.imageFilename}">
                                <p class="item-title">${dto.subject}</p>
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
