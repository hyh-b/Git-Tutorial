<%@page import="com.example.model.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	int flag = (Integer)request.getAttribute("flag");	
	BoardTO to = (BoardTO)request.getAttribute("to");
	System.out.println("to데이터 : "+to);
	String seq = to.getSeq();

	out.println( "<script type='text/javascript'>" );
	if( flag == 0 ) {
		out.println( "alert('글수정에 성공');" );
		out.println( "location.href='view.do?seq=" + to.getSeq() + "';" );
	} else if( flag == 1 ) {
		out.println( "alert('비밀번호 오류');" );
		out.println( "history.back();" );
	} else {
		out.println( "alert('글수정에 실패');" );
		out.println( "history.back();" );
	}
	out.println( "</script>" );
%>
