<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
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
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </header>
        <!-- 메시지 출력 -->
<main class="container my-5">
    
    <div id="alertContainer" class="alert alert-danger d-none" role="alert">운동 시작시간이 종료시간보다 더 늦을수 없습니다.</div>

    <!-- 운동 기록 입력 폼 -->
    <form action="${pageContext.request.contextPath}/todayworkout/insertRecord" method="post">
             <div class="mb-3">
                <label for="exerciseDate" class="form-label">운동 날짜</label>
                <input type="date" class="form-control" id="exerciseDate" name="exerciseDate" value="${recordWorkDTO.exerciseDate}" required>
            </div>
            <div class="mb-3">
                <label for="exerciseName" class="form-label">운동명</label>
                <input type="text" class="form-control" id="exerciseName" name="exerciseName" value="${recordWorkDTO.exerciseName}" required>
            </div>
            <div class="mb-3">
                <label for="exerciseContent" class="form-label">운동 내용</label>
                <textarea class="form-control" id="exerciseContent" name="exerciseContent" rows="3" required>${recordWorkDTO.exerciseContent}</textarea>
            </div>
            <div class="mb-3">
                <label for="exerciseStart" class="form-label">운동 시작 시간</label>
                <input type="datetime-local" class="form-control" id="exerciseStart" name="exerciseStart" value="${recordWorkDTO.exerciseStart}" required>
            </div>
            <div class="mb-3">
                <label for="exerciseEnd" class="form-label">운동 종료 시간</label>
                <input type="datetime-local" class="form-control" id="exerciseEnd" name="exerciseEnd" value="${recordWorkDTO.exerciseEnd}" required>
            </div>
            <div class="mb-3">
                <label for="exercisecnt" class="form-label">운동 횟수</label>
                <input type="number" class="form-control" id="exercisecnt" name="exercisecnt" value="${recordWorkDTO.exercisecnt}" required>
            </div>
            <div class="mb-3">
                <label for="exerciseunit" class="form-label">운동 단위(SET)</label>
                <input type="text" class="form-control" id="exerciseunit" name="exerciseunit" value="${recordWorkDTO.exerciseunit}" required>
            </div>
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">운동 등록</button>
            </div>
    </form>
        <!-- 운동 기록 리스트 페이지로 이동하는 링크 추가 -->
        <div class="d-flex justify-content-center mt-4">
    	<a href="${pageContext.request.contextPath}/todayworkout/list" class="btn btn-success btn-lg">
       	나의 운동 기록 리스트 보기
    	</a>
		</div>
    </main>
    <footer class="mt-auto">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    </footer>
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
<script src="${pageContext.request.contextPath}/resources/js/mypage/workvaild.js"></script>
</body>
</html>