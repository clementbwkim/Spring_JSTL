package com.spring.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.reply.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	
	@Autowired 
	private SqlSession session;
	
	@Override
	public List<ReplyVO> replyList(String k_num) {
		return session.selectList("replyList", k_num);
	}

	@Override
	public int replyInsert(ReplyVO rvo) {
		return session.insert("replyInsert");
	}

	@Override
	public int replyUpdate(ReplyVO rvo) {
		return session.update("replyUpate");
	}

	@Override
	public int replyDelete(String r_num) {
		return session.delete("replyDelete", r_num);
	}

	@Override
	public ReplyVO chaebunReply() {
		return session.selectOne("chaebunReply");
	}
	
	

}
