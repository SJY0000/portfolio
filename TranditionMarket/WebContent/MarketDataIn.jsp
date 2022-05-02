<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="js/MarketDataIn.js"></script>
</head>
<body>
    <%request.setCharacterEncoding("utf-8"); %>	
	<form action="<%=request.getContextPath() %>/Data" method="post" id="data">
		<input type="submit" value="db에 저장">
		<input type="hidden" name="command" value="listdb" >
	</form>
</body>
</html>