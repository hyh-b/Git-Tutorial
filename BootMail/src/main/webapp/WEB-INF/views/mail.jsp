<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/board.css">
</head>
<body>

<form action="mail_ok.do" method="post">



<div class="contents_sub">	
			<!--게시판-->
			<div class="board_write">
				<table>
				<tr>
					<th class="top">글쓴이</th>
					<td class="top"><input type="text" name="name" value="" class="board_view_input_mail" maxlength="5" /></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="subject" value="" class="board_view_input" /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="content" class="board_editor_area"></textarea></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="text" name="email" value="" class="board_view_input_mail"/></td>
				</tr>
				</table>
				
				<table>
				<tr>
					<br />
					<td style="text-align:left;border:1px solid #e0e0e0;background-color:f9f9f9;padding:5px">
						<div style="padding-top:7px;padding-bottom:5px;font-weight:bold;padding-left:7px;font-family: Gulim,Tahoma,verdana;">※ 개인정보 수집 및 이용에 관한 안내</div>
						<div style="padding-left:10px;">
							<div style="width:97%;height:95px;font-size:11px;letter-spacing: -0.1em;border:1px solid #c5c5c5;background-color:#fff;padding-left:14px;padding-top:7px;">
								1. 수집 개인정보 항목 : 회사명, 담당자명, 메일 주소, 전화번호, 홈페이지 주소, 팩스번호, 주소 <br />
								2. 개인정보의 수집 및 이용목적 : 제휴신청에 따른 본인확인 및 원활한 의사소통 경로 확보 <br />
								3. 개인정보의 이용기간 : 모든 검토가 완료된 후 3개월간 이용자의 조회를 위하여 보관하며, 이후 해당정보를 지체 없이 파기합니다. <br />
								4. 그 밖의 사항은 개인정보취급방침을 준수합니다.
							</div>
						</div>
						<div style="padding-top:7px;padding-left:5px;padding-bottom:7px;font-family: Gulim,Tahoma,verdana;">
							<input type="checkbox" name="info" value="1" class="input_radio"> 개인정보 수집 및 이용에 대해 동의합니다.
						</div>
					</td>
				</tr>
				</table>
			</div>
			
			<div class="btn_area">
				
				<div class="align_right">
					<input type="submit" value="전송" class="btn_write btn_txt01" style="cursor: pointer;">
				</div>
			</div>
			<!--//게시판-->
		</div>
		</div>
</form>
</body>
</html>