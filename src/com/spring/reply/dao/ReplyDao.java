package com.spring.reply.dao;

import java.util.List;

import com.spring.reply.vo.ReplyVO;

public interface ReplyDao {

	public List<ReplyVO> replyList(String k_num);
	public int replyInsert(ReplyVO rvo);
	public int replyUpdate(ReplyVO rvo);
	public int replyDelete(String r_num);
	public ReplyVO chaebunReply();
}
