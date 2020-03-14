<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>글수정 화면</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
		<script type="text/javascript">
		 		$(function(){
		 			
		 				$("#boardUpdate").click(function(){
		 					
		 					if(!chkSubmit($("#k_name"), "이름을")) return;
		 					else if(!chkSubmit($("#k_title"), "제목을")) return;
		 					else if(!chkSubmit($("#k_content"), "제목을")) return;
		 					else{
		 						if($('#file').val().indexOf('.')>-1){
		 							var ext = $('#file').val().split('.').pop().toLowerCase();
		 							if(jQuery.inArray(ext, ['gif','jpg','jepg','png'])==-1){
		 								alert("'gif','jpg','jepg','png' 파일만 업로드 할 수 있습니다.");
		 								return;
		 							}
		 						}
		 						$("#f_writeForm").attr({
		 							'method':'post',
		 							'action':'/board/boardUpdate.kbw'
		 							
		 						});
		 						$("#f_writeForm").submit();
		 					}
		 				});
		 				$("#boardList").click(function(){
		 					location.href="/board/boardList.kbw";
		 				});
		 		});
		</script>
	</head>
	<body>
			<div id="boardTit"><h3>글수정</h3></div>
			<form id="f_writeForm" name="f_writeForm" enctype="multipart/form-data">
					<input type="hidden" id="k_num" name="k_num" value="${updatedata.k_num}">
					<input type="hidden" id="k_file" name="k_file" value="${updatedata.k_file}">
					<table id="boardWrite">
							<tr>
									<td>작성자</td>
									<td><input type="text" name="k_name" id="k_name" value="${updatedata.k_name}" /></td>
							</tr>
							<tr>
									<td>글제목</td>
									<td><input type="text" name="k_title" id="k_title" value="${updatedata.k_title}" /></td>
							</tr>
							<tr>
									<td>내용</td>
									<td height="200"><textarea name="k_content" id="k_content" rows="10" cols="70">${updatedata.k_content}</textarea>
							</tr>
							<tr>
									<td>첨부파일</td>
									<td><input type="file" name="file" id="file" ></td>
							</tr>
							<tr>
									<td>비밀번호</td>
									<td><input type="password" name="k_pwd" id="k_pwd"><label>수정할 비밀번호를 입력하세요.</label></td>
							</tr>
					</table>
			</form>
			<div id="boardBut">
					<input type="button" value="수정" class="but"  id="boardUpdate" />
					<input type="button" value="목록" class="but"  id="boardList" />
			</div>
	</body>
</html>