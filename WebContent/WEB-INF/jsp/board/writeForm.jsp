<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>글쓰기 화면</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript"
		  src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	    <script type="text/javascript" src="../se2/js/service/HuskyEZCreator.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#boardInsert").click(function(){
						 // 에디터의 내용이 textarea에 적용된다.
						 oEditors.getById["k_content"].exec("UPDATE_CONTENTS_FIELD", []);

						 // 에디터의 내용에 대한 값 검증은 이곳에서
						 // document.getElementById("ir1").value를 이용해서 처리한다.

						 try {
						     elClickedObj.form.submit();
						 } catch(e) {}
					
					if(!chkSubmit($('#k_name'),"이름을"))	return;
					else if(!chkSubmit($('#k_title'),"제목을"))	return;
					else if(!chkSubmit($('#k_content'),"작성할 내용을"))	return;
					else if(!chkSubmit($('#file'),"첨부파일을"))	return;
					else if(!chkSubmit($('#k_pwd'),"비밀번호를"))	return;
					else{
						/* 배열내의 값을 찾아서 입덱스를 반환(요소가 없을 경우-1반환) jQuery.inArray(찾을 값, 검색 대상의 배열)*/
						var ext = $('#file').val().split('.').pop().toLowerCase();
						if(jQuery.inArray(ext, ['gif', 'png', 'jpg', 'jpeg'])== -1){
							alert("gif png jpg jpeg 파일만 업로드 할 수 있습니다.");
							return;
						}
						$('#f_writeForm').attr({
							
							'method':'post',
							'action':'/board/boardInsert.kbw'
						});
						$("#f_writeForm").submit();
					}
					
				});
				$('#boardList').click(function(){
					location.href="/board/boardList";
				});	
				
				var oEditors = [];
				nhn.husky.EZCreator.createInIFrame({
				 oAppRef: oEditors,
				 elPlaceHolder: "k_content",
				 sSkinURI: "../se2/SmartEditor2Skin.html",
				 fCreator: "createSEditor2"
				});
				
			});
		</script>
	</head>
	<body>
		<div id="boardTit"><h3>글쓰기</h3></div>
		<form id="f_writeForm" name="f_writeForm" enctype="multipart/form-data">
				<table id="boardWrite">
					<tr>
						<td>작성자</td>
						<td><input type="text" name="k_name" id="k_name" ></td>
					</tr>
					<tr>
						<td>글제목</td>
						<td><input type="text" name="k_title" id="k_title"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td height="200"><textarea name="k_content" id="k_content" rows="10" cols="70"></textarea></td>
					</tr>
					<tr>
						<td>첨부파일</td>
						<td><input type="file" name="file" id="file"></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="k_pwd" id="k_pwd"></td>
					</tr>				
				</table>
		</form>
		<div id="boardBut">
				<input type="button" value="저장" class="but" id="boardInsert">
				<input type="button" value="목록" class="but" id="boardList"> 
		</div>
	</body>
</html>