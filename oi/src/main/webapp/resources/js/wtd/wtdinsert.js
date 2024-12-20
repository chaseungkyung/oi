var cp = "/oi";
function sendInsert() {
	const form = document.insertForm;
	const text = document.getElementById('filedescribe');
	if (!form.content.value) {
		form.content.focus();
		return;
	}
	if (form.fileinput.files.length === 0) {
		text.innerText = "사진을 선택해주세요";
		return;
	}

	const files = form.fileinput.files;
	const maxsize = 2 * 1024 * 1024;

	for (el of files) {
		if (el.size > maxsize) {
			text.innerHTML = "용량은 2MB까지만 가능합니다";
			return;
		}
	}

	form.action = cp + "/completeworkout/insertwtd";
	form.submit();
};
function sendUpdate() {
	$form = document.insertForm;

	$form.action = cp + '/completeworkout/update';


	let images = document.querySelectorAll('.image');

	if (!$form.content.value) {
		$form.content.focus();
		return;
	}
	
	if (!$form.fileinput.value) {
		let  arr = [];
		images.forEach(function(item, index) {
			if ($(item).attr('data-valid') == 'false') {
				arr.push('false');
				return;
			}
		});
		
		if(arr.length == images.length){
			$form.fileinput.focus();
			return;
		}
	}

	let url = cp + "/completeworkout/deletefile";

	images.forEach(function(item, index) {
		let vaild = $(item).attr('data-valid')
		let filenum = $(item).attr('data-filenum');
		if (vaild == 'false') {
			$.ajax({
				url: url,
				type: 'get',
				data: { filenum: filenum },
				dataType: 'JSON',
				success: function(data) {

				},
				error: function(e) {
					console.log(e.responseText);
				}
			});
		}
	});

	$form.submit();
}
