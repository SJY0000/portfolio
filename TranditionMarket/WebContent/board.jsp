<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dao.MypageDAO"%>
<%@ page import="model.User"%>
	<jsp:include page="header.jsp" />

<div>
	<div class="position-relative col-8 mx-auto mt-4">
		<%
			if ((String) session.getAttribute("uno") == null) {
				session.setAttribute("uno","");
				session.setAttribute("nick","");
				session.setAttribute("admin","0");
			}
			String action = request.getParameter("action");
			String uno = (String) session.getAttribute("uno");
			String nick = (String) session.getAttribute("nick");
			String admin = (String) session.getAttribute("admin");
			String id = (String) session.getAttribute("userID");
			
			if (id != null) {
				MypageDAO mdao = new MypageDAO();
				User user = mdao.showData(id);
				uno = Integer.toString(user.getUno());
				nick = user.getUserNick();
				admin = Integer.toString(user.getAdmin());
				session.setAttribute("uno", uno);
				session.setAttribute("nick", nick);
				session.setAttribute("admin", admin);
			}

		if (action.equals("notice")) {
		%>
		<h1 class="mb-5 pb-5">공지사항</h1>
		<%
			} else {
		%>
		<h1 class="mb-5 pb-5">후기게시판</h1>
		<%
			}
		%>
		<table
			id = "boards"
			class="table table-bordered table-hover cell-border text-center col-8">
			<thead>
				<tr class="bg-secondary bg-opacity-10">
					<th class="d-none">게시판번호</th>
					<th class="d-none">타입</th>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>추천수</th>
					<th>조회수</th>
					<th>작성일</th>
				</tr>
			
			<tbody>
				<c:forEach var="board" items="${boardlist}">
					<tr data-bno="${board.bno}">		
						<td class="d-none"><c:out value="${board.bno}" />
						<td class="d-none"><c:out value="<%= action %>" />
						<td class="col-sm-1"><c:out value="${board.index}" /></td>
						<td class="col-sm-6"><c:out value="${board.title}" /></td>
						<td class="col-sm-1"><c:out value="${board.writer}" /></td>
						<td class="col-sm-1"><c:out value="${board.reco}" /></td>
						<td class="col-sm-1"><c:out value="${board.check}" /></td>
						<td class="col-sm-2"><c:out value="${board.date}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button
			class="position-absolute bottom-0 end-0 mb-5 mt-0 bg-secondary px-3 py-1"
			onclick="move()">글쓰기</button>
	</div>
</div>
<jsp:include page="footer.jsp" />
<script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/js/market.js"></script>
<script type="text/javascript">
const rows = document.querySelector('#boards tbody');
let data = null;
rows.addEventListener('click', function (e) {
  let table = new DataTable('#boards');
  let data = table.row(e.target).data();

location.href = 'Boards?action=view&bno=' + data[0] + '&actiont=' + data[1] + '&check=' + data[6];
})
function move() {
	if (<%=uno.equals("")%>) {
		location.href = 'no.jsp?action=noLogin';
	} else if (<%=action.equals("notice")%>) {
		if (<%=admin.equals("1")%>) {
			location.href= '<%=request.getContextPath() %>' + '/Boards?action=write&actiont=' + '<%=action %>';
		} else {
			alert('권한이 없습니다.');
		}
	} else {
		location.href= '<%=request.getContextPath() %>' + '/Boards?action=write&actiont=' + '<%=action %>'; 
	}
}
</script>


