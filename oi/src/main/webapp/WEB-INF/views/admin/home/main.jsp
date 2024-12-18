<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<title>OI</title>

<jsp:include page="/WEB-INF/views/layout/adminheadimported.jsp" />

</head>
<body>

	<jsp:include page="/WEB-INF/views/layout/admin_header.jsp" />

	<main>
		<jsp:include page="/WEB-INF/views/layout/left.jsp" />
		<div class="wrapper">
			<div class="body-container">
				<div class="body-main">
					<p>관리자 페이지 입니다.</p>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</body>
</html>