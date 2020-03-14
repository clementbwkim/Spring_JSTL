package com.spring.board.vo;

import org.springframework.web.multipart.MultipartFile;

import com.spring.common.vo.CommonVO;

public class BoardVO extends CommonVO {
		
	private String k_num;
	private String k_name; 
	private String k_title;
	private String k_content;
	private String k_date;
	private String k_pwd;

	private MultipartFile file; //첨부파일
	private String k_file; //실제서버에 저장한 파일명
	
	
	
	@Override
	public String toString() {
		return "BoardVO [k_num=" + k_num + ", k_name=" + k_name + ", k_title=" + k_title + ", k_content=" + k_content
				+ ", k_date=" + k_date + ", k_pwd=" + k_pwd + ", file=" + file + ", k_file=" + k_file + "]";
	}
	
	
	
	public String getK_num() {
		return k_num;
	}
	public void setK_num(String k_num) {
		this.k_num = k_num;
	}
	public String getK_name() {
		return k_name;
	}
	public void setK_name(String k_name) {
		this.k_name = k_name;
	}
	public String getK_title() {
		return k_title;
	}
	public void setK_title(String k_title) {
		this.k_title = k_title;
	}
	public String getK_content() {
		return k_content;
	}
	public void setK_content(String k_content) {
		this.k_content = k_content;
	}
	public String getK_date() {
		return k_date;
	}
	public void setK_date(String k_date) {
		this.k_date = k_date;
	}
	public String getK_pwd() {
		return k_pwd;
	}
	public void setK_pwd(String k_pwd) {
		this.k_pwd = k_pwd;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getK_file() {
		return k_file;
	}
	public void setK_file(String k_file) {
		this.k_file = k_file;
	}
	
	
	
}
