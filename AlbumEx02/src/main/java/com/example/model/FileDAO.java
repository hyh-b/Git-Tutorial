package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int fileWriteOk(FileTO to) {
		int flag2 = 1;
		
		String sql = "insert into album_cmt_file2 values ( 0, ?, ?, ?, ?, ?, now() )";
		int result = jdbcTemplate.update(
				sql,
				to.getPseq(),to.getFilename(),to.getFilesize(),to.getLatitude(),to.getLongitude());
		
		if(result==1) {
			flag2=0;
		}
		
		return flag2;
	}
	
	public int fileModifyOk(FileTO to) {
		int flag = 2;
		
		int result = jdbcTemplate.update(
				"update album_cmt_file2 set filename=?, filesize=?, latitude=?, longitude=? where pseq=?",
				to.getFilename(),to.getFilesize(),to.getLatitude(),to.getLongitude(),to.getPseq());
		
		if(result ==1) {
			flag = 0;
		}else if(result==0){
			flag=1;
		}
		return flag;
	}
	
	public ArrayList<FileTO> fileList(){
		ArrayList<FileTO> flists = (ArrayList<FileTO>)jdbcTemplate.query(
				"select seq, pseq, filename, filesize from album_cmt_file2 where seq in ( select max(seq) from album_cmt_file2 group by pseq )"
				, new BeanPropertyRowMapper<FileTO>(FileTO.class));
		
		return flists;
	}
	
	public List<FileTO> fileView(FileTO to) {
		List<FileTO> files = jdbcTemplate.query(
				"select seq, pseq, filename, filesize, latitude, longitude from album_cmt_file2 where pseq=?", 
				new BeanPropertyRowMapper<FileTO>(FileTO.class),to.getPseq());
		return files;
	}
	
	public int fileDeleteOk(FileTO to) {
		int flag2 = 2;
		int result = jdbcTemplate.update("delete from album_cmt_file2 where pseq=? ",
				to.getPseq()
				);
				if(result ==1) {
					flag2 = 0;
				}else if(result==0){
					flag2=1;
				}
		return flag2;
	}
	
	
}
