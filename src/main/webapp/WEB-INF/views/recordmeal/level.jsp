<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> 회원 등급 </title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .mitsuping-card {
            border: 2px solid #f8b500;
            border-radius: 15px;
            padding: 20px;
            background-color: #fff3e0;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 300px;
            margin: auto;
        }
        .mitsuping-img {
            border-radius: 20%;
            width: 150px;
            height: 150px;
            object-fit: cover;
            margin-bottom: 15px;
        }
        .mitsuping-name {
            font-size: 1.5rem;
            font-weight: bold;
            color: #f8b500;
            margin-bottom: 10px;
        }
        .mitsuping-description {
            font-size: 1rem;
            color: #555;
        }
        .btn-mitsuping {
            background-color: #f8b500;
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn-mitsuping:hover {
            background-color: #e09e00;
        }
    </style>
</head>
<body class="bg-light">

    <div class="container my-5">
        <div class="mitsuping-card">
            <img src="${pageContext.request.contextPath}/resources/images/DUDEOJI.png" alt="레전드두더지" class="mitsuping-img">
            <div class="mitsuping-name">레전드두더지</div>
            <div class="mitsuping-description">
                축하합니다 <br>
                😊 레전드 두더지 등급 😊 <br>
                중고 거래시 두더wl 포인트 +10p 
            </div>
        </div>
    </div>

    <!-- Bootstrap JS 및 의존성 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
</body>
</html>