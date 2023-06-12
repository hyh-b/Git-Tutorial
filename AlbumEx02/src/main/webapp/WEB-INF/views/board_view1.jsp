<%@page import="com.example.model.CmtTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.example.model.FileTO"%>
<%@page import="com.example.model.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BoardTO to =(BoardTO)request.getAttribute("to");
	FileTO to2 = (FileTO)request.getAttribute("to2");
	List<FileTO> flists = (List)request.getAttribute("to3");
	ArrayList<CmtTO> clists = (ArrayList)request.getAttribute("clists");
	System.out.println("댓글 수 : "+ clists.size()); 
	String seq = to.getSeq();
	String subject = to.getSubject();
	String writer = to.getWriter();
	String wdate = to.getWdate();
	String wip = to.getWip();
	String hit = to.getHit();
	String content = to.getContent().replaceAll( "\n", "<br />" );
	String mail = to.getMail();
	String cmtyes = to.getCmtyes();
	String longitude = "";
	String latitude = "";
	FileTO first = flists.get(0);
	longitude = first.getLongitude();
	latitude = first.getLatitude();
	
	StringBuilder sbHtml = new StringBuilder();
	
	for(FileTO to3 : flists){
		String filename = to3.getFilename();
		
		sbHtml.append("<img src='../../upload/"+filename+"' width='900' onerror='' />");
		sbHtml.append("<br />");
		
	}
	StringBuilder cmtHtml = new StringBuilder();
	for(CmtTO cto : clists){
		String cwriter = cto.getWriter();
		String ccontent = cto.getContent();
		String cwdate = cto.getWdate();
		String cseq = cto.getSeq();
		
		cmtHtml.append("<tr>");
		cmtHtml.append("<td class='coment_re' >");
		cmtHtml.append("<strong>"+cwriter+"</strong> ("+cwdate+")");
		cmtHtml.append("<div class='coment_re_txt'>");
		cmtHtml.append(ccontent);
		cmtHtml.append("</div>");
		cmtHtml.append("</td>");
		cmtHtml.append("<form action='cdelete.do?cseq="+cseq+"' method='post' name='cdfrm'>");
		cmtHtml.append("<input type='hidden' name='seq' value="+seq+">");
		cmtHtml.append("<td class='coment_re' width='20%' align='right'>");
		cmtHtml.append("<input type='password' name='cpassword1' placeholder='비밀번호' class='coment_input pR10' />");
		cmtHtml.append("<input type='submit' id='cdbtn' value='삭제' class='btn_comment btn_txt02' style='cursor: pointer;' />");
		cmtHtml.append("</td>");
		cmtHtml.append("</form>");
		cmtHtml.append("</tr>");
	}
	
	
	
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/board_view.css">
<script type="text/javascript">
	window.onload = function(){
		document.getElementById('cbtn').onclick = function(){
			//alert('click');
			
			if(document.cfrm.cwriter.value.trim()==''){
				alert('글쓴이를 입력하셔야 합니다');
				return false;
			}
			
			if(document.cfrm.cpassword.value.trim()==''){
				alert('비밀번호를 입력하셔야 합니다');
				return false;
			}
			if(document.cfrm.ccontent.value.trim()==''){
				alert("내용을 입력하셔야 합니다");
				
				return false;
			}
			
			document.cfrm.submit();
		}
		
	}
	
	/* window.onload = function(){
		document.getElementById('cdbtn').onclick = function(){
			//alert('click');
			
			if(document.cdfrm.cpassword1.value.trim()==''){
				alert('비밀번호를 입력하셔야 합니다');
				return false;
			}
			
			
			document.cdfrm.submit();
		}
		
	} */
	
	
</script>
</head>

