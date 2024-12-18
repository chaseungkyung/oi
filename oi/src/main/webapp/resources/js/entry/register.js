var cp = "/oi";

$(function() {
	// 이메일 도메인 선택시 change 이벤트 등록 해땅
	const domain = document.querySelector('#emailDomain');
	$(domain).change(function() {
		let obj = $(this);
		let value = obj.val();
		if ($(obj).val() != 'self') {
			$(obj).prev().val(value);
			$(obj).prev().prop('readonly', true);
		} else {
			$(obj).prev().val("");
			$(obj).prev().prop('readonly', false);
		}
	});

	// 아이디 중복검사
	const idbtn = document.querySelector('.checkbtn');
	$(idbtn).each( function(index,item){
			let text = $(this).prev().val();
			let url = cp + '/access/idcheck';

			if (!idtext) {
				$(this).prev().focus();
				return false;
			}

			$.ajax({
				type: 'get',
				url: url,
				data: { id: text },
				dataType: 'json',
				success: function(data) {
					$('#idFeedback').html(data.state);
					if (data.result == 'true') {
						$('#idFeedback').attr('data-valid', "true");
						document.getElementById('idFeedback').style.color = "blue";
						$('#idFeedback').show();
					} else {
						$('#idFeedback').attr('data-valid', "false");
						$('#idFeedback').css('color', 'red').show();
					}
				},
				beforeSend: function(jqXHR) {
					jqXHR.setRequestHeader('AJAX', true);
				},
				error: function(e) {
					console.log(e.responseText);
				}
			});

		})
	
	/*
	click(function() {
		let idtext = $(this).prev().val();
		let url = cp + '/access/idcheck';

		if (!idtext) {
			$(this).prev().focus();
			return false;
		}

		$.ajax({
			type: 'get',
			url: url,
			data: { id: idtext },
			dataType: 'json',
			success: function(data) {
				$('#idFeedback').html(data.state);
				if (data.result == 'true') {
					$('#idFeedback').attr('data-valid', "true");
					document.getElementById('idFeedback').style.color = "blue";
					$('#idFeedback').show();
				} else {
					$('#idFeedback').attr('data-valid', "false");
					$('#idFeedback').css('color', 'red').show();
				}
			},
			beforeSend: function(jqXHR) {
				jqXHR.setRequestHeader('AJAX', true);
			},
			error: function(e) {
				console.log(e.responseText);
			}
		});

	});
	*/
});

function submitOk(){
	let $form = document.signupForm;
	let valid = document.getElementById('idFeedback').getAttribute('data-valid');
	
	if(! $form.username.value.trim()){
		$form.username.focus();
		return;
	}
	if(! $form.userid.value.trim()){
		$form.userid.focus();
		return;
	}
	if(valid == 'false'){
		alert('아이디 중복검사를 진행해주세요');
		return;
	}
	
	if(! $form.password.value){
		$form.password.focus();
		return;
	}

	if($form.password.value !== $form.confirmpassword.value){
		$('#confirm-password-feedback').show();
		return;
	}else {
		$('#confirm-password-feedback').hide();
	}

		if(! $form.dob.value){
		$form.dob.focus();
		return;
	}
	if(! $form.phone2.value.trim()){
		$form.phone2.focus();
		return;
	}
	
	if(! $form.phone3.value.trim()){
		$form.phone3.focus();
		return;
	}
	
	if(! $form.email1.value.trim()){
		$form.email1.focus();
		return;
	}
	
	if(! $form.email2.value.trim()){
		$form.email2.focus();
		return;
	}
	if(! $form.zipcode.value.trim()){
		$form.findZipBtn.focus();
		return;
	}
	if(! $form.address1.value.trim()){
		$form.findZipBtn.focus();
		return;
	}
	if(! $form.address2.value.trim()){
		$form.address2.focus();
		return;
	}
	
	$form.action = cp+"/access/register";
	$form.submit();
}