var cp = "/oi";

function getComments(wnum) {
	let url = cp + "/completeworkout/modalbody";

	$.ajax({
		type: 'get',
		url: url,
		data: { wnum: wnum },
		dataType: 'text',
		success: function(data) {
			$('.modal-body').html(data);
		},
		beforeSend: function(jqXHR) {
			$('.modal-body').html("");
			jqXHR.setRequestHeader('AJAX', true);
		},
		error: function(e) {
			console.log(e.responseText);
		}
	});
}

$(function() {
	// 댓글 입력 란 
	$('.modal-body').on('click', '.btn-answer', function() {

		let $content = $(this).prev('.contents');
		let content = $(this).prev('.contents').val();
		let wnum = $(this).next('.getwnum').val();

		if (!content) {
			$(this).prev('.contents').focus();
			return false;
		}

		let url = cp + "/completeworkout/insertcomment";

		$.ajax({
			type: 'get',
			url: url,
			data: { content: content, wnum: wnum },
			dataType: 'json',
			success: function(data) {
				getComments(data.wnum);
			},
			beforeSend: function(jqXHR) {
				$content.val("");
				jqXHR.setRequestHeader('AJAX', true);
			},
			error: function(e) {
				console.log(e.responseText);
			}
		});
	});
});
// 모달창 가져오기
$(function() {
	$('.profile-posts').on('click', '.articles', function() {
		let url = cp + "/completeworkout/personalarticle";
		let wnum = $(this).attr('data-wnum');
		console.log(wnum);
		$.ajax({
			type: 'get',
			url: url,
			data: { wnum: wnum },
			dataType: 'text',
			success: function(data) {
				$(".modal-body").html(data);
			},
			beforeSend: function(jqXHR) {
				jqXHR.setRequestHeader('AJAX', true);
			},
			error: function(e) {
				console.log(e.responseText);
			}
		});
	});
});

// 컨텐츠 불러오기 
function loadingcontent(data) {

	let dataCount = data.dataCount;
	let page = data.nowpage;
	let totalpage = data.totalpage;

	$('.profile-posts').attr('data-page', page);
	$('.profile-posts').attr('data-total', totalpage);
	$('#dataCount').html(dataCount);

	if (page < totalpage) {
		$('.seemore2').show();
	}
	if (page == totalpage) {
		$('.seemore2').hide();
	}
	let text;
	for (item of data.list) {
		let filename = item.file.saveFileName;
		text = '<div class="col-4 articles" data-bs-toggle="modal" data-bs-target="#modal" data-wnum="' + item.wnum + '">'
		text += '<img class="post" alt="사진" src="' + cp + '/uploads/photo/' + filename + '">'
		text += '</div>';
		$('.profile-posts').append(text);
	}
}
function goajax(page) {
	let url = cp + "/completeworkout/personal";

	$.ajax({
		type: "get",
		url: url,
		data: { page: page },
		dataType: 'json',
		success: function(data) {
			loadingcontent(data);
		},
		beforeSend: function(jqXHR) {
			jqXHR.setRequestHeader('AJAX', true);
		},
		error: function(e) {
			console.log(e.responseText);
		}
	});
}
$(function() {
	goajax(1);
	$('.seemore2').click(function() {
		let page = $('.profile-posts').attr('data-page');
		let total = $('.profile-posts').attr('data-total');
		
		if(parseInt(page) < parseInt(total)){
			page++;
			goajax(page);
		}
	});
});
