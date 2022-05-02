<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dao.MypageDAO"%>
<%@ page import="dao.AdminDAO"%>
<%@ page import="model.User"%>
<%@ page import="java.util.*"%>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="js/mypage.js"></script>
<jsp:include page="header.jsp" />
<%
// session에서 아이디를 받아와 유저 정보 저장 후 로그인 중이 아니거나 관리자 계정이 아니면 메인 페이지로 돌려보냄
String id = (String) session.getAttribute("userID");
MypageDAO mdao = new MypageDAO();
User user = mdao.showData(id);
if (id == null || user.getAdmin() == 0) {
	response.sendRedirect("main.jsp");
}
AdminDAO adao = new AdminDAO();
%>
<h2>관리자 페이지</h2>
<table style="border: 1px solid black; text-align: center; padding: 5px;">
	<tr>
		<th>유저 아이디</th>
		<th>유저 이름</th>
		<th>유저 닉네임</th>
		<th>유저 이메일</th>
		<th>유저 주소</th>
		<th>유저 누적 경고</th>
		<th>관 리</th>
	</tr>
	<c:forEach var="users" items="${users}">
		<tr>
			<td>
				<c:out value="${users.userId}" />
			</td>
			<td>
				<c:out value="${users.userName}" />
			</td>
			<td>
				<c:out value="${users.userNick}" />
			</td>
			<td>
				<c:out value="${users.userEmail}" />
			</td>
			<td>
				<c:out value="${users.userAddress}" />
			</td>
			<td>
				<c:out value="${users.warning}" />
			</td>
			<td>
				<button class="btn btn-primary adminBtn" data-id="<c:out value='${users.userId}'/>" data-nick="<c:out value='${users.userNick}'/>" data-warning="<c:out value='${users.warning}'/>" data-img="<c:out value='${users.userImg}'/>">관리</button>
				<button class="btn btn-danger kickBtn" data-id="<c:out value='${users.userId}' />">강퇴</button>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="modal fade" id="kickBox" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">회원 강퇴</h5>
			</div>
			<form id="fm-kick">
				<div class="modal-body">
					<!-- ajax 처리 필요 -->
					<input type="hidden" name="cmd" value="delete"> <input type="hidden" value="" name="userId"><span>강퇴시키시겠습니까</span>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">강퇴</button>
					<button type="button" class="btn btn-secondary" id="closeKickBox">취소</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="modal fade" id="controlBox" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="fm-control">
				<div class="modal-header">
					<h5 class="modal-title" id="staticBackdropLabel">회원 관리</h5>
					<div>
					<label for="warningCount">누적 경고</label>
					<span id="warningCount" style="color:red;"></span>
					<button type="button" class="btn btn-sm btn-danger warningBtn">경고주기</button>
					</div>
				</div>
				<div class="modal-body">
					<input type="hidden" name="cmd" value="edit">
					<input type="hidden" name="warning" value="">
					<input type="hidden" name="userId" value=""> 
					<label for=userNick class="form-label">사용자 닉네임</label> 
					<input type="text" class="form-control" name="userNick" id="userNick"> 
					<label for="userImg" class="form-label">사용자 프로필</label> 
					<input type="text" class="form-control" id="userImgBox" readonly value="" name="userImg">
					<button type="button" class="btn btn-sm btn-danger delete-imgBtn">이미지 삭제</button>
					<!-- 삭제버튼 클릭시 등록된 이미지 주소삭제 -->
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">수정</button>
					<button type="button" class="btn btn-secondary" id="closeControlBox">취소</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="js/mypage.js"></script>
<jsp:include page="footer.jsp" />