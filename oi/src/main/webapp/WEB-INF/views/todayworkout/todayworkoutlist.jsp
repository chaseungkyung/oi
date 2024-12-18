<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>OI - Today Workout Main</title>
   <title>OI</title>
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
<jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </header>
     <main>
        <h1>운동 기록 리스트</h1>
        
        <!-- 메시지 출력 -->
        <c:if test="${not empty message}">
            <p class="${messageType}">
                ${message}
            </p>
        </c:if>

        <!-- 운동 기록 리스트 테이블 -->
        <c:if test="${empty recordList}">
            <p>운동 기록이 없습니다.</p>
        </c:if>
        
        <c:if test="${not empty recordList}">
            <table border="1">
                <thead>
                    <tr>
                        <th>운동 날짜</th>
                        <th>운동명</th>
                        <th>운동 내용</th>
                        <th>운동 시작 시간</th>
                        <th>운동 종료 시간</th>
                        <th>운동 횟수</th>
                        <th>운동 단위</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${recordList}">
                        <tr>
                            <td>${record.exerciseDate}</td>
                            <td>${record.exerciseName}</td>
                            <td>${record.exerciseContent}</td>
                            <td>${record.exerciseStart}</td>
                            <td>${record.exerciseEnd}</td>
                            <td>${record.exercisecnt}</td>
                            <td>${record.exerciseunit}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/todayworkout/edit?exerciseNum=${record.exerciseNum}">수정</a>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/todayworkout/deleteRecord" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                    <input type="hidden" name="exerciseNum" value="${record.exerciseNum}">
                                    <button type="submit">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <!-- 운동 기록 추가 링크 -->
        <a href="${pageContext.request.contextPath}/todayworkout/main">운동 기록 추가하기</a>
    </main>
    <footer>
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </footer>

</body>
</html>