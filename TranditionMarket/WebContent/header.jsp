<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.User"%>
<%@ page import="dao.MypageDAO"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" />
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/v/dt/jqc-1.12.4/dt-1.11.4/b-2.2.2/sl-1.3.4/datatables.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/market.css" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mypage.css" />
<title>전알사</title>
</head>
<body>
	<%
		String id = (String) session.getAttribute("userID");
		String mainId = id+"님 환영합니다.";
		MypageDAO mdao = new MypageDAO();
		User user = mdao.showData(id); 
		
	%>
	<header class="p-3 bg-dark text-white">
		<div class="container">
			<div class="d-flex flex-wrap justify-content-between">
				<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
					<li><a href="main.jsp" class="nav-link px-2 text-white"><svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-shop" viewBox="0 0 16 16"><path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.371 2.371 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976l2.61-3.045zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0zM1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5zM4 15h3v-5H4v5zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1v-3zm3 0h-2v3h2v-3z" /></svg></a></li>
					<li><a href="<%=request.getContextPath()%>/Boards?action=notice" class="nav-link px-2 mt-3 text-white">공지사항</a></li>
					<li><a href="<%=request.getContextPath()%>/Boards?action=board&" class="nav-link px-2 mt-3 text-white">게시판</a></li>
				</ul>
				<div class="text-end flex-row-reverse mt-2">
				
			<%
				if(session.getAttribute("userID") == null) {
			%>
					<button type="button" class="btn btn-outline-light me-2" onclick="location.href='<%=request.getContextPath()%>/login.jsp'">로그인</button>
					<button type="button" class="btn btn-warning" onclick="location.href='<%=request.getContextPath()%>/join.jsp'">회원가입</button>
			<%
				} else {
			%>
					
					<img src="<%=request.getContextPath()%><%=user.getUserImg() %>" alt="image" width="32" height="32" class="rounded-circle">
					<% System.out.println(request.getContextPath()); %>
					<% System.out.println(user.getUserImg());%>
					<span class="mr-2"><%=mainId%></span>
					<button type="button" class="btn btn-warning" onclick="location.href='<%=request.getContextPath()%>/Mypage'">마이페이지</button>
					<button type="button" class="btn btn-outline-light me-2" onclick="location.href='<%=request.getContextPath()%>/logout.jsp'">로그아웃</button>
			<%
				}
			%>
				</div>
			</div>
		</div>
	</header>
