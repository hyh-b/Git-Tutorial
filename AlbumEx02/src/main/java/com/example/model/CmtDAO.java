package com.example.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CmtDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ArrayList<CmtTO> cmtViewList(CmtTO to){
		ArrayList<CmtTO> clists = (ArrayList<CmtTO>)jdbcTemplate.query(
				"select seq, pseq, writer, content, wdate from album_cmt_comment2 where pseq=?",
				new BeanPropertyRowMapper<CmtTO>(CmtTO.class),to.getPseq());
		return clists;
	}
	
	public int cmtWriteOk(CmtTO to) {
		int cflag = 1;
		
		int result = jdbcTemplate.update(
				"insert into album_cmt_comment2 values (0,?,?,?,?,now());",
				to.getPseq(),to.getWriter(),to.getPassword(),to.getContent());
		if(result ==1) {
			cflag = 0;
		}
		
		int result2 = jdbcTemplate.update(
				"update album_cmt_board2 set cmt=cmt+1 where seq=?",
				to.getPseq());
		return cflag;
	}
	
	
	
	public int cmtDeleteOk(CmtTO to) {
		int flag = 2;
		int result = jdbcTemplate.update(
				"delete from album_cmt_comment2 where seq=? and password=?",
				to.getSeq(),to.getPassword());
		if(result ==1) {
			flag = 0;
		}else if(result==0){
			flag=1;
		}
		int result2 = jdbcTemplate.update(
				"update album_cmt_board2 set cmt=cmt-1 where seq=?",
				to.getPseq());
		return flag;
	}
	
	public int cmt_boardDeleteOk(CmtTO to) {
		int flag = 2;
		int result = jdbcTemplate.update(
				"delete from album_cmt_comment2 where pseq=?",
				to.getPseq());
		if(result ==1) {
			flag = 0;
		}else if(result==0){
			flag=1;
		}
		return flag;
	}
	
	
}
