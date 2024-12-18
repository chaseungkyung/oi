<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>

<jsp:include page="/WEB-INF/views/layout/adminheadimported.jsp"/>

<style type="text/css">
.body-main {
	max-width: 900px;
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

.btnSpanIcon, .btnCategoryAddOk { cursor: pointer; }
</style>

</head>
<body>

<jsp:include page="/WEB-INF/views/layout/admin_header.jsp"/>
	
<main>
	<jsp:include page="/WEB-INF/views/layout/left.jsp"/>
	
	<div class="wrapper">
		<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-question-octagon"></i> FAQ </h3>
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
					<div class="tab-pane fade show active" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content"></div>
					
					<div class="row py-3">
						<div class="col">
							<button type="button" class="btn btn-light" onclick="reloadFaq();" title="새로고침"><i class="bi bi-arrow-counterclockwise"></i></button>
						</div>
						<div class="col-6 text-center">
							<form class="row" name="searchForm" method="post">
								<div class="col-auto p-1">
									<select name="schType" class="form-select">
										<option value="all" ${schType=="all"?"selected":""}>제목+내용</option>
										<option value="faqTitle" ${schType=="faqTitle"?"selected":""}>제목</option>
										<option value="faqContent" ${schType=="faqContent"?"selected":""}>내용</option>
									</select>
								</div>
								<div class="col-auto p-1">
									<input type="text" name="kwd" value="${kwd}" class="form-control">
								</div>
								<div class="col-auto p-1">
									<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
									
									<input type="hidden" id="searchType" value="all">
									<input type="hidden" id="searchValue" value="">
								</div>
							</form>
						</div>
						<div class="col text-end">
							<button type="button" class="btn btn-light" onclick="categoryManage();">카테고리</button>
							<button type="button" class="btn btn-light" onclick="writeForm();">글올리기</button>
						</div>
					</div>				
	
				</div>
			</div>
			
		</div>
	</div>
</main>

<!-- FAQ 등록 및 수정 대화상자 -->
<div class="modal fade" id="myDialogModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="myDialogModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="myDialogModalLabel">FAQ</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body pt-1">
			</div>
		</div>
	</div>
</div>

<!-- 카테고리 대화상자 -->
<div class="modal fade" id="faqCategoryDialogModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="faqCategoryDialogModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="faqCategoryDialogModalLabel">FAQ 카테고리</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body pt-1">
			
				<form name="categoryForm" method="post">
					<table class="table table-bordered" >
						<thead class="table-light">
							<tr align="center">
								<th width="170">카테고리</th>
								<th width="120">활성</th>
								<th width="80">출력순서</th>
								<th>변경</th>
							</tr>
						</thead>
						<tbody>
							<tr align="center">
								<td> <input type="text" name="faqCateName" class="form-control"> </td>
								<td>
									<select name="enabled" class="form-select">
										<option value="1">활성</option>
										<option value="0">비활성</option>
									</select>
								</td>
								<td> <input type="text" name="orderNo" class="form-control"> </td>
								<td> <button type="button" class="btn btn-light btnCategoryAddOk">등록</button> </td>
							</tr>
						</tbody>
						<tfoot class="category-list"></tfoot> 
					</table>
				</form>
			
			</div>
		</div>
	</div>
</div>

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
	// $('#tab-0').addClass('active');
	
	listPage(1);
	
	// 탭이 변경될 때 실행
    $('button[role="tab"]').on('click', function(e){
    	searchReset();
    	
    	listPage(1);
    });
});

// 검색 폼 초기화
function searchReset() {
	const f = document.searchForm;
	f.schType.value = 'all';
	f.kwd.value = '';
	
	$('#searchType').val('all');
	$('#searchValue').val('');
}

