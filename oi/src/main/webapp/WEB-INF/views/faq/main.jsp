<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>

<jsp:include page="/WEB-INF/views/layout/headimported.jsp"/>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.nav-tabs .nav-link {
	min-width: 130px;
	background: #f3f5f7;
	border-radius: 0;
	border-top: 1px solid #dbdddf;
	border-right: 1px solid #dbdddf;
	color: #333;
	font-weight: 600;
}
.nav-tabs .nav-item:first-child .nav-link {
	border-left: 1px solid #dbdddf;
}

.nav-tabs .nav-link.active {
	background: #3d3d4f;
	color: #fff;
}
.tab-pane { min-height: 70px; }
</style>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
	<div class="container">
		<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-question-octagon"></i> 자주하는 질문 </h3>
			</div>
			
			<div class="body-main">

				<div>
					<ul class="nav nav-tabs" id="myTab" role="tablist">
						<li class="nav-item" role="presentation">
							<button class="nav-link active" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-selected="true" data-tab="0">모두</button>
						</li>
		
						<c:forEach var="vo" items="${listCategory}" varStatus="status">
							<li class="nav-item" role="presentation">
								<button class="nav-link" id="tab-${status.count}" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-selected="true" data-tab="${vo.faqCateNum}">${vo.faqCateName}</button>
							</li>
						</c:forEach>
		
					</ul>
				</div>
			
				<div class="tab-content pt-2" id="nav-tabContent">
					<div class="row py-3">
						<div class="col-3"></div>
						<div class="col-6">
							<form class="row" name="searchForm" method="post">
								<div class="col input-group">
									<input type="text" name="kwd" value="${kwd}" class="form-control rounded me-1">
									<button type="button" class="btn btn-light rounded" onclick="searchList()"> <i class="bi bi-search"></i> </button>
									
									<input type="hidden" id="searchType" value="all">
									<input type="hidden" id="searchValue" value="">
								</div>
							</form>
						</div>
						<div class="col-3"></div>
					</div>
					
					<div class="tab-pane fade show active" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content"></div>
					
				</div>				

			</div>
		</div>
	</div>
</main>

<script type="text/javascript">
function login() {
	location.href = '${pageContext.request.contextPath}/access/login';
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
			complete:function() {
			},
			error: function(jqXHR) {
				if(jqXHR.status === 403) {
					login();
					return false;
				} else if(jqXHR.status === 406) {
					alert('요청 처리가 실패했습니다.');
					return false;
				}
				
				console.log(jqXHR.responseText);
			}
	};
	
	if(file) {
		settings.processData = false; // 파일 전송시 필수. 서버로 보낼 데이터를 쿼리문자열로 변환 여부
		settings.contentType = false; // 파일 전송시 필수. contentType. 기본은 application/x-www-urlencoded
	}
	
	$.ajax(url, settings);
}

$(function(){
	listPage(1);
	
	// 탭이 변경될 때 실행
    $('button[role="tab"]').on('click', function(e){
    	resetSearch();
    	
    	listPage(1);
    });
});

// 검색 폼 초기화
function resetSearch() {
	const f = document.searchForm;
	f.kwd.value = '';
	
	$('#searchValue').val('');
}

function listPage(page) {
	const $tab = $('button[role="tab"].active');
	let faqCateNum = $tab.attr('data-tab');
	let schType = $('#searchType').val();
	let kwd = $('#searchValue').val();
	
	let url = '${pageContext.request.contextPath}/faq/list';
	let query = 'pageNo='+ page + '&faqCateNum=' + faqCateNum;
	if( kwd ) {
		query += '&schType=' + schType 
			+ '&kwd=' + encodeURIComponent(kwd);
	}

	let selector = '#nav-content';
	
	const fn = function(data) {
		$(selector).html(data);
	};
	
	ajaxFun(url, 'get', query, 'text', fn);
}

function searchList() {
	const f = document.searchForm;
	
	let kwd = f.kwd.value.trim();
	$('#searchValue').val(kwd);
	
	listPage(1);
}

function reloadFaq() {
	resetSearch();
	
	listPage(1);
}
</script>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/footerimported.jsp"/>
</body>
</html>