<body>
<!-- 상단 디자인 -->
<div class="contents1"> 
	<div class="con_title"> 
		<p style="margin: 0px; text-align: right">
			<img style="vertical-align: middle" alt="" src="../../images/home_icon.gif" /> &gt; 커뮤니티 &gt; <strong>여행지리뷰</strong>
		</p>
	</div>

	<div class="contents_sub">	
	<!--게시판-->
		<div class="board_view">
			<table>
			<tr>
				<th width="10%">제목</th>
				<td width="60%"><%=subject %>(<%=wip %>)</td>
				<th width="10%">등록일</th>
				<td width="20%"><%=wdate %></td>
			</tr>
			<tr>
				<th>글쓴이</th>
				<td><%=writer %></td>
				<th>조회</th>
				<td><%=hit %></td>
			</tr>
			<tr>
				<th>위치정보</th>
				<td>
					위도 : (<%=latitude %>) / 경도 : (<%=longitude %>)
					<input type="button" value="지도보기" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='https://www.google.com/maps/@37.50449,127.048860,17z'" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<td colspan="4" height="200" valign="top" style="padding:20px; line-height:160%">
					<div id="bbs_file_wrap">
						<div>
							<%-- <img src="../../upload/<%=filename %>" width="900" onerror="" /><br /> --%>
							<%=sbHtml %>
						</div>
					</div>
					<%=content %>
				</td>
			</tr>			
			</table>
			
			<table>
			<!-- <tr>
				<td class="coment_re" >
					<strong>54545</strong> (2016.09.19 02:00)
					<div class="coment_re_txt">
						Test
					</div>
				</td>
				<td class="coment_re" width="20%" align="right">
					<input type="password" name="cpassword1" placeholder="비밀번호" class="coment_input pR10" />
					<input type="button" value="삭제" class="btn_comment btn_txt02" style="cursor: pointer;" />
				</td>
			</tr>
			<tr>
				<td class="coment_re">
					<strong>하오리</strong> (2016.09.19 07:54)
					<div class="coment_re_txt">
						우리는 민족 중흥의 역사적 사명을 띄고 이 땅에 태어났다. 조상의 빛난 얼을 오늘에 되살려
					</div>
				</td>
				<td class="coment_re" width="20%" align="right">
					<input type="password" name="cpassword2" placeholder="비밀번호" class="coment_input pR10" />
					<input type="button" value="삭제" class="btn_comment btn_txt02" style="cursor: pointer;" />
				</td>
			</tr> -->
			<%=cmtHtml %>
			</table>
			<form action="cwrite_ok.do" method="post" name="cfrm">
			<input type="hidden" name="seq" value=<%=seq %>>
			<input type="hidden" name="mail" value=<%=mail %>>
			<input type="hidden" name="cmtyes" value=<%=cmtyes %>>
			
			<table>
			<tr>
				<td width="94%" class="coment_re">
					글쓴이 <input type="text" name="cwriter" maxlength="5" class="coment_input" />&nbsp;&nbsp;
					비밀번호 <input type="password" name="cpassword" class="coment_input pR10" />&nbsp;&nbsp;
				</td>
				<td width="6%" class="bg01"></td>
			</tr>
			<tr>
				<td class="bg01">
					<textarea name="ccontent" cols="" rows="" class="coment_input_text"></textarea>
				</td>
				<td align="right" class="bg01">
					<input type="button" id="cbtn" value="댓글등록" class="btn_re btn_txt01" />
				</td>
			</tr>
			</table>
			</form>
		</div>
		<div class="btn_area">
			<div class="align_left">			
				<input type="button" value="목록" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='list.do'" />
			</div>
			<div class="align_right">
				<input type="button" value="수정" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='modify.do?seq=<%=seq %>'" />
				<input type="button" value="삭제" class="btn_list btn_txt02" style="cursor: pointer;" onclick="location.href='delete.do?seq=<%=seq %>'" />
				<input type="button" value="쓰기" class="btn_write btn_txt01" style="cursor: pointer;" onclick="location.href='write.do'" />
			</div>	
		</div>
		<!--//게시판-->
	</div>
<!-- 하단 디자인 -->
</div>

</body>
</html>
