//$(document).ready(function() {  })
// $(function() { }) 위, 아래 동일함 준비되면 자동으로 시작
$(function () {
  $('a.deleteConfirm').click(function () {
    if (!confirm('삭제하겠습니까?')) return false; // 취소시 삭제안됨
  });

  // page conenet ck에디터 추가
  if ($('#content').length) {
    // JQuery에서는 태그선택시 무조건 true이기 때문에 length(없으면 0, 있으면 1)를 사용
    ClassicEditor.create(document.querySelector('#content')).catch((error) => {
      console.error(error);
    });
  }
  // product description ck에디터 추가
  if ($('#description').length) {
    // JQuery에서는 태그선택시 무조건 true이기 때문에 length(없으면 0, 있으면 1)를 사용
    ClassicEditor.create(document.querySelector('#description')).catch((error) => {
      console.error(error);
    });
  }

  $('a.nav-link').on('mouseenter', function () {
    $('a.nav-link').closest('li').toggleClass('Choice');
  });

  const asd = document.querySelector('.c');
  asd.addEventListener('click', function (e) {
    asd.toggleClass('.hide');
  });
});
