<%@page import="model.MarketImgBean"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.MarketBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="header.jsp">
	<c:param name="title" value="전알사"></c:param>
</c:import>


<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a668c56babf85dfbe2bbf6a57c4e1354"></script>
<body>
<div class="container">
	<jsp:include page="searchbar.jsp"/>
	<form action="<%=request.getContextPath()%>/View" method="get">
		<input type="hidden" name="action" value="viewMarket">
		<%
			ArrayList<MarketBean> searchedMK = new ArrayList<>();
			boolean dataNull = (boolean)request.getAttribute("MKdataNull");
			if(dataNull){
		%>
			<div class="not_found02"> <p><em>'<%=request.getAttribute("searchKW") %>'</em>에 대한 검색결과가 없습니다.</p>  <ul> <li>단어의 철자가 정확한지 확인해 보세요.</li> <li>한글을 영어로 혹은 영어를 한글로 입력했는지 확인해 보세요.</li> <li>검색어의 단어 수를 줄이거나, 보다 일반적인 검색어로 다시 검색해 보세요.</li> </ul> </div>
		<%	
			}else{
				searchedMK = (ArrayList<MarketBean>)request.getAttribute("searchedMK");
		%>
		<hr class="featurette-divider" />
		<div id="map" style="width: 650px; height: 500px;"></div>
		<script>
			var container = document.getElementById('map');
			var options = {
				center: new kakao.maps.LatLng(<%=searchedMK.get(0).getLat()%>, <%=searchedMK.get(0).getLongi()%>),
				level: 4
			};
			var map = new kakao.maps.Map(container, options);
			
			var marker = new kakao.maps.Marker({
			    map: map, 
			    position: new kakao.maps.LatLng(<%=searchedMK.get(0).getLat()%>, <%=searchedMK.get(0).getLongi()%>)
			});
			marker.setMap(map);
			
			var iwContent = '<div class="card text-dark">' + 
            '      <div class="card-header">' + 
            '          <button type="submit" name="mno" value="<%=searchedMK.get(0).getMno() %>" class="nav-link px-2 text-bule border border-0"><%=searchedMK.get(0).getMname()%> <span class="align-self-end" onclick="closeOverlay()" title="닫기"><i class="bi bi-x-square text-dark"></i></span></button>' + 
            '      </div>' + 
            '      <div class="card-body">' + 
            '            <div class="ellipsis"><%=searchedMK.get(0).getMadd()%></div>'+
            '      </div>' +
            '</div>';

			var infowindow = new kakao.maps.InfoWindow({
				content : iwContent
			});
			infowindow.open(map, marker);
			kakao.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map, marker);
			});
			function closeOverlay() {
				infowindow.open(null, marker);
			}
		</script>
		<%
			List<Boolean> MKIMGdataNull = new ArrayList<>();
			List<MarketImgBean> searchedMKIMG = new ArrayList<>();
			for (int i = 0; i < searchedMK.size(); i++) {
				MKIMGdataNull.add((Boolean)request.getAttribute("MKIMGdataNull"+i));
				if(!MKIMGdataNull.get(i)){
					searchedMKIMG = (ArrayList<MarketImgBean>)request.getAttribute("searchedMKIMG"+i);
				}
		%>
		<hr class="featurette-divider" />
		<div class="row featurette">
			<div class="col-md-7 order-md-2">
				<h2 class="featurette-heading">
					<button type="submit" name="mno" value="<%=searchedMK.get(i).getMno() %>" class="nav-link bg-white px-2 text-bule border border-0"><%=searchedMK.get(i).getMname()%></button>
				</h2>
				<p class="lead">
					<%=searchedMK.get(i).getMadd()%>
				</p>
			</div>
			<div class="col-md-5 order-md-1">
				<div class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto">
					<%
					if(!MKIMGdataNull.get(i)){
					%>
              			<button type="submit" class="bg-white border border-0" name="mno" value="<%=searchedMK.get(i).getMno() %>"><img style="width: 300px; height: 300px; " alt="<%=searchedMK.get(i).getMname()%>" src="<%=searchedMKIMG.get(0).getUrl()%>"></button>
              		<%
					}else{
					%>
						<img style="width: 300px; height: 300px; " alt="<%=searchedMK.get(i).getMname()%>" src="">
					<%
					}
					%>
            	</div>
			</div>
		</div>
		<%
			}
		%>
		<%
			}
		%>
	</form>
</div>
</body>
<c:import url="footer.jsp"></c:import>