document.addEventListener("DOMContentLoaded", function() {
  const startOfWeek = new Date('${startOfWeek}');
  const endOfWeek = new Date('${endOfWeek}');
  
  // 주의 첫 날(일요일)부터 토요일까지의 날짜를 생성
  let currentDate = new Date(startOfWeek);
  const calendarBody = document.getElementById("calendarBody");
  
  let row = document.createElement("tr");
  
  // 일요일부터 토요일까지 반복
  for (let i = 0; i < 7; i++) {
    const cell = document.createElement("td");
    cell.classList.add("day");
    cell.textContent = currentDate.getDate();
    calendarBody.appendChild(row);
    
    // 다음 날짜로 이동
    currentDate.setDate(currentDate.getDate() + 1);
  }
  
  // 현재 주의 날짜 출력
  document.querySelector("label").textContent = `${startOfWeek.toLocaleDateString()} ~ ${endOfWeek.toLocaleDateString()}`;
});

function moveWeek(offset) {
  // 서버에서 새로운 주의 시작일과 끝일을 받아오는 로직 필요
  // fetch API를 사용하여 서버로부터 새로운 날짜 정보를 가져와 화면 갱신
  fetch(`/updateWeek?offset=${offset}`)
    .then(response => response.json())
    .then(data => {
      // 새로운 날짜로 달력 갱신
      updateCalendar(data.startOfWeek, data.endOfWeek);
    });
}

function updateCalendar(startOfWeek, endOfWeek) {
  // 새로운 주의 날짜로 달력 업데이트
  const calendarBody = document.getElementById("calendarBody");
  calendarBody.innerHTML = '';  // 기존 내용 제거
  
  let currentDate = new Date(startOfWeek);
  let row = document.createElement("tr");
  
  // 일요일부터 토요일까지 날짜 추가
  for (let i = 0; i < 7; i++) {
    const cell = document.createElement("td");
    cell.classList.add("day");
    cell.textContent = currentDate.getDate();
    row.appendChild(cell);
    
    // 날짜 증가
    currentDate.setDate(currentDate.getDate() + 1);
  }
  
  calendarBody.appendChild(row);
  
  // 주의 시작일과 끝일을 화면에 출력
  document.querySelector("label").textContent = `${startOfWeek} ~ ${endOfWeek}`;
}

// 아침, 점심, 저녁 추가 기능
function addMealEntry(mealType) {
  let mealTime = prompt("식사 시간을 입력하세요 (예: 7:00): ");
  let mealMenu = prompt("식단을 입력하세요: ");
  
  if (mealTime && mealMenu) {
    let table = document.getElementById(`${mealType}Table`);
    let row = table.insertRow();
    row.insertCell(0).textContent = mealTime;
    row.insertCell(1).textContent = mealMenu;
  }
}

// 메모 저장 및 표시
function addMeal() {
  let memoText = document.getElementById("memoInput").value;
  if (memoText) {
    alert("메모가 저장되었습니다: " + memoText);
  }
}
