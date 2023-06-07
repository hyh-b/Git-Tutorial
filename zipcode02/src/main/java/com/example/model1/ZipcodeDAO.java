package com.example.model1;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class ZipcodeDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ArrayList<ZipcodeTO> zipcodeList(String strDong){
		ArrayList<ZipcodeTO> lists =(ArrayList<ZipcodeTO>)jdbcTemplate.query(
				"select * from zipcode where dong like ?", 
				new BeanPropertyRowMapper<ZipcodeTO>(ZipcodeTO.class),strDong + "%");
		
		return lists;
	}
}
