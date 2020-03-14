package com.spring.reply.service;

import java.util.List;

import com.spring.reply.vo.ReplyVO;

public interface ReplyService {
	public List<ReplyVO> replyList(String k_num);
	public int replyInsert(ReplyVO rvo);
	public int replyUpdate(ReplyVO rvo);
	public int replyDelete(String r_num);
	public ReplyVO chaebunReply();

}
