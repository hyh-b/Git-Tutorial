package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileTO {
	private String seq;
	private String pseq;
	private String latitude;
	private String longitude;
	private String filename;
	private long filesize;
	private String wdate;
}
