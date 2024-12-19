$(document).ready(function () {
    $("form").on("submit", function (e) {
        const startTime = new Date($("#exerciseStart").val());
        const endTime = new Date($("#exerciseEnd").val());
        const alertContainer = $("#alertContainer");

        // 경고창 초기화
        alertContainer.addClass("d-none").text("");

        if (startTime > endTime) {
            alertContainer.text("운동 시작 시간이 종료 시간보다 늦을 수 없습니다.");
            alertContainer.removeClass("d-none");

            // 경고창으로 스크롤 이동
            $("html, body").animate({ scrollTop: alertContainer.offset().top - 20 }, "slow");
            e.preventDefault(); // 폼 제출 방지
        }
    });
});