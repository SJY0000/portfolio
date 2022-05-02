<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>글쓰기</title>
</head>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<body>
	<jsp:include page="header.jsp" />


	<div class="container mt-3">
		<%
			request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String actiont = request.getParameter("actiont");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		if (actiont.equals("notice")) {
		%>
		<h1 class="mb-5 pb-5">공지사항/글쓰기</h1>
		<%
			} else {
		%>
		<h1 class="mb-5 pb-5">후기게시판/글쓰기</h1>
		<%
			}
		%>
		<%
			if (title == null) {
			title = "";
			content = "";
		}
		%>

		<div class="card">
			<div class="card-body">
				<form action="<%=request.getContextPath()%>/Boards" method="POST">
					<%
						if (action.equals("edit")) {
					%>
					<input type="hidden" name="action" value="cedit" /> <input
						type="hidden" name="bno"
						value="<%=request.getParameter("bno")%>" /> <input type="hidden"
						name="check" value="<%=request.getParameter("check")%>" /> <input
						type="hidden" name="uno"
						value="<%=(String) session.getAttribute("uno")%>" />
					<%
						} else {
					%>
					<input type="hidden" name="action" value="save" /> <input
						type="hidden" name="uno"
						value="<%=(String) session.getAttribute("uno")%>" />
					<%
						}
					%>
					<input type="hidden" name="actiont" value="<%=actiont%>" /> <input
						type="text" class="form-control mb-3" name="title"
						value="<%=title%>" placeholder="제목" maxlength="50" required />
					<textarea class="form-control mb-3" name="content" id="content" placeholder="내용" maxlength="2048"
						style="height: 350px" required><%=content%></textarea>
					<div class="row">
						<input type="submit" value="글쓰기"
							class="btn bg-secondary bg-opacity-10 ml-auto mr-3 mt-3" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>	
	<script type="text/javascript">
	$(document).ready(function() {
		  $('#content').summernote({        
			  	tabsize: 2,
		        height: 300,
		        toolbar: [
		          ['style', ['style']],
		          ['font', ['bold', 'underline', 'clear']],
		          ['color', ['color']],
		          ['para', ['ul', 'ol', 'paragraph']],
		          ['table', ['table']],
		          ['insert', ['link', 'picture', 'video']],
		          ['view', ['fullscreen', 'codeview', 'help']]
		        ]
		      });
		  $('.note-insert').remove(); //링크, 이미지, 동영상 넣는 기능 삭제(모달 버튼 이상하고 저장안됨)
		  });
	</script>
	
</body>
</html>
