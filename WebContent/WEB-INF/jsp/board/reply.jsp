<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	   	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>comment</title>
		<link rel="stylesheet" type="text/css" href="/include/css/reply.css">
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
		<script type="text/javascript">
				$(function(){
					//기본 댓글 목록 불러오기 
					var k_num = "<c:out value='${detail.k_num}' />"
					listAll(k_num)
					
					//댓글 내용 저장 이벤트
					$("#replyInsert").click(function(){
						//작성자 이름에 대한 입력여부 검사
						if(!chkSubmit($("#r_name"),"이름을")) return;
						else if(!chkSubmit($("#r_content"),"내용을")) return;
						else{
							var insertUrl = "/replies/replyInsert.kbw";
							//글 저장을 위한 post방식의 Ajax연동 처리
							$.ajax({
									url:insertUrl,
									type:"post",
									headers:{
										"Content-Type":"application/json",
										"X-HTTP-Method-Override":"post"
									},
									dataType:"text",
									data:JSON.stringify({
												k_num:k_num,
												r_name:$("#r_name").val(),
												r_pwd:$("#r_pwd").val(),
												r_content:$("#r_content").val()
									}),
									error:function(){
										//실행시 오류가 방생하였을 경우 
										alert("시스템 오류 입니다. 관리자에게 문의하세요");
									},
									success : function(resultData){
										if(resultData=="SUCCESS"){
											alert("댓글 등록이 완료되었습니다.");
											dataReset();
											listAll(k_num);
										}
									}
							});
						}
					});
					
					//수정버튼 클릭시 수정폼 출력
					$(document).on("click", ".update_form", function(){
						$(".reset_btn").click();
						var conText = $(this).parents("li").children().eq(1).html();
						console.log("conText:" + conText);
						$(this).parents("li").find("input[type='button']").hide();
						$(this).parents("li").children().eq(0).html();
						var conArea = $(this).parents("li").children().eq(1);
						
						conArea.html("");
						var data="<textarea name='content' id='content'>" + conText+"</textarea>";
						data+= "<input type='button'class='update_btn' value='수정완료'>";
						data+= "<input type='button'class='reset_btn' value='수정취소'>";
						conArea.html(data);
					});
					
					//초기화 버튼
					$(document).on("click", ".reset_btn", function(){
						var conText = $(this).parents("li").find("textarea").html();
						$(this).parents("li").find("input[type='button']").show();
						var conArea = $(this).parents("li").children().eq(1);
						conArea.html(conText);
					});
					
					//글 수정을 위한 Ajax 연동처리
					$(document).on("click", ".update_btn", function(){
						var r_num = $(this).parents("li").attr("data-num");
						var r_content = $("#content").val();
						if(!chkSubmit($("#content"),"댓글 내용을")) return;
						else{
							
							$.ajax({
										url:'/replies/' + r_num + ".kbw",
										type:'put',
										headers:{
												"Content-Type":"application/json",
												"X-HTTP-Method-Override":"PUT"},
											data:JSON.stringify({
												r_content:r_content}),
											dataType:'text',
											success:function(result){
												console.log("result:" + result);
												if(result == 'SUCCESS'){
													alert("수정되었습니다.");
													listAll(k_num);
												}
											}
							});
						}
				
					});
				 	//글 삭제를 위한 Ajax연동처리
					$(document).on("click", ".delete_btn", function(){
						var r_num =$(this).parents("li").attr("data-num");
						console.log("r_num:" + r_num);
						
						if(confirm("선택하신 댓글을 삭제하시겠습니까?")){
							$.ajax({
									type: 'delete',
									url : '/replies/'+ r_num+'.kbw',
									headers:{
										"Content-Type":"application/json",
										"X-HTTP-Method-Override":"DELETE"
									},
									dataType:'text',
									success:function(result){
										console.log("result:" + result);
										if(result=='SUCCESS'){
											alert("삭제되었습니다.");
											listAll(k_num);
										}
									}
							});
						}
					});
				 	//리스트 요청 함수
				 	function listAll(k_num){
				 		$("#comment_list").html("");
				 		var url = "/replies/all/"+ k_num+".kbw";
				 		$.getJSON(url,function(data){
				 				
				 				$(data).each(function(){
				 					var r_num = this.r_num;
				 					var r_name = this.r_name;
				 					var r_content = this.r_content;
				 					var r_date = this.r_date;
				 					addNewItem(r_num, r_name, r_content, r_date);
				 				});
				 		}).fail(function(){
				 			alert("댓글 목록을 불러오는데  실패하였습니다. 잠시후에 다시 시도해주세요.");
				 		});
				 	}
				 	
				 	//새로운 글을 화면에 추가하기 위한 함수 
				 	function addNewItem(r_num, r_name, r_content, r_date){
				 		
				 		var new_li =$("<li>");
				 		new_li.attr("data-num", r_num);
				 		new_li.addClass("comment_item");
				 		
				 		var writer_p = $("<p>");
				 		writer_p.addClass("writer");
				 		
				 		var name_span = $("<span>");
				 		name_span.addClass("name");
				 		name_span.html(r_name + "님");
				 		
				 		var date_span= $("<span>");
				 		date_span.html("/" + r_date+"");
				 		
				 		var up_input = $("<input>");
				 		up_input.attr({"type":"button", "value":"수정하기"});
				 		up_input.addClass("update_form");
				 		
				 		var del_input = $("<input>");
				 		del_input.attr({"type":"button","value":"삭제하기"});
				 		del_input.addClass("delete_btn");
				 		
				 		var content_p = $("<p>");
				 		content_p.addClass("con");
				 		content_p.html(r_content);
				 		
				 		writer_p.append(name_span).append(date_span).append(up_input).append(del_input)
				 		new_li.append(writer_p).append(content_p);
				 		$("#comment_list").append(new_li);
				 	}
				 	
				 	//INPUT 태그들에 대한 초기화 함수 
				 	function dataReset(){
				 		$("#r_name").val("");
				 		$("#r_pwd").val("");
				 		$("#r_content").val("");
				 	}
				});
		</script>
	</head>
	<body>
			<div id="replyContainer">
				<h1></h1>
				<div id="comment_write">
					<form id="comment_form">
						<div>
								<label for="r_name">작성자</label>
								<input type="text" name="r_name" id="r_name" />
								<label for="r_name">비밀번호</label>
								<input type="password" name="r_pwd" id="r_pwd" />
								<input type="button" id="replyInsert" value="저장하기">
						</div>
						<div>
								<label for="r_content">댓글내용</label>
								<textarea name="r_content" id="r_content" rows="" cols=""></textarea>
						</div>
					</form>
				</div>
				<ul id="comment_list">
						<!-- 여기에 동적 생성 요소가 들어가게 됩니다. -->
				</ul>
			</div>
	</body>
</html>