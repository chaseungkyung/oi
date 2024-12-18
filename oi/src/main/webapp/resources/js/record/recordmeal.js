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
