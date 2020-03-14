<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.spring.board.vo.BoardVO" %>
<%--<%
	BoardVO bvo = (BoardVO)request.getAttribute("detail");
	System.out.println("bvo >>> : " + bvo); %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>글상세 보기</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
		<script type="text/javascript">
			var butChk = 0; //수정버튼과 삭제버튼을 구별하기 위한 변수
			$(function(){
				
				$("#pwdChk").hide();
				
				var file = "<c:out value='${detail.k_file}' />";
				if(file!=""){
							$("#fileImage").attr({
										src:"/uploadStorage/${detail.k_file}",
										width:"400px",
										height:"300px"
							});
				}
				
				$("#updateForm").click(function(){
					$("#pwdChk").show();
					$("#msg").text("작성시 입력한 비밀번호를 입력해주세요").css("color", "#000099");
					
					butChk = 1;
				});
				
				$("#boardDelete").click(function(){
					$("#pwdChk").show();
					$("#msg").text("작성시 입력한 비밀번호를 입력해주세요").css("color", "#000099");
					
					butChk = 2;
				});
				
				$("#pwdBut").click(function(){
					pwdConfirm();
				});
				
				$("#boardList").click(function(){
					location.href="/board/boardList.kbw";
					
				});
			});
			
			function pwdConfirm(){
				if(!chkSubmit($("#k_pwd"), "비밀번호를")) return;
				else{
					$.ajax({
						url : "/board/pwdConfirm.kbw",
						type : "POST",
						data : $("#f_pwd").serialize(),
						error : function(){
							alert("시스템 오류입니다, 관리자에게 문의하세요");
						},
					success : function(resultData){
						var goUrl=""
						if(resultData==0){
								$("#msg").text("작성시 입력한 비밀번호가 일치하지 않습니다").css("color","red");
								$("#k_pwd").select();
						}else if(resultData==1){
							$("#msg").text("");
							if(butChk==1){
								goUrl = "/board/updateForm.kbw";
							}else if(butChk==2){
								goUrl = "/board/boardDelete.kbw";
							}
							$("#f_data").attr("action", goUrl);
							$("#f_data").submit();
							
						}
					}
					});
				}
				
			}
		</script>
	</head>
	<body>
			<div id="boardTit"><h3>글상세</h3></div>
			<form name="f_data" id="f_data" method="post">
				<input type="hidden" name="k_num" value="${detail.k_num}">
				<input type="hidden" name="k_file" value="${detail.k_file} ">
			</form>
			<table id="boardPwdBut">
					<tr>
						<td id="btd1">
								<div id="pwdChk">
									<form name="f_pwd" id="f_pwd">
										<input type="hidden" name="k_num" id="k_num" value="${detail.k_num}" />
										<label for="k_pwd" id="l_pwd">비밀번호</label>
										<input type="password" name="k_pwd" id="k_pwd" />
										<input type="button" id="pwdBut" name="pwdBut" value="확인" />
										<span id="msg"></span>
									</form>
								</div>
						</td>
						<td id="btd2">
								<input type="button" value="수정" id="updateForm">
								<input type="button" value="삭제" id="boardDelete">
								<input type="button" value="목록" id="boardList">
						</td>
					</tr>
			</table>
			<%-- 비밀번호 확인 버튼 및 버튼 추가 종료  --%>
			<%-- 상세정보 보여주기 시작 --%>
			
			<div id="boardDetail">
					 <table>
					 		<colgroup>
					 				<col width="25%" />
					 				<col width="25%" />
					 				<col width="25%" />
					 				<col width="25%" />
					 		</colgroup>
					 		<tbody>
					 			<tr>
					 				<td class="ac">작성자</td>
					 				<td> ${detail.k_name}</td>
					 				<td class="ac">작성일</td>
					 				<td>${detail.k_date}</td>
					 			</tr>
					 			<tr>
					 				<td class="ac">제목</td>
					 				<td colspan="3">${detail.k_title}</td>
					 			</tr>
					 			<tr>
					 				<td class="ac">내용</td>
					 				<td colspan="3">${detail.k_content}</td>
					 			</tr>
					 			<tr class="ctr"> 
					 					<td class="ac">첨부파일</td>
					 					<td colspan="3"><img id="fileImage" /></td>
					 			</tr>
					 		</tbody>
					 </table>
			</div>
			<%-- 상제정보 보여주기 종료 --%>
			<jsp:include page="reply.jsp"></jsp:include>
	</body>
</html>