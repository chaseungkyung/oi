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
        .reply .form-header {
            border-bottom: 1px solid #e9ecef;
            padding-bottom: 10px;
        }

        .reply .input-group textarea {
            border-radius: 0.25rem 0 0 0.25rem;
        }

        .reply .btnSendReply {
            border-radius: 0 0.25rem 0.25rem 0;
        }

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
            font-family : 'Arial', sans-serif; !important;
            background-color: #ffffff !important;
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

            <div class="col-md-6">
                <div id="carouselExampleIndicators" class="carousel slide">
                   <div class="carousel-indicators">
                       <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                       <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                       <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                   </div>

                <div class="carousel-inner">
                    <c:forEach var="dto" items="${listFile}" varStatus="status">
                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                <img src="${pageContext.request.contextPath}/uploads/photo/${dto.imageFilename}"
                                     class="d-block w-100 h-70 img-fluid rounded" style="max-height: 450px;" alt="상품 이미지">
                        </div>
                    </c:forEach>

                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>

                </div>
            </div>



            <!-- 상품 정보 -->
            <div class="col-md-6">
                <h2 class="fw-bold">${dto.goodsName}</h2>
                <p class="text-danger fs-4 fw-bold"><fmt:formatNumber value="${dto.goodsPrice}" type="number" groupingUsed="true" />원</p>
                <p class="text-muted">작성자:${dto.memberId}</p>
                <p class="mt-3">${dto.goodsExp}</p>
                <i id="heartIcon" class="bi bi-suit-heart heart-icon fs-4 me-3" onclick="toggleHeart()"></i>
