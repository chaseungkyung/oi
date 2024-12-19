
var cp = "/oi";
var $sensor = document.querySelector('#sensor');
var $contentcover = document.querySelector('#wtdmaincontent');


function intotheForm() {
	location.href = cp + "/completeworkout/insertwtd";
}


// AJAX 통신 성공했을때 콜백함수 
const successFn = function(data) {

	let $sensor = document.querySelector('#sensor');
	let $contentcover = document.querySelector('#wtdmaincontent');
	
	document.querySelector('#wtdmaincontent').insertAdjacentHTML('beforeend', data);

	total = $('#wtdmaincontent').find('input.total_page:last-child').attr('total_page');
	page = $('#wtdmaincontent').find('input.page:last-child').attr('page');

	if (page === total) {
		$sensor.style.display = 'none';
	} else if (page < total) {
		io.observe($sensor);
	}

	if (total === 0) {
		$contentcover.innerHTML = '';
	}


	$('#wtdmaincontent').attr('data-page', page);
	$('#wtdmaincontent').attr('data-total', total);
	$('div#sensor').attr('data-loading', 'false');
}

//컨텐츠 불러오기 
function loading($page) {
	let url = cp + "/completeworkout/list";
	let $sensor = document.querySelector('#sensor');

	$.ajax({
		type: 'get',
		url: url,
		data: {
			page: $page
		},
		success: function(data) {
			successFn(data);
		},
		beforeSend: function(jqXHR) {
			$sensor.setAttribute('data-loading', true);
			jqXHR.setRequestHeader('AJAX', true);
		},
		error: function(e) {
			console.log(e.responseText);
		}
	});
}


// 감지 콜백 함수 
const callback = (entries, io) => {

	let $sensor = document.querySelector('#sensor');
	let $contentcover = document.querySelector('#wtdmaincontent');

	entries.forEach((entry) => {
		if (entry.isIntersecting) {
			let load = $sensor.getAttribute('data-loading');
			if (load !== 'false') {
				return;
			}
			io.unobserve(entry.target);

			let page = parseInt($contentcover.getAttribute('data-page'));
			let total = parseInt($contentcover.getAttribute('data-total'));

			if (page === 0 || page < total) {
				page++;
				loading(page);
			}
		}
	});
};


// 처음 로딩 
$(function() {
	loading(1);

	// 감지 등록 

	var io = new IntersectionObserver(callback);
	io.observe(document.querySelector("#sensor"));
});


$(function() {
	$('#wtdmaincontent').on('click', '.emotion', function() {
		if ($(this).hasClass('like')) {


			const num = $(this).closest('table.bodytable').attr('data-num');
			const $obj = $(this).find('i');
			let mode = $(this).find('i').hasClass('bi-heart-fill') ? 'delete' : 'insert';
			let url = cp + '/completeworkout/insertlike';
			let query = { num: num, mode: mode };

			const fx = function(data) {
				if (mode == 'insert') {
					$($obj).removeClass('bi-heart');
					$($obj).addClass('bi-heart-fill');
				} else {
					$($obj).removeClass('bi-heart-fill');
					$($obj).addClass('bi-heart');
				}
				$($obj).parent().next('span').html(data.count);
			};

			$.ajax({
				type: 'get',
				url: url,
				data: query,
				dataType: 'json',
				success: function(data) {
					fx(data);
				},
				beforeSend: function(jqXHR) {
					jqXHR.setRequestHeader('AJAX', true);
				},
				error: function(e) {
					console.log(e.responseText);
				}
			});
		}
	});
});

// 게시글 및 댓글 답글 띄우기 
$(function() {
	$('#wtdmaincontent').on('click', '.btnmodalshow', function() {
		let url = cp + "/completeworkout/modalbody";
		let wnum = $(this).attr('data-article');

		const query = { wnum: wnum };

		$.ajax({
			type: 'get',
			url: url,
			data: query,
			dataType: 'text',
			success: function(data) {
				$('.modal-body').html(data);
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