// 리스트 및 페이징
function listPage(page) {
	const $tab = $('button[role="tab"].active');
	let faqCateNum = $tab.attr('data-tab');
	let schType = $('#searchType').val();
	let kwd = $('#searchValue').val();
	
	let url = '${pageContext.request.contextPath}/admin/faq/list';
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

// 검색
function searchList() {
	const f = document.searchForm;
	
	let schType = f.schType.value;
	let kwd = f.kwd.value.trim();
	
	$('#searchType').val(schType);
	$('#searchValue').val(kwd);
	
	listPage(1);
}

// 새로 고침
function reloadFaq() {
	searchReset();
	
	listPage(1);
}

// FAQ 등록 폼
function writeForm() {
	$('#myDialogModalLabel').text('자주하는 질문 등록');
	// $('#myDialogModal .modal-dialog').addClass('modal-lg');
	
	/*
	// 로그인이 풀린 경우 이상한 현상
	let url = '${pageContext.request.contextPath}/admin/faq/write';
	$('#myDialogModal .modal-body').load(url); // AJAX-GET
	$('#myDialogModal').modal("show");
	*/
	
	let url = '${pageContext.request.contextPath}/admin/faq/write';
    const fn = function(data) {
    	$('#myDialogModal .modal-body').html(data);
    	$('#myDialogModal').modal("show");
    };
    
    ajaxFun(url, 'get', null, 'text', fn);
}

// FAQ 등록 및 수정 완료
function sendOk(mode, page) {
    const f = document.faqForm;

    if(! f.faqCateNum.value) {
        alert('카테고리를 선택하세요. ');
        f.faqCateNum.focus();
        return;
    }
    
	let str = f.faqTitle.value;
    if(!str) {
        alert('제목을 입력하세요. ');
        f.faqTitle.focus();
        return;
    }

	str = f.faqContent.value;
    if( !str ) {
        alert('내용을 입력하세요. ');
        f.faqContent.focus();
        return;
    }
    
    let url = '${pageContext.request.contextPath}/admin/faq/' + mode;
    let formData = $('form[name=faqForm]').serialize();
    
    const fn = function(data) {
    	$('#myDialogModal .modal-body').empty();
    	$('#myDialogModal').modal('hide');
    	
    	if(mode === 'write') {
    		searchReset();
    		listPage(1);
    	} else {
    		listPage(page);
    	}
    };
    
    ajaxFun(url, 'post', formData, 'json', fn);
}

// FAQ 등록 또는 수정 취소
function sendCancel() {
	$('#myDialogModal .modal-body').empty();
	$('#myDialogModal').modal("hide");
}

// FAQ 수정 폼
function updateFaq(faqNum, page) {
	$('#myDialogModalLabel').text('자주하는 질문 수정');
	
	let url = '${pageContext.request.contextPath}/admin/faq/update';
    const fn = function(data) {
    	$('#myDialogModal .modal-body').html(data);
    	$('#myDialogModal').modal("show");
    };
    
    ajaxFun(url, 'get', {faqNum:faqNum, pageNo:page}, 'text', fn);
}

// FAQ 삭제
function deleteFaq(faqNum, page) {
	if(! confirm('게시글을 삭제하시겠습니까 ? ')) {
		return false;
	}
	
	let url = '${pageContext.request.contextPath}/admin/faq/delete';
    const fn = function(data) {
    	listPage(page);
    };
    
    ajaxFun(url, 'post', {faqNum:faqNum}, 'json', fn);
}

// 카테고리 관리
function categoryManage() {
   	$('#faqCategoryDialogModal').modal('show');
   	
   	listAllCategory();
}

// 카테고리 리스트
function listAllCategory() {
	let url = '${pageContext.request.contextPath}/admin/faq/listAllCategory';
	
	const fn = function(data) {
		$('.category-list').html(data)
	};
	
	ajaxFun(url, 'get', null, 'text', fn);
}

// 카테고리 등록
$(function(){
	$('#faqCategoryDialogModal').on('click', '.btnCategoryAddOk', function(){
		const $tr = $(this).closest('tr');
		
		let faqCateName = $tr.find('input[name=faqCateName]').val().trim();
		let enabled = $tr.find('select[name=enabled]').val();
		let orderNo = $tr.find('input[name=orderNo]').val();
		
		if(! faqCateName ) {
			$tr.find('input[name=faqCateName ]').focus();
			return false;
		}
		
		if(! /^\d+$/.test(orderNo)) {
			$tr.find('input[name=orderNo]').focus();
			return false;
		}
		
		let url = '${pageContext.request.contextPath}/admin/faq/insertCategory';
		let formData = {faqCateName:faqCateName, enabled:enabled, orderNo:orderNo};
		
		const fn = function(data) {
			$('form[name=categoryForm]')[0].reset();
			
			listAllCategory();
		};
		
		ajaxFun(url, 'post', formData, 'json', fn);
	});
});

// 카테고리 수정
$(function(){
	let $cloneTr = null;
	
	$('#faqCategoryDialogModal').on('click', '.btnCategoryUpdate', function(){
		const $tr = $(this).closest('tr');
		
		$cloneTr = $tr.clone(true); // clone //수정 전 데이터를 그대로 복사해둔 뒤, 수정 취소를 해도 다시 돌려주는 것 (true)를 써주어야 이벤트까지 복제됨
		
		$tr.find('input').prop('disabled', false);
		$tr.find('select').prop('disabled', false);
		$tr.find('input[name=faqCateName]').focus();
		
		$tr.find('.category-modify-btn').hide();
		$tr.find('.category-modify-btnOk').show();		
	});

	// 카테고리 수정 완료
	$('#faqCategoryDialogModal').on('click', '.btnCategoryUpdateOk', function(){
		const $tr = $(this).closest('tr');
		
		let faqCateNum = $tr.find('input[name=faqCateNum]').val();
		let faqCateName = $tr.find('input[name=faqCateName]').val().trim();
		let enabled = $tr.find('select[name=enabled]').val();
		let orderNo = $tr.find('input[name=orderNo]').val();
		
		if(! faqCateName) {
			$tr.find('input[name=faqCateName]').focus();
			return false;
		}
		
		if(! /^[0-9]+$/.test(orderNo)) {
			$tr.find('input[name=orderNo]').focus();
			return false;
		}
		
		let url = '${pageContext.request.contextPath}/admin/faq/updateCategory';
		let formData = {faqCateNum:faqCateNum, faqCateName:faqCateName , enabled:enabled, orderNo:orderNo};
		const fn = function(data){
			let state = data.state;
			if(state === 'false') {
				alert('카테고리 수정이 불가능합니다');
				return false;
			}
			
			$cloneTr = null;
			
			listAllCategory();
		};
		
		ajaxFun(url, 'post', formData, 'json', fn);
	});

	// 카테고리 수정 취소
	$('#faqCategoryDialogModal').on('click', '.btnCategoryUpdateCancel', function(){
		const $tr = $(this).closest('tr');

		if( $cloneTr ) {
			$tr.replaceWith($cloneTr);
		}
		
		$cloneTr = null;
	});
});

// 카테고리 삭제
$(function(){
	$('#faqCategoryDialogModal').on('click', '.btnCategoryDeleteOk', function(){
		if(! confirm('카테고리를 삭제하시겠습니까 ? ')) {
			return false;
		}
		
		const $tr = $(this).closest('tr');
		let faqCateNum = $tr.find('input[name=faqCateNum]').val();
		
		let url = '${pageContext.request.contextPath}/admin/faq/deleteCategory';
		const fn = function(data) {
			listAllCategory();
		};
		
		ajaxFun(url, 'post', {faqCateNum:faqCateNum}, 'json', fn);
	});
});

$(function(){
	// 카테고리 대화상자 객체
	const myModalEl = document.getElementById('faqCategoryDialogModal');
	
	myModalEl.addEventListener('show.bs.modal', function(){
		// 모달 대화상자가 보일때
	});

	myModalEl.addEventListener('hidden.bs.modal', function(){
		// 모달 대화상자가 닫할때
		location.href = '${pageContext.request.contextPath}/admin/faq/main';
	});
});

</script>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<jsp:include page="/WEB-INF/views/layout/footerimported.jsp"/>
</body>
</html>