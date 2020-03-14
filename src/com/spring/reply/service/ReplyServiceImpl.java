package com.spring.reply.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.reply.dao.ReplyDao;
import com.spring.reply.vo.ReplyVO;


@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
			Logger logger = Logger.getLogger("ReplyServiceImpl.class");
			
			@Autowired
			private ReplyDao replyDao;

			@Override
			public List<ReplyVO> replyList(String k_num) {
				logger.info("ReplyServiceImpl replyList >>>>>>>");
				List<ReplyVO> myList = replyDao.replyList(k_num);
				return myList;
			}

			@Override
			public int replyInsert(ReplyVO rvo) {
				logger.info("ReplyServiceImpl replyInsert >>>>>>>");
				int result = replyDao.replyInsert(rvo);
				return result;
			}

			@Override
			public int replyUpdate(ReplyVO rvo) {
				logger.info("ReplyServiceImpl replyUpdate >>>>>>>");
				int result = replyDao.replyUpdate(rvo);
				return result;
			}

			@Override
			public int replyDelete(String r_num) {
				logger.info("ReplyServiceImpl replyDelete >>>>>>>");
				int result = replyDao.replyDelete(r_num);
				return result;
			}

			@Override
			public ReplyVO chaebunReply() {
				logger.info("ReplyServiceImpl chaebunReply >>>>>>>");
				return replyDao.chaebunReply();
			}
			




	
	

}
