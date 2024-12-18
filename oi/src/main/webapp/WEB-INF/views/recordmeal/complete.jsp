<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<body>
		<div class="body-container">	

	        <div class="row justify-content-md-center mt-5">
	            <div class="col-md-8">
	                <div class="border rounded mt-5">
	                	<div class="border-bottom p-3">
                        	<h4 class="text-center fw-bold mb-0">뭐</h4>
                        </div>
		                <div class="pt-4 pb-4">
							<p class="text-center fs-6 mb-0" >메세지</p>
		                </div>
	                </div>
	            </div>
	        </div>
	        
	        <div class="row justify-content-md-center mt-1">
	        	<div class="col-md-8 d-grid">
	        		<button type="button" class="btn btn-lg btn-primary" onclick="location.href='${pageContext.request.contextPath}/';">메인화면으로 이동 <i class="bi bi-arrow-counterclockwise"></i> </button>
	        	</div>
	        </div>

		</div>
</body>
</html>