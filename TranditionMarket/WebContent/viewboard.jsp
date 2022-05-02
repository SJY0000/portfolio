<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page import="model.Boards"%> 
<%@ page import="dao.MypageDAO"%>
<%@ page import="model.User"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<meta charset="UTF-8">
    <jsp:include page="header.jsp" />
 
    <div class="position-relative col-8 mx-auto mt-4 ">
      <%
      	String actiont = request.getParameter("actiont");
		String action = request.getParameter("action");
		String uno = (String) session.getAttribute("uno");
		String nick = (String) session.getAttribute("nick");
		String admin = (String) session.getAttribute("admin");
		String id = (String) session.getAttribute("userID");
		
		if (uno == null) {
			session.setAttribute("uno","");
			session.setAttribute("nick","");
			session.setAttribute("admin","0");
		}
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

      
      if (actiont.equals("notice")) {
      %>
      <h1 id="boardname" class="mb-5 pb-5 fw-bold">공지사항</h1>
      <%
      } else {
      %>
      <h1 id="boardname" class="mb-5 pb-5 fw-bold">후기게시판</h1>
      <%
      }
      %>
      <%
      if (uno == null || uno.equals("")) { 
    	  actiont = "no";
      	}
      %>
      <div class="container">
        <div class="row rowcolumn">
          <div class="col-1 bg-secondary bg-opacity-10">제목</div>
          <div class="col-8">${boardlist.title}</div>
          <div class="col-1 bg-secondary bg-opacity-10">작성자</div>
          <div class="col-2">${boardlist.writer}</div>
          <div class="col-2 bg-secondary bg-opacity-10">추천수</div>
          <div class="col-2">${boardlist.reco}</div>
          <div class="col-2 bg-secondary bg-opacity-10">조회수</div>
          <div class="col-2">${boardlist.check}</div>
          <div class="col-2 bg-secondary bg-opacity-10">작성일</div>
          <div class="col-2">${boardlist.date}</div>
        </div>
        <br>
        <div>${boardlist.content}</div>
        <br />
        <button id="recom" class="btn btn-primary rounded mx-auto d-block">
          <i class="bi bi-hand-thumbs-up-fill"></i><br />추천하기
        </button>
        <hr />
        <button class="btn btn-danger rounded float-end m-1" data-bs-toggle="modal" data-bs-target="#modalcontent">삭제</button>
        <button class="btn btn-secondary rounded float-end m-1" onclick="cedit()">수정</button>
      </div>
      <br>
      <br>
      <br>
      <br>
    
        <%
            int bno =(Integer) request.getAttribute("bno");
            int cno = 0;
            String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            try{
            	String url = "jdbc:mysql://localhost:3306/tranditionmarket?useSSL=false";
                Connection conn = DriverManager.getConnection(url,"root","1234");
                                                              	
                PreparedStatement pstmt = conn.prepareStatement("SELECT * from comment join user on comment.uno = user.uno join board on board.bno = comment.bno where comment.bno = ? order by cno asc");			
                pstmt.setInt(1, bno);
                ResultSet rs = pstmt.executeQuery();
                
               while(rs.next()){
	               Boards board = new Boards();
	               board.setBno(rs.getInt("bno"));
		           board.setCno(rs.getInt("cno"));
		           board.setUno(rs.getInt("uno"));
		           board.setWriter(rs.getString("nick"));
		           board.setCcontent(rs.getString("ccontent"));
		           board.setDate(rs.getString("cdate").substring(0, 10));
		                                                              		
		           out.print("<div class='box border border-secondary mb-3'>");  
		           out.print("<div class='bg-secondary bg-opacity-10'><strong>작성자</strong> "
		           + board.getWriter()
		           +" <strong>작성일</strong> " 
		           + rs.getString("cdate").substring(0, 10)
		           + "<button id='editbtn' class='btn btn-secondary p-1 m-1' data-bs-toggle='modal' data-bs-target='#modal-edit' data-cno='" + board.getCno() + "' data-uno='" + board.getUno() + "'>수정</button>"
		           + "<button id='deletebtn' class='btn btn-danger p-1 m-1' data-bs-toggle='modal' data-bs-target='#modalcomment' data-cno='" + board.getCno() + "' data-uno='" + board.getUno() + "'>삭제</button>"
		           + " </div>");  
		           out.print("<p>"+rs.getString("ccontent")+"</p>");  
		           out.print("</div>"); 
		           }  
		           conn.close();
	                                                              	
	           } catch (Exception e){
	              e.printStackTrace();
	           }
            %>
	<div id="output"></div>  
	<br>
        <textarea class="comments" name="comment" style="width:100%; resize:none;" required></textarea>
        <br>
        <button class="btn btn-secondary opacity-50 rounded float-end" id="com" type="button">등록</button>
      </div>
