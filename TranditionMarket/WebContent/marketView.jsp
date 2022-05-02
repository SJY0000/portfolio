<%@page import="model.MarketImgBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.MarketBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="header.jsp">
	<c:param name="title" value="전알사"></c:param>
</c:import>
<div class="p-3 p-3">
	<div class="container">
		<%
			MarketBean viewMK = new MarketBean();
			List<MarketImgBean> viewMKIMG = new ArrayList<>();
			boolean MKIMGdataNull = (boolean) request.getAttribute("MKIMGdataNull");
			viewMK = (MarketBean) request.getAttribute("viewMK");
			if (!MKIMGdataNull) {
				viewMKIMG = (ArrayList<MarketImgBean>) request.getAttribute("viewMKIMG");
			}
		%>
		
		<div class="row featurette mt-5">
			<div class="col-md-7 order-md-2">
				<h2 class="featurette-heading">
					<div class="px-2 fw-bold"><%=viewMK.getMname()%>&nbsp&nbsp<i class="bi bi-star"></i></div>
				</h2>
				<br>
				<p class="lead">
					유형 :
					<%=viewMK.getMtype()%></p>
				<p class="lead">
					주소 :
					<%=viewMK.getMadd()%></p>
				<p class="lead">
					개설 주기 :
					<%=viewMK.getPeriod()%></p>
				<p class="lead">
					점포수 :
					<%=viewMK.getStore()%></p>
				<p class="lead">
					취급 품목 :
					<%=viewMK.getObject()%></p>
				<p class="lead">
					공중화장실 :
					<%
					if (viewMK.getToilet() == 1) {
				%>
					있음
					<%
					} else {
				%>
					없음
					<%
					}
				%>
				</p>
				<p class="lead">
					주차장 :
					<%
					if (viewMK.getParking() == 1) {
				%>
					있음
					<%
					} else {
				%>
					없음
					<%
					}
				%>
				</p>
			</div>
			<div class="col-md-5 order-md-1">
				<div
					class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto">
					<%
						if (!MKIMGdataNull) {
					%>
					
						<img style="width: 500px; height: 500px;"
							alt="<%=viewMK.getMname()%>" src="<%=viewMKIMG.get(0).getUrl()%>">
				
					<%
						} else {
					%>
					<img style="width: 500px; height: 500px;"
						alt="<%=viewMK.getMname()%>" src="">
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

</div>

<c:import url="footer.jsp"></c:import>