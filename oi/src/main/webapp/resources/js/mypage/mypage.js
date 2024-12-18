$(document).ready(function () {
  // 버튼 클릭 이벤트
  $("#toggleButton").click(function () {
    // 사이드바를 접었다 펼치기
    $(".my-history-nav").toggleClass("collapsed");

    // 버튼에 collapsed 클래스 추가/제거
    $(this).toggleClass("collapsed");

    // 화살표 방향 변경
    if ($(this).hasClass("collapsed")) {
      $(this).html("&gt;"); // 접힌 상태에서 화살표 방향 (>)
    } else {
      $(this).html("&lt;"); // 펼쳐진 상태에서 화살표 방향 (<)
    }
  });
});