<div class="modal fade" id="modal-edit" tabindex="-1" aria-labelledby="editLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 id="title-edit" class="modal-title"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form id="edit" autocomplete="off" action="<%=request.getContextPath()%>/Boards">
      	<input type="hidden" name="action" value="comment">
      	<input type="hidden" name="actiont" value="<%=actiont%>">
        <div class="modal-body">
          <div class="form-group">
            <label for="ccontent">content</label>
            <input type="text" class="form-control" id="ccontent" name="ccontent" value="${boardlist.ccontent}" required >
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-success">저장</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        </div>
      </form>
    </div>
  </div>
</div>
<div class="modal fade" id='modalcontent' tabindex="-1"  aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>삭제하시겠습니까?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="cdelete()">확인</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id='modalcomment' tabindex="-1"  aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>삭제하시겠습니까?</p>
      </div>
      <div class="modal-footer">
      <form id="cd" autocomplete="off" action="<%=request.getContextPath()%>/Boards">
      	<input type="hidden" name="action" value="deletecomment">
      	<input type="hidden" name="actiont" value="<%=actiont%>">
      	<input type="hidden" name="bno" value="${boardlist.bno}">
      	<input type="hidden" name="nick" value="<%=session.getAttribute("nick")%>">
        <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </form>
      </div>
    </div>
  </div>
