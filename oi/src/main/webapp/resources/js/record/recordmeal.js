function mealInsertOk() {
    const f = document.mealForm;
	    const mealTime = f.mealTime.value.trim();
    if (!mealTime) {
        alert("식사 시간을 선택하세요");
        return;
    }
	    const mealName = f.mealName.value.trim();
    if (!mealName) {
        alert("식단 이름을 입력해주세요");
        return;
    }
	const mealDate = f.mealDate.value.trim();
    const mealCapacity = f.mealCapacity.value.trim();
    const mealKcal = f.mealKcal.value.trim();

    if (!mealDate ||  !mealCapacity || !mealKcal) {
        alert("모든 필드를 입력해주세요.");
        return;
    }
	    
    console.log("Meal Time: ", mealTime);
    console.log("Meal Name: ", mealName);
    console.log("Meal Date: ", mealDate);
    console.log("Meal Capacity: ", mealCapacity);
    console.log("Meal Kcal: ", mealKcal);
	
	let url = '/oi/recordmeal/mealinsert';
	
	// Ajax로 데이터 전송
    $.ajax({
        url: url,
        type: 'POST',
        data: {
            dietFoodTime: mealTime,
            dietFoodName: mealName,
            dietFoodDate: mealDate,
            capacity: mealCapacity,
            kcal: mealKcal
        },
		dataType: 'json',
        success: function(response) {
            if (response.state === "true") {
                alert("식단이 등록되었습니다.");
                closeModal(); 
				$.ajax({
				    url: '/oi/recordmeal/mealList',
				    type: 'GET',
				    dataType: 'html',
				    success: function(data) {
				        $('#calendarBody').html($(data).find('#calendarBody').html());
				        // 'calendarBody'는 출력할 테이블 ID에 맞게 수정
				    },
				    error: function(err) {
				        console.error("리스트 갱신 실패: ", err);
				    }
				});
            } else {
                alert("저장에 실패했습니다.");
            }
        },
        error: function(a) {
            console.log(a.responseText);
        }
    });
}




// 모달창
let currentMealType = ""; // 현재 추가할 식사 타입

function openModal(mealType) {
	currentMealType = mealType;
	document.getElementById('mealModal').style.display = 'flex';
}


function closeModal() {
	document.getElementById('mealModal').style.display = 'none';
	document.getElementById('mealTime').value = "";
	document.getElementById('mealName').value = "";
}

function ajaxFun(url, method, formData, dataType, fn, file = false) {
	const settings = {
			type: method, 
			data: formData,
			dataType:dataType,
			success:function(data) {
				fn(data);
			},
			beforeSend: function(jqXHR) {
			},
			complete: function () {
			},
			error: function(jqXHR) {
				console.log(jqXHR.responseText);
			}
	};
	
	if(file) {
		settings.processData = false;  // file 전송시 필수. 서버로전송할 데이터를 쿼리문자열로 변환여부
		settings.contentType = false;  // file 전송시 필수. 서버에전송할 데이터의 Content-Type. 기본:application/x-www-urlencoded
	}
	
	$.ajax(url, settings);
}

$(function(){
	$('.btn-search').click(function(){
	let kwd = $('#keyword').val().trim();
		if( ! kwd) {
		return false;
		}
	searchMeal(kwd);
	});
		
	function searchMeal(food_Name) {		
		let spec = 'http://apis.data.go.kr/1390802/AgriFood/MzenFoodCode/getKoreanFoodList';
		let key = '7aZjzRKyMraXhRyx91Pdw%2Fgec2y9eFWM8gPnrUVz5jg706DLS3hAESkz4xFMXyLLA%2BSA%2FM0aD1yQyVDsQ0ZbkQ%3D%3D';
		let Page_No = 1;
		let Page_Size = 5;
		
		let qs = "serviceKey="+ encodeURIComponent(key);
		qs += "&Page_No="+ Page_No;
		qs += "&Page_Size="+ Page_Size;
//		qs += "&keyword=" + encodeURIComponent(keyword);
		qs += "&food_Name=" + food_Name;
		
		const fn = function(data) {
			$('.list-header-right').empty();
			$('.list-content').empty();
			
			printXML(data);
		};
		
		ajaxFun(spec, 'GET', qs, 'xml', fn);	
	}
	
	function printXML(data) {
		console.log(data);

	}
});
