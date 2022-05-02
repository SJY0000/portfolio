$('#openEditModal').on('click', function() {
	$('#editBox').modal('show');
});
$('#openExitModal').on('click', function() {
	$('#exitBox').modal('show');
});
$('#btnEditProfile').on('click', function() {
	$('#modalEditProfile').modal('show');
});
$('#btnDeleteProfile').on('click', function() {
	$('#modalDeleteProfile').modal('show');
});

$('#closeModalBtn').on('click', function() {
	$('#editBox').modal('hide');
});
$('#closeControlBox').on('click', function() {
	$('#controlBox').modal('hide');
});
$('#closeKickBox').on('click', function() {
	$('#kickBox').modal('hide');
});
$('#closeModalBtn2').on('click', function() {
	$('#exitBox').modal('hide');
});
$('#closeModalBtn3').on('click', function() {
	$('#deleteBm').modal('hide');
});
$('#closeModalBtn4').on('click', function() {
	$('#modalEditProfile').modal('hide');
});
$('#closeModalBtn5').on('click', function() {
	$('#modalDeleteProfile').modal('hide');
});


// 관리자용 삭제 모달 ajax
$('.kickBtn').on('click', function() {
	$('#fm-kick').find('input[name=userId]').val($(this).data('id'));
	$('#kickBox').modal('show');
});
$('#fm-kick').submit(function(e) {
	e.preventDefault();
	e.stopPropagation();
	$('.btn-action').prop('disabled', true);
	$.ajax({
		type: "POST",
		url: "http://localhost:8090/TranditionMarket/admin",
		data: $('#fm-kick').serialize(),
		dataType: 'text'
	})
		.done(function(data) {
			if (data.status) {
				$('#kickBox').modal('hide');
				location.reload();
			}
			location.reload();
		})
		.fail(function(jqXHR, textStatus) {
			console.log(textStatus);
		});
})

// 관리자용 프로필 이미지 삭제기능(url 공백으로)
$('.delete-imgBtn').on('click', function() {
	$('#userImgBox').val("");
});

// 회원 관리 모달창에 데이터 전달
$('.adminBtn').on('click', function() {
	let count = 0;
	count = $(this).data('warning');
	$('#controlBox').modal('show'); // 모달창 표시
	$('#fm-control').find('input[name=userNick]').val($(this).data('nick'));
	$('#fm-control').find('input[name=userId]').val($(this).data('id'));
	$('#fm-control').find('input[name=userImg]').val($(this).data('img'));
	$('#fm-control').find('input[name=warning]').val(count);
	$('#fm-control').find('span').html(count);
	// 누적경고 버튼
	$('.warningBtn').on('click', function() {
		if (count < 3) {
			count++;
			$('#warningCount').html(count);
			$('#fm-control').find('input[name=warning]').val(count);
		} else {
			alert("이미 누적된 경고가 최대 입니다!")
		}
	})
});

// 회원 관리 모달 ajax
$('#fm-control').submit(function(e) {
	e.preventDefault();
	e.stopPropagation();
	$('.btn-action').prop('disabled', true);
	$.ajax({
		type: "POST",
		url: "http://localhost:8090/TranditionMarket/admin",
		data: $('#fm-control').serialize(),
		dataType: 'text'
	})
		.done(function(data) {
			if (data.status) {
				$('#controlBox').modal('hide');
				location.reload();
			}
			location.reload();
		})
		.fail(function(jqXHR, textStatus) {
			console.log(textStatus);
		});
});

$('.btn-deleteBm').on('click', function() {
	$('#fm-delete-bm').find('input[name=mname]').val($(this).data('mname'));
	$('#deleteBm').modal('show');
});
$('#delete-bm-btn').on('click', function(e) {
	e.preventDefault();
	e.stopPropagation();
	$('.btn-action').prop('disabled', true);
	$.ajax({
		type: "POST",
		url: "http://localhost:8090/TranditionMarket/Mypage",
		data: $('#fm-delete-bm').serialize(),
		dataType: 'text'
	})
		.done(function(data) {
			if (data.status) {
				$('#deleteBm').modal('hide');
				location.reload();
			}
			location.reload();
		})
		.fail(function(jqXHR, textStatus) {
			console.log(textStatus)
		})
})

function nickCheck() {
	let userNick = $('#userNick').val().trim();
	let cmd = "checkNick";
	$.ajax({
		type: "POST",
		url: "http://localhost:8090/TranditionMarket/Mypage",
		data: {
			userNick: userNick,
			cmd: cmd
		},
		dataType: "text"
	})
		.done(function(result) {
			if (result == 1) {
				$('#checkedNickName').text("사용 불가능한 닉네임 입니다.")
				$('#checkedNickName').css('color', 'red')
				$('#txtNick').text("닉네임 중복확인을 해주세요");
				$('#txtNick').css('color', 'orange');
			} else {
				$('#checkedNickName').text("사용 가능한 닉네임 입니다.")
				$('#checkedNickName').css('color', 'green')
				$('#txtNick').text("");
				allowedNick();
			}
		})
		.fail(function(jqXHR, textStatus) {
			console.log(textStatus)
		})

}

function allowedNick() {
	let btnEdit = document.getElementById('btn-edit');
	if (btnEdit.getAttribute('disabled') != null) {

	}
	btnEdit.removeAttribute('disabled')
}

function changeNick() {
	let btnEdit = document.getElementById('btn-edit');
	$('#txtNick').text("닉네임 중복확인을 해주세요");
	$('#txtNick').css('color', 'orange');
	btnEdit.setAttribute('disabled', '');
}


