<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- fmt 태그라이브러리를 제거하거나 필요에 따라 사용 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>OI - 운동 기록 리스트</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout/footer_layout.css">
    <jsp:include page="/WEB-INF/views/layout/headimported.jsp" />
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </header>
    <main class="container my-5">
        <h1 class="mb-4">운동 기록 리스트</h1>
        
        <!-- 메시지 출력 -->
        <c:if test="${not empty message}">
            <div class="alert ${messageType == 'error' ? 'alert-danger' : 'alert-success'}" role="alert">
                ${message}
            </div>
        </c:if>

        <!-- 운동 기록 리스트 테이블 -->
        <c:if test="${empty recordList}">
            <div class="alert alert-info" role="alert">
                운동 기록이 없습니다.
            </div>
        </c:if>
        
        <c:if test="${not empty recordList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
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
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty record.exerciseDate}">
                                            ${record.exerciseDate}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${record.exerciseName}</td>
                                <td>${record.exerciseContent}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty record.exerciseStart}">
                                            ${record.exerciseStart}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty record.exerciseEnd}">
                                            ${record.exerciseEnd}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${record.exercisecnt}</td>
                                <td>${record.exerciseunit}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/todayworkout/edit?exerciseNum=${record.exerciseNum}" class="btn btn-sm btn-primary" aria-label="수정">
                                        <i class="fas fa-edit"></i> 수정
                                    </a>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/todayworkout/deleteRecord" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline;">
                                        <input type="hidden" name="exerciseNum" value="${record.exerciseNum}">
                                        <button type="submit" class="btn btn-sm btn-danger" aria-label="삭제">
                                            <i class="fas fa-trash-alt"></i> 삭제
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        
        <!-- 운동 기록 추가 링크 -->
        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/todayworkout/main" class="btn btn-success">
                <i class="fas fa-plus-circle"></i> 운동 기록 추가하기
            </a>
        </div>
    </main>
    <footer class="mt-auto">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </footer>
</body>
</html>