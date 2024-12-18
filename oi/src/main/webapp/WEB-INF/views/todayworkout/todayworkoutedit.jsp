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
        <h1>운동 기록 수정</h1>
        
        <!-- 메시지 출력 -->
        <c:if test="${not empty message}">
            <p class="${messageType}">
                ${message}
            </p>
        </c:if>

        <!-- 운동 기록 수정 폼 -->
        <form action="${pageContext.request.contextPath}/todayworkout/updateRecord" method="post">
            <!-- 운동 기록 번호 (숨김 필드) -->
            <input type="hidden" name="exerciseNum" value="${recordWorkDTO.exerciseNum}">
            
            <table>
                <tr>
                    <td><label for="exerciseDate">운동 날짜:</label></td>
                    <td><input type="date" id="exerciseDate" name="exerciseDate" value="${recordWorkDTO.exerciseDate}" required></td>
                </tr>
                <tr>
                    <td><label for="exerciseName">운동명:</label></td>
                    <td><input type="text" id="exerciseName" name="exerciseName" value="${recordWorkDTO.exerciseName}" required></td>
                </tr>
                <tr>
                    <td><label for="exerciseContent">운동 내용:</label></td>
                    <td><textarea id="exerciseContent" name="exerciseContent" required>${recordWorkDTO.exerciseContent}</textarea></td>
                </tr>
                <tr>
                    <td><label for="exerciseStart">운동 시작 시간:</label></td>
                    <td><input type="datetime-local" id="exerciseStart" name="exerciseStart" value="${recordWorkDTO.exerciseStart}" required></td>
                </tr>
                <tr>
                    <td><label for="exerciseEnd">운동 종료 시간:</label></td>
                    <td><input type="datetime-local" id="exerciseEnd" name="exerciseEnd" value="${recordWorkDTO.exerciseEnd}" required></td>
                </tr>
                <tr>
                    <td><label for="exercisecnt">운동 횟수:</label></td>
                    <td><input type="number" id="exercisecnt" name="exercisecnt" value="${recordWorkDTO.exercisecnt}" required></td>
                </tr>
                <tr>
                    <td><label for="exerciseunit">운동 단위:</label></td>
                    <td><input type="text" id="exerciseunit" name="exerciseunit" value="${recordWorkDTO.exerciseunit}" required></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="submit">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/todayworkout/list">취소</a>
                    </td>
                </tr>
            </table>
        </form>
    </main>
    <footer>
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </footer>

</body>
</html>