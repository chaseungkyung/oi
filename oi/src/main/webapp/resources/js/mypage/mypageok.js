$(document).ready(function () {
  // 비밀번호 검증 (최소 8자, 대소문자, 숫자, 특수문자 포함)
  function validatePassword() {
    const password = $('#userPwd').val().trim();
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordPattern.test(password)) {
      showError('#userPwd', '비밀번호는 대소문자, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.');
      return false;
    } else {
      hideError('#userPwd');
      return true;
    }
  }

  // 닉네임 검증 (2~10자 한글, 영문)
  function validateNickname() {
    const nickname = $('#usernickName').val().trim();
    const nicknamePattern = /^[a-zA-Z가-힣]{2,10}$/;
    if (!nicknamePattern.test(nickname)) {
      showError('#usernickName', '닉네임은 한글 또는 영문 2~10자여야 합니다.');
      return false;
    } else {
      hideError('#usernickName');
      return true;
    }
  }

  // 우편번호, 주소 검증
  function validateAddressFields() {
    const zipCode = $('#zipCode').val().trim();
    const address = $('#address').val().trim();
    let isValid = true;

    if (zipCode === '') {
      showError('#zipCode', '우편번호를 검색해주세요.');
      isValid = false;
    } else {
      hideError('#zipCode');
    }

    if (address === '') {
      showError('#address', '주소를 입력해주세요. (우편번호 검색 버튼 사용)');
      isValid = false;
    } else {
      hideError('#address');
    }

    return isValid;
  }

  // 신체 정보 검증
  // 키(3자리), 몸무게(2~3자리), 체지방률(2~3자리), 근육량(2~3자리)
  function validateHeight() {
    const height = $('#height').val().trim();
    const heightPattern = /^\d{3}$/; 
    if (!heightPattern.test(height)) {
      showError('#height', '키는 3자리 숫자여야 합니다. 예: 150');
      return false;
    } else {
      hideError('#height');
      return true;
    }
  }

  function validateWeight() {
    const weight = $('#weight').val().trim();
    const weightPattern = /^\d{2,3}$/; 
    if (!weightPattern.test(weight)) {
      showError('#weight', '몸무게는 2~3자리 숫자여야 합니다. 예: 50, 100');
      return false;
    } else {
      hideError('#weight');
      return true;
    }
  }

  function validateBmi() {
    const bmi = $('#bmi').val().trim();
    const bmiPattern = /^\d{2,3}$/; 
    if (!bmiPattern.test(bmi)) {
      showError('#bmi', '체지방률은 2~3자리 숫자여야 합니다. 예: 15, 100');
      return false;
    } else {
      hideError('#bmi');
      return true;
    }
  }

  function validateMuscle() {
    const muscle = $('#muscle').val().trim();
    const musclePattern = /^\d{2,3}$/;
    if (!musclePattern.test(muscle)) {
      showError('#muscle', '근육량은 2~3자리 숫자여야 합니다. 예: 20, 100');
      return false;
    } else {
      hideError('#muscle');
      return true;
    }
  }

  // 공통 에러 메시지 표시 함수
  function showError(selector, message) {
    $(selector).addClass('is-invalid');
    $(selector).next('.invalid-feedback').remove();
    $(selector).after(`<div class="invalid-feedback">${message}</div>`);
  }

  // 에러 메시지 숨기기 함수
  function hideError(selector) {
    $(selector).removeClass('is-invalid');
    $(selector).next('.invalid-feedback').remove();
  }

  // 페이지 로드 시 초기 검증
  validatePassword();
  validateNickname();
  validateAddressFields();

  // 신체정보 초기 검증
  validateHeight();
  validateWeight();
  validateBmi();
  validateMuscle();

  // 입력 이벤트
  $('#userPwd').on('input', validatePassword);
  $('#usernickName').on('input', validateNickname);
  $('#zipCode, #address').on('input', validateAddressFields);

  $('#height').on('input', validateHeight);
  $('#weight').on('input', validateWeight);
  $('#bmi').on('input', validateBmi);
  $('#muscle').on('input', validateMuscle);

  // 개인정보 폼 제출 검증
  $('#personalRegisterButton').on('click', function (event) {
    const isPasswordValid = validatePassword();
    const isNicknameValid = validateNickname();
    const isAddressValid = validateAddressFields();
	const isHeightValid = validateHeight();
	const isWeightValid = validateWeight();
	const isBmiValid = validateBmi();
	const isMuscleValid = validateMuscle();

    if (!isPasswordValid || !isNicknameValid || !isAddressValid || !isHeightValid || !isWeightValid || !isBmiValid || !isMuscleValid) {
      event.preventDefault();
      alert('입력값을 확인해 주세요.');
      scrollToFirstError();
    } else {
      alert('수정이 완료되었습니다!');
    }
  });

  function scrollToFirstError() {
    const $firstInvalidField = $('.is-invalid').first();
    if ($firstInvalidField.length > 0) {
      $('html, body').animate({
        scrollTop: $firstInvalidField.offset().top - 20
      }, 500);
      $firstInvalidField.focus();
    }
  }
});