<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tld/custom_tag.tld" %>
<!DOCTYPE>
<html>
	<head>	
		<meta charset="UTF-8">
		<title>글 목록</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<!-- <script type="text/javascript" src="/include/js/jquery-1.11.0.min.js" /> -->
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
		<script type="text/javascript">
				$(function(){
					
					if("<c:out value='${data.keyword}' />" != ""){
						$("#keyword").val("<c:out value='${data.keyword}' />");
						$("#search").val("<c:out value='${data.search}' />");
					}
					
					/*한 페이지에 보여줄 레코드 수 조회 후 선택한 값 그대로 유지하기 위한 설정*/
					if("<c:out value='${data.pageSize}' />"!=""){
						$("#pagaSize").val("<c:out value='${data.pageSize}'/>")
					}
					
					
					$("#search").change(function(){
						if($("#search").val()=="all"){
							$("#keyword").val("전체 데이터를 조회합니다.");
						}else if($("#search").val()!="all"){
								$("#keyword").val("");
								$("#keyword ").focus();
						}
						
					});
					
					$("#searchData").click(function(){
						if($("#search").val()!="all"){
								if(!chkSubmit($("#keyword"),"검색어를")) return;
						}
						goPage(1);
					});
					
					/*한 페이지에 보여줄 레코드 수 변경될 때마다 처리 이벤트*/
					$("#pageSize").change(function(){
						goPage(1);
					});

					
					$("#writeForm").click(function(){
					$("#detailForm").attr("action", "/board/writeForm.kbw").submit();
					});
					$(".goDetail").click(function(){
							var k_num = $(this).parents("tr").attr("data-num");
							$("#k_num").val(k_num);
							$("#detailForm").attr({
								"method":"get",
								"action":"/board/boardDetail.kbw"
							});
							$("#detailForm").submit();
					});
					
				});
				
				//정렬 버튼 클릭시 처리함수 
				function setOrder(order_by){
						$("#order_by").val(order_by);
						if($("#order_sc").val()=="DESC"){
									$("#order_sc").val('ASC');
						}else{
									$("#order_sc").val('DESC');
						}
						
						goPage(1);
				}
				
				//검색과 한 페이지에 보여줄 레코드 수 처리 및 페이징을 위한 실질적인 처리함수 
				function goPage(page){
						if($("#search").val()=="all"){
								$("#keyword").val("");
						}
						$("#page").val(page);
						$("#f_search").attr({
							'method':'get',
							'action':'/board/boardList.kbw'
						});
						$("#f_search").submit();
				}
		</script>
	</head>
	<body>
			<div id="boardContainer">
				<div id="boardTit"><h3>글 목록</h3></div>
				<form name="detailForm" id="detailForm" >
					<input type="hidden" name="k_num" id="k_num">
					<input type="hidden" name="page" value="${data.page}">
					<input type="hidden" name="pageSize" value="${data.pageSize}">
				</form>
				
				<!-- =============================================검색기능 시작 -->
				<div id="boardSearch">
							<form id="f_search" name="f_search">
								<input type="hidden" id="page" name="page" value="${data.page}" />
								<input type="hidden" id="order_by" name="order_by" value="${data.order_by}" />
								<input type="hidden" id="order_sc" name="order_sc" value="${data.order_sc}" />
								
									<table summary="검색">
										<colgroup>
											<col width="70%"><col>
											<col width="30%"><col>
										</colgroup>
										<tr>
											<td id="btd1">
											<label>검색조건</label>
											<select id="search" name="search">
												<option value="all">전체</option>
												<option value="k_title">제목</option>
												<option value="k_content">내용</option>
												<option value="k_name">작성자</option>
											</select>
											<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요" />
											<input type="button" id="searchData" value="검색" />
											</td>
											<td id="btd2">한 페이지에
												<select id="pageSize" name="pageSize">
														<option value="10">10줄</option>
														<option value="20">20줄</option>
														<option value="30">30줄</option>
														<option value="50">50줄</option>
														<option value="70">70줄</option>
														<option value="100">100줄</option>
												</select>	
											</td>
										</tr>
									</table>							
							</form>
				</div>
				<!-- =============================================검색기능 종료 -->
				
				<div id="boardList"> 
				<table summary="게시판 리스트"> 
					<colgroup>
						<col width="10%" />
						<col width="62%" />
						<col width="15%"/>
						<col width="13%"/>
					</colgroup>
					<thead>
							<tr>
								<th><a href="javascript:setOrder('k_num');">글번호
								<c:choose>
									 <c:when test="${data.order_by == 'k_num' and data.order_sc == 'ASC'}">▲</c:when>
									 <c:when test="${data.order_by == 'k_num' and data.order_sc == 'DESC'}">▼</c:when>
									 <c:otherwise>▲</c:otherwise>
								</c:choose></a></th>
								<th>글제목</th>
								<th><a href="javascript:setOrder('k_date');">작성일
								<c:choose>
       								 <c:when test="${data.order_by == 'k_date' and data.order_sc == 'ASC'}">▲</c:when>
									 <c:when test="${data.order_by == 'k_date' and data.order_sc == 'DESC'}">▼</c:when>
									 <c:otherwise>▲</c:otherwise>
								</c:choose></a></th>
								<th class="borcle">작성자</th>
							</tr>
					</thead>
					<tbody>
					<!-- 데이터 출력 -->
					<c:choose>
						<c:when test="${not empty boardList}">
							<c:forEach var="board" items="${boardList}" varStatus="status">
								<tr align="center" data-num="${board.k_num}">
									<!-- <td>${count - (status.count-1)}</td> -->
									<td>${board.k_num}</td>
									<td align="left"><span class="goDetail">${board.k_title }</span></td>
									<td>${board.k_date}</td>
									<td>${board.k_name}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4" align="center">등록된 게시물이 존재하지 않습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				</div>
				<%-- 리스트 종료 --%>
				<%-- 글쓰기 버튼 출력 시작  --%>
				<div id="boardBut">
					<input type="button" value="글쓰기" id="writeForm" name="writeForm">
				</div>				
				<%-- 글쓰기 버튼 출력 종료 --%>
				<!-- 페이지 네비게이션  -->
				<div id="boardPage">
					<tag:paging page="${param.page}" total="${total}" list_size="${data.pageSize}" />
				</div>
				</div>
	</body>
</html>