<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<jsp:include page="header.jsp"/>
			<div class="container h-75 d-flex justify-content-center">
				<div class="d-flex justify-content-center align-self-center">
					<div class="position-relative mx-auto">
						<div class="border border-dark mx-auto p-4">
							<form class="form-horizontal" action="Login" method="POST">
							<h2 class="text-center">Login</h2>
							<input type="hidden" name="action" value="login">
							<br><br>
							<div class="form-group">
								<label for="id">ID</label> 
								<input type="text" class="form-control"  
										name="userID" required="required" 
										placeholder="아이디를 입력하세요">
							</div>
							<div class="form-group">
								<label for="pwd">Password</label> 
								<input type="password" class="form-control" 
										name="userPassword" required="required"
										placeholder="비밀번호를 입력하세요"><br>
							</div>
							<div class="text-center">			
								<button type="submit" class="btn btn-warning">로그인</button>
							</div>
							</form>
						</div>
					</div>
				</div>
			</div>
	<jsp:include page="footer.jsp"/>	
