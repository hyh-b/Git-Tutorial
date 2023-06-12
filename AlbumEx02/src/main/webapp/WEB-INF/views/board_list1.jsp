<%@page import="com.example.model.FileTO"%>
<%@page import="com.example.model.BoardTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<BoardTO> lists = (ArrayList)request.getAttribute("lists");
	ArrayList<FileTO> flists = (ArrayList)request.getAttribute("flists");

	int totalRecord = lists.size();
	
	StringBuilder sbHtml = new StringBuilder();
	
	for( BoardTO albumTO : lists ) {
		String seq = albumTO.getSeq();
		String subject = albumTO.getSubject();
		String writer = albumTO.getWriter();
		String cmtCnt = albumTO.getCmt();
		String wdate = albumTO.getWdate();
		String hit = albumTO.getHit();
		int wgap = albumTO.getWgap();
		
		String imageName = null;
		long imageSize = 0;
		
		for( FileTO imageTO : flists ) {
			if( imageTO.getPseq().equals( seq ) ) {
				imageName = "./upload/" + imageTO.getFilename();
				imageSize = imageTO.getFilesize();
			}
		}
		
		sbHtml.append( "<tr>" );
		sbHtml.append( "	<td class='last2'>" );
		sbHtml.append( "		<div class='board'>" );
		sbHtml.append( "			<table class='boardT'>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td class='boardThumbWrap'>" );
		sbHtml.append( "					<div class='boardThumb'>" );
		sbHtml.append( "						<a href='view.do?seq=" + seq + "'><img src='" + imageName + "' border='0' width='200' /></a>" );
		sbHtml.append( "					</div>" );
		sbHtml.append( "				</td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td>" );
		sbHtml.append( "					<div class='boardItem'>" );
		sbHtml.append( "						<strong>" + subject +"</strong>" );
		sbHtml.append( "						<span class='coment_number'><img src='./images/icon_comment.png' alt='commnet'>" + cmtCnt + "</span>" );
		if( wgap <= 1 ) {
			sbHtml.append( "						<img src='./images/icon_new.gif' alt='NEW'>") ;
		}
		sbHtml.append( "					</div>" );
		sbHtml.append( "				</td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td><div class='boardItem'><span class='bold_blue'>" + writer + "</span></div></td>" );
		sbHtml.append( "			</tr>" );
		sbHtml.append( "			<tr>" );
		sbHtml.append( "				<td>" );
		sbHtml.append( "					<div class='boardItem'>" + wdate + " <font>|</font> Hit " + hit + "</div>" );
		sbHtml.append( "				</td>" );
		sbHtml.append( "			</tr>") ;
		sbHtml.append( "		</table>" );
		sbHtml.append( "	</div>" );
		sbHtml.append( "</td>" );
		sbHtml.append( "</tr>" );
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/board_list.css">
<style type="text/css">
<!--
	.board_pagetab { text-align: center; }
	.board_pagetab a { text-decoration: none; font: 12px verdana; color: #000; padding: 0 3px 0 3px; }
	.board_pagetab a:hover { text-decoration: underline; background-color:#f2f2f2; }
	.on a { font-weight: bold; }
-->
</style>
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
		<div class="board_top">
			<div class="bold">
				<p>총 <span class="txt_orange"><%=totalRecord %></span>건</p>
			</div>
		</div>	
		
		<!--게시판-->
		<table class="board_list">
		<%=sbHtml %>
		</table>
		
		<div class="btn_area">
			<div class="align_right">		
				<input type="button" value="쓰기" class="btn_write btn_txt01" style="cursor: pointer;" onclick="location.href='write.do'" />
			</div>
			
			<!--페이지넘버-->
			<div class="paginate_regular">
				<div class="board_pagetab">
					<span class="off"><a href="#">&lt;&lt;</a>&nbsp;&nbsp;</span>
					<span class="off"><a href="#">&lt;</a>&nbsp;&nbsp;</span>
					<span class="off"><a href="#">[ 1 ]</a></span>
					<span class="on"><a href="#">[ 2 ]</a></span>
					<span class="off"><a href="#">[ 3 ]</a></span>
					<span class="off">&nbsp;&nbsp;<a href="#">&gt;</a></span>
					<span class="off">&nbsp;&nbsp;<a href="#">&gt;&gt;</a></span>
				</div>
			</div>
			<!--//페이지넘버-->	
		</div>
		<!--//게시판-->
  	</div>
</div>
<!--//하단 디자인 -->

</body>
</html>