</div>
 <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script type="text/javascript">
      const button = document.getElementById('recom');

      button.addEventListener('click', () => {
        reco();
      });

      function reco() {
    	if (<%=uno == null || uno.equals("")%>) {
    		location.href = 'no.jsp?action=noLogin';
    	} else {
    	location.href = '<%=request.getContextPath()%>' + '/Boards?action=reco&bno=' + '${boardlist.bno}' + '&reco=' + '${boardlist.reco}' + '&actiont=' + '<%= actiont %>' + '&uno=' + '<%=request.getParameter("uno") %>' + '&nick=' + '<%= request.getParameter("nick")%>';    		
    	}
  
      }
    </script>
    <script type="text/javascript">
    const path = '<%= request.getContextPath() %>';
    $('#com').on('click', function (e) {
    	if (<%=uno == null || uno.equals("")%>) {
        	location.href = 'no.jsp?action=noLogin';
        } else {
    	let comment =$('.comments').val(); 
       $.ajax({
          type: 'GET',
          url: path + '/Boards?action=commentsave',
          data: { "comment" : comment,
        	  		"bno" : ${boardlist.bno}}, 
        contentType: "charset=UTF-8", 
      
        })
          .done(function (data) {
        	  location.reload();
          })
          .fail(function (jqXHR, textStatus) {
            console.log(textStatus);
          });
      }    	
    })
    </script>

      <script type="text/javascript">
      const editbutton = document.getElementById('editbtn');
      let com = document.querySelectorAll('#output .box');

      function cedit() {
    	  let uno = '<%=uno%>';
    	  let boarduno = '${boardlist.uno}';
    	  let admin = "<%= admin%>";
    	  if (<%=uno == null || uno.equals("")%>) {
    		  location.href = 'no.jsp?action=noLogin';
    	  } else if (uno == boarduno || admin == "1" ) {
          	location.href = '<%=request.getContextPath()%>' + '/write.jsp?bno=' + '${boardlist.bno}' +  '&actiont=' + '<%=request.getParameter("actiont")%>' + '&action=edit' + '&title=' + '${boardlist.title}' + '&content=' + '${boardlist.content}' + '&check=' + '${boardlist.check}';    		      		  
    	  } else {  
    		  alert('수정 및 삭제를 할 수 없습니다.');
    		  location.reload();
    	  }
      }
      function cdelete() {
    	  let uno = '<%=uno%>';
    	  let boarduno = '${boardlist.uno}' == null ? <%=session.getAttribute("bno")%> : '${boardlist.uno}';
    	  let admin = "<%= admin%>";
    	  if (<%=uno == null || uno.equals("")%>) {
    		  location.href = 'no.jsp?action=noLogin';
    	  } else if (uno == boarduno || admin == "1") {
           location.href = '<%=request.getContextPath()%>' + '/Boards?action=cdelete&bno=' + '${boardlist.bno}' +'&actiont=' + '<%=request.getParameter("actiont")%>';    		      		  
    	  } else {
    		  alert('수정 및 삭제를 할 수 없습니다.');
    		  location.reload();
    	  }
      }
      </script>
    
      <script type="text/javascript">
      $('.box').on('click', '#editbtn', function (e) {
        const $modal = $('#modal-edit');
  	  	let cuno = $(this).data('uno');
  	  	let uno = '<%=uno%>';
  	  	let admin = "<%=admin%>";
        $modal.find('#title-edit').text('댓글수정');
        
        $.ajax({
          type: 'POST',
          url: path + '/Boards?action=editcomment',
          data: 'cno=' + $(this).data('cno'), 
          dataType: 'json', 

        })
          .done(function (data) {
            console.log(data);
            
              $('#ccontent').val(data.board.ccontent);  
              $modal.find('#edit').append('<input type="hidden" name="cno" value="' + data.board.cno + '">');
              $modal.find('#edit').append('<input type="hidden" name="ccontent" value="' + data.board.ccontent + '">');
              $modal.find('#edit').append('<input type="hidden" name="bno" value="' + data.board.bno + '">');
              $modal.find('#edit').append('<input type="hidden" name="cuno" value="' + cuno + '">');
              $modal.find('#edit').append('<input type="hidden" name="uno" value="' + uno + '">');
              $modal.find('#edit').append('<input type="hidden" name="admin" value="' + admin + '">');

              $modal.show();
            
          })
          .fail(function (jqXHR, textStatus) {
            console.log(textStatus);
          });
      });
      $('.box').on('click', '#deletebtn', function (e) {
    	  const $modal = $('#modalcomment');
    	  	let cuno = $(this).data('uno');
			let uno = '<%=uno%>';
			let admin = "<%=admin%>";
			$modal.hide();
          $.ajax({
            type: 'POST',
            url: path + '/Boards?action=editcomment',
            data: 'cno=' + $(this).data('cno'), 
            dataType: 'json', 

          })
            .done(function (data) {
              console.log(data);

             	$modal.find('#cd').append('<input type="hidden" name="cno" value="' + data.board.cno + '">');
             	$modal.find('#cd').append('<input type="hidden" name="cuno" value="' + cuno + '">');
             	$modal.find('#cd').append('<input type="hidden" name="uno" value="' + uno + '">');
             	$modal.find('#cd').append('<input type="hidden" name="admin" value="' + admin + '">');

                               	        		  
             	$modal.show();
              
            })
            .fail(function (jqXHR, textStatus) {
              console.log(textStatus);
            });
    		  
    	   
        });    
      </script>


<jsp:include page="footer.jsp"/>