<%--                <i class="bi bi-suit-heart-fill heart-icon fs-4 me-3"></i>--%>
                <button type="button"
                        class="btn btn-outline-primary btnSendBoardLike" title="좋아요">
                    <i
                            class="bi ${isUserLike ? 'bi-hand-thumbs-up-fill':'bi-hand-thumbs-up'}"></i>&nbsp;&nbsp;<span
                        id="boardLikeCount">${dto.goodsHitCnt}</span>
                </button>


                <c:choose>
                    <c:when test="${sessionScope.member.userId==dto.memberId}">
                        <button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/marketplace/update?num=${dto.goodsListNum}&page=${page}';">수정</button>
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


        <div class="reply mt-4">
            <div class="form-header d-flex align-items-center justify-content-between">
                <span class="bold fs-5">댓글</span>
                <span class="text-muted small"> 거래용 댓글</span>
                <button id="toggleReplyBtn" type="button" class="btn btn-sm btn-outline-secondary ms-3">댓글 숨기기</button>
            </div>

            <div id="replyContent" class="mt-3">
                <form name="replyForm" method="post" class="mb-4">
                    <table class="table table-borderless reply-form">
                        <tr>
                            <td><textarea class="form-control" name="content"></textarea></td>
                        </tr>
                        <tr>
                            <td align="right">
                                <button type="button" class="btn btn-light btnSendReply">댓글 등록</button>
                            </td>
                        </tr>
                    </table>
                </form>

                <div id="listReply"></div>
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
    $(function () {
        $('#toggleReplyBtn').click(function () {
            const $replyContent = $('#replyContent');
            if ($replyContent.is(':visible')) {
                $replyContent.hide(); // 댓글 영역 숨기기
                $(this).text('댓글 보기'); // 버튼 텍스트 변경
            } else {
                $replyContent.show(); // 댓글 영역 보이기
                $(this).text('댓글 숨기기'); // 버튼 텍스트 변경
            }
        });
    });

    // function toggleHeart() {
    //     const heartIcon = document.getElementById("heartIcon");
    //     if (heartIcon.classList.contains("bi-suit-heart")) {
    //         heartIcon.classList.remove("bi-suit-heart");
    //         heartIcon.classList.add("bi-suit-heart-fill");
    //     } else {
    //         heartIcon.classList.remove("bi-suit-heart-fill");
    //         heartIcon.classList.add("bi-suit-heart");
    //     }
    // }










    function login() {
        location.href = '${pageContext.request.contextPath}/member/login'; //수정해야함
    }
    function ajaxFun(url, method, formData, dataType, fn, file=false) {
        const settings = {

            type: method,
            data: formData,
            dataType: dataType,
            success: function(data) {
                fn(data);
            },
            beforeSend: function(jqXHR) {
                jqXHR.setRequestHeader('AJAX', true);
            },
            complete:function(){
            },
            error: function(jqXHR) {
                if(jqXHR.status === 403) {
                    login();
                    return false;
                } else if(jqXHR.status === 406 ){
                    alert('요청 처리 실패');
                    return false;
                }
                console.log(jqXHR.responseText);
            }
        };
        if(file) {
            settings.processData = false;
            settings.contentType = false;

        }
        $.ajax(url, settings);
    }
    $(function (){
        $('.btnSendBoardLike').click(function (){
            const $i = $(this).find('i');
            let userLiked = $i.hasClass('bi-hand-thumbs-up-fill');
            let  msg = userLiked ? "게시글 찜을 취소하시겠습니까?" :"게시글에 공감하시겠습니까?";

            if (! confirm(msg)){
                return false;
            }

            let  url = "${pageContext.request.contextPath}/marketplace/insertgoodsLike";
            let query ="num=${dto.goodsListNum}&userLiked="+userLiked;

            const fn = function (data){
                console.log("AJAX 응답 데이터:", data);

                let state = data.state;
                if (state=='true'){
                    if(userLiked){
                        $i.removeClass('bi-hand-thumbs-up-fill').addClass('bi-hand-thumbs-up');
                    }else {
                        $i.removeClass('bi-hand-thumbs-up').addClass('bi-hand-thumbs-up-fill');
                    }
                    let count = data.goodsHitCnt;
                    $('#boardLikeCount').text(count);

                }else if (state=== 'liked'){
                    alert('공감은 한번만 가능');
                }else {
                    alert('공감처리 실패~!');
                }
            };
            ajaxFun(url,'post',query,'json',fn)

        })
    })

    $(function (){
        $('.btnSendReply').click(function (){
            const $tb = $(this).closest('table');
            let content = $tb.find('textarea').val().trim();
            if (!content){
                $tb.find('textarea').focus();
                return false;
            }
            let num = ${dto.goodsListNum}; // 서버 값에서 goodsListNum을 받아옴
            let url = '${pageContext.request.contextPath}/marketplace/insertReply';
            let query = { num: num, content: content, parentNum: 0 };

            const fn = function (data){
                if (data.state==='true'){
                    $tb.find('textarea').val('');
                    listPage(1);
                }else {
                    alert("댓글 등록이 실패했습니다");
                }
            };
            ajaxFun(url,'post',query,'json',fn);
        })
    })
    $(function (){
        listPage(1);
    });
    function listPage(page){
        let url ='${pageContext.request.contextPath}/marketplace/listReply';
        let query = 'num=${dto.goodsListNum}&pageNo='+page;
        let selector = '#listReply';
        const fn = function (data){
            $(selector).html(data);
        };
        ajaxFun(url,'GET',query,'text',fn);
    }
    // 댓글별 답글 리스트
    function listReplyAnswer(parentNum) {
        let url = '${pageContext.request.contextPath}/marketplace/listReplyAnswer';
        let query = 'parentNum=' + parentNum;
        let selector = '#listReplyAnswer' + parentNum;

        const fn = function (data) {
            $(selector).html(data);
        };

        ajaxFun(url, 'get', query, 'text', fn);
    }
    // 댓글별 답글 개수
    function countReplyAnswer(parentNum) {
        let url = '${pageContext.request.contextPath}/marketplace/listReplyAnswer';
        let query = 'parentNum=' + parentNum;

        const fn = function (data) {
            let count = data.count;
            let selector = '#answerCount' + parentNum;
            $(selector).html(count);
        };
        ajaxFun(url,'post',query,'json',fn);
    }
    // 답글 버튼
    $(function () {
        $('#listReply').on('click','.btnReplyAnswerLayout',function () {
            let replyNum = $(this).attr('data-replyNum');
            const $trAnswer = $(this).closest('tr').next();
            let isVisible = $trAnswer.is(':visible');

            if(isVisible){
                $trAnswer.hide();
            }else {
                $trAnswer.show();

                // 답글 리스트
                listReplyAnswer(replyNum);

                // 답글 개수
                countReplyAnswer(replyNum);
            }
        });
    });
    //답글등록
    $(function () {
        $('#listReply').on('click','.btnSendReplyAnswer', function () {
            let num = '${dto.goodsListNum}';
            let replyNum = $(this).attr('data-replyNum');
            const $td = $(this).closest('td');

            let content = $td.find('textarea').val().trim();
            if(! content ){
                $td.find('textarea').focus();
                return false;
            }

            let url = '${pageContext.request.contextPath}/marketplace/insertReply';
            let formData = {num:num,content:content,parentNum:replyNum};

            const fn = function (data) {
                $td.find('textarea').val('');

                if(data.state === 'true'){
                    listReplyAnswer(replyNum);
                    countReplyAnswer(replyNum);
                }
            };

            ajaxFun(url,'post',formData,'json',fn);
        });
    });
    // 댓글 삭제
    $(function () {
        $('#listReply').on('click','.deleteReply', function () {
            if(! confirm('게시글을 삭제 하시겠습니까 ? ')){
                return false;
            }
            let replyNum = $(this).attr('data-replyNum');
            let page = $(this).attr('data-pageNo');

            let url = '${pageContext.request.contextPath}/marketplace/deleteReply';
            let query = 'replyNum='+replyNum;

            const fn = function () {
                listPage(page);
            }

            ajaxFun(url,'post',query,'json',fn);
        });
    });
    // 댓글의 답글 삭제
    $(function () {
        $('#listReply').on('click','.deleteReplyAnswer', function () {
            if(! confirm('게시글을 삭제 하시겠습니까 ? ')){
                return false;
            }
            let replyNum = $(this).attr('data-replyNum');
            let parentNum = $(this).attr('data-parentNum');

            let url = '${pageContext.request.contextPath}/marketplace/deleteReply';
            let query = 'replyNum='+replyNum;

            const fn = function () {
                listReplyAnswer(parentNum);
                countReplyAnswer(parentNum);
            }

            ajaxFun(url,'post',query,'json',fn);
        });
    });


    function deleteOk(){
        if (confirm('게시글을 삭제 하시 겠습니까?')){
            let query = 'num=${dto.goodsListNum}&page=${page}';
            let url = '${pageContext.request.contextPath}/marketplace/delete?' + query;
            location.href=url;
        }
    }


</script>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/layout/footerimported.jsp" />
</footer>
</body>


</html>
