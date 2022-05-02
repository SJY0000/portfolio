<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
if (<%=request.getParameter("action").equals("noLogin")%>) {
	alert("로그인을 해주세요");
	login();
} else if (<%=request.getParameter("action").equals("permission")%>) {
	alert("댓글을 수정, 삭제 할 권한이 없습니다.");
	permission();
}

function login() {
	location.href = 'login.jsp';
}

function permission() {
	location.href = '<%=request.getContextPath()%>' + '/Boards?action=' + '<%=request.getParameter("actiont")%>';
}
</script>
</body>
</html>