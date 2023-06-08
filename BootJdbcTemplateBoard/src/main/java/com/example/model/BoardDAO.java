package com.example.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ArrayList<BoardTO> boardList(){
		String sql = "select seq, subject, writer, date_format(wdate, '%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap from board1 order by seq desc";
		ArrayList<BoardTO> lists = (ArrayList<BoardTO>)jdbcTemplate.query(
				sql,
				new BeanPropertyRowMapper<BoardTO>(BoardTO.class));
		return lists;
	}
	
	public int boardWriteOk(BoardTO to) {
		int flag = 1;
		
		String sql = "insert into board1 values ( 0, ?, ?, ?, ?, ?, 0, ?, now() )";
		int result = jdbcTemplate.update(
				sql,
				to.getSubject(),to.getWriter(),to.getMail(),to.getPassword(),to.getContent(),to.getWip());
		
		if(result==1) {
			flag=0;
		}
		
		return flag;
	}
	
	public BoardTO boardView(BoardTO to) {
		int result = jdbcTemplate.update(
				"update board1 set hit=hit+1 where seq=?",
				to.getSeq());
		
		to = jdbcTemplate.queryForObject(
				"select seq, subject, writer, mail, wip, wdate, hit, content from board1 where seq=?"
				, new BeanPropertyRowMapper<BoardTO>(BoardTO.class),
				to.getSeq());
		
		return to;
	}
	
	public BoardTO boardDelete(BoardTO to) {
		to = (BoardTO) jdbcTemplate.queryForObject(
				"select seq, subject, writer from board1 where seq=?",
				new BeanPropertyRowMapper<BoardTO>(BoardTO.class),
				to.getSeq());
		return to;
	}
	
	public BoardTO boardModify(BoardTO to) {
		to = (BoardTO) jdbcTemplate.queryForObject(
				"select seq, subject, writer, mail, content from board1 where seq=?",
				new BeanPropertyRowMapper<BoardTO>(BoardTO.class),
				to.getSeq());
		return to;
	}
	
	public int boardDeleteOk(BoardTO to) {
		int flag =2;
		int result = jdbcTemplate.update("delete from board1 where seq=? and password=?",
				to.getSeq(), to.getPassword()
				);
				if(result ==1) {
					flag = 0;
				}else if(result==0){
					flag=1;
				}
				return flag;
	}
	
	public int boardModifyOk(BoardTO to) {
		int flag =2;
		System.out.println("modify to : "+to);
		int result = jdbcTemplate.update("update board1 set subject=?, mail=?, content=? where seq=? and password=?",
				to.getSubject(), to.getMail(),to.getContent() ,to.getSeq(), to.getPassword()
				);
				if(result ==1) {
					flag = 0;
				}else if(result==0){
					flag=1;
				}
				System.out.println("modify to2 : "+to);
				return flag;
	}
}
