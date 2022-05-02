<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int cno = Integer.parseInt(request.getParameter("cno"));

try{
	String url = "jdbc:mysql://localhost:3306/traditionmarket?useSSL=false";
	Connection conn = DriverManager.getConnection(url,"root","1234");
	
	PreparedStatement pstmt = conn.prepareStatement("SELECT * from comment join user on comment.uno = user.uno join board on board.bno = comment.bno where comment.cno = ?");			
	pstmt.setInt(1, cno);
	ResultSet rs = pstmt.executeQuery();
	

	while(rs.next()){
		out.print("<div class='box border border-secondary'>");  
		out.print("<div class='bg-secondary bg-opacity-10'><strong>작성자</strong> "
		+rs.getString("nick")
		+" <strong>작성일</strong> " 
		+ rs.getString("date").substring(0,10)
		+ "<button class='btnu btn-secondary p-1 m-1 btn-edit' data-id=<c:out value=" + rs.getInt("cno") + "'>수정</button>"
		+ "<button class='btnd btn-danger p-1 m-1 btn-delete' >삭제</button>"
		+ " </div>");  
		out.print("<p>"+rs.getString("ccontent")+"</p>");  
		out.print("</div>"); 
	}  
	conn.close();
	
} catch (Exception e){
	e.printStackTrace();
}

%>
</body>
</html>