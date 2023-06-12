package com.example.model;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int boardWriteOk(BoardTO to) {
		int flag = 1;
	    
	    String sql = "INSERT INTO album_cmt_board2 (seq,subject, writer, mail, password, content,cmt, cmtyes, hit,wip, wdate) " +
	                 "VALUES ( 0, ?, ?, ?, ?, ?,0,?, 0, ?, now())";
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    int result = jdbcTemplate.update(con -> {
	        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, to.getSubject());
	        ps.setString(2, to.getWriter());
	        ps.setString(3, to.getMail());
	        ps.setString(4, to.getPassword());
	        ps.setString(5, to.getContent());
	        ps.setString(6, to.getCmtyes());
	        ps.setString(7, to.getWip());
	        return ps;
	    }, keyHolder);

	    if (result == 1) {
	        flag = 0;
	    }
	    
	    // Retrieve the generated key (seq) from KeyHolder
	    if (keyHolder.getKey() != null) {
	        to.setSeq(keyHolder.getKey().toString());
	    }
	    
	    return flag;
	}
	
	public ArrayList<BoardTO> boardList(){
		ArrayList<BoardTO> lists = (ArrayList<BoardTO>)jdbcTemplate.query(
				"select seq, subject, writer, content, date_format(wdate, '%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap, cmtyes, cmt from album_cmt_board2 order by seq desc"
				, new BeanPropertyRowMapper<BoardTO>(BoardTO.class));
		
		return lists;
	}
	
	public BoardTO boardView(BoardTO to) {
		int result = jdbcTemplate.update(
				"update album_cmt_board2 set hit=hit+1 where seq=?",
				to.getSeq());
		
		to = jdbcTemplate.queryForObject(
				"select seq, subject, writer, mail, wip, wdate, hit, content, cmt, cmtyes from album_cmt_board2 where seq=?"
				, new BeanPropertyRowMapper<BoardTO>(BoardTO.class),
				to.getSeq());
		return to;
	}
	
	public BoardTO boardDelete(BoardTO to) {
		to = (BoardTO) jdbcTemplate.queryForObject(
				"select seq, subject, writer from album_cmt_board2 where seq=?",
				new BeanPropertyRowMapper<BoardTO>(BoardTO.class),
				to.getSeq());
		return to;
	}
	
	public int boardDeleteOk(BoardTO to) {
		int flag =2;
		int result = jdbcTemplate.update("delete from album_cmt_board2 where seq=? and password=?",
				to.getSeq(), to.getPassword()
				);
				if(result ==1) {
					flag = 0;
				}else if(result==0){
					flag=1;
				}
				return flag;
	}
	
	public BoardTO boardModify(BoardTO to) {
		to = (BoardTO) jdbcTemplate.queryForObject(
				"select seq, subject, writer, content, mail from album_cmt_board2 where seq=?", 
				new BeanPropertyRowMapper<BoardTO>(BoardTO.class),to.getSeq());
		return to;
	}
	
	public int boardModifyOk(BoardTO to) {
		int flag =2;
		int result = jdbcTemplate.update("update album_cmt_board2 set writer=?, subject=?, mail=?, content=? where seq=? and password=?",
				to.getWriter(),to.getSubject(), to.getMail(),to.getContent() ,to.getSeq(), to.getPassword()
				);
				if(result ==1) {
					flag = 0;
				}else if(result==0){
					flag=1;
				}
		return flag;
	}
}
