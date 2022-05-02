<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.BookmarkDAO"%>
<%@ page import="model.User"%>
<%@ page import="model.MarketBean"%>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<jsp:include page="header.jsp" />
<%
/* 마이페이지 접속시 세션의 아이디를 조회하여 비 로그인 상태일때 login.jsp 페이지로 돌려보냄 */
String id = (String) session.getAttribute("userID");
if (id == null) {
	response.sendRedirect("login.jsp");
}
BookmarkDAO bdao = new BookmarkDAO();
List<MarketBean> bookmark = bdao.showBookmark(id);
%>

<h2 style="margin-left: 100px; margin-top: 20px; padding: 10px">즐겨찾기</h2>
<div class="bmContainer">
	<%
	for (MarketBean b : bookmark) {
	%>
	<div class="bmCard">
		<h5><a href="<%=request.getContextPath()%>/View?action=viewMarket&mno=<%=b.getMno()%>"><%=b.getMname()%></a></h5>
		<hr>
		<img src="<%=bdao.getMarketImg(b.getMno())%>" class="imgBox"> <span>주소 : <%=b.getMadd()%></span>
		<hr>
		<button type="button" class="btn btn-sm btn-primary btn-bm-delete" data-bs-toggle="modal" data-bs-target="#bmDeleteModal" data-mno="<%=b.getMno()%>">즐겨찾기 삭제</button>
	</div>
	<%
	}
	%>
</div>


<!-- 즐겨찾기 삭제 모달 -->
<div class="modal fade" id="bmDeleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">즐겨찾기 삭제</h5>
			</div>
			<div class="modal-body">
				<form id="bmDeleteForm">
					<input type="hidden" name="cmd" value="delete">
					<input type="hidden" name="userId" value="<%=(String) session.getAttribute("userID")%>">
				 	<input type="hidden" name="mno" value="">
				</form>
				<span>즐겨찾기를 삭제하시겠습니까?</span>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary bmDeleteBtn">삭제</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<script>
	let imgSrc = document.querySelectorAll(".imgBox")
	for (let i = 0; i < imgSrc.length; i++) {
		if (imgSrc[i].src == 'http://localhost:8090/TranditionMarket/null') {
			imgSrc[i].src = "https://pds.joongang.co.kr//news/component/htmlphoto_mmdata/201807/10/ba4fa995-5ce9-4e2f-a238-13c8bdd22d21.jpg";
		}
	};
	$('.btn-bm-delete').on('click', function() {
		$('#bmDeleteForm').find('input[name=mno]').val($(this).data('mno'));
	});
	$('.bmDeleteBtn').on('click', function(e){
		e.preventDefault();
		e.stopPropagation();
		$('.btn-action').prop('disabled', true);
		$.ajax({
			type: "POST",
			url: "http://localhost:8090/TranditionMarket/Bookmark",
			data: $('#bmDeleteForm').serialize(),
			dataType: 'text'
		})
		.done(function(data){
			if(data.status){
				$('#bmDeleteModal').modal('hide');
				location.reload();
			}
			location.reload();
		})
		.fail(function(jqXHR, textStatus){
			console.log(textStatus)
		})
	})
</script>
<jsp:include page="footer.jsp" />