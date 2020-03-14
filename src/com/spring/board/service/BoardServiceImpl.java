package com.spring.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVO;
import com.sun.istack.internal.logging.Logger;



@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	Logger logger = Logger.getLogger(BoardServiceImpl.class);

	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl boardList>>>>>> ");
		return boardDao.boardList(bvo);
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl boardInsert>>>>>> ");
		return boardDao.boardInsert(bvo);
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl boardDetail>>>>>> ");
		return boardDao.boardDetail(bvo);
	}

	@Override
	public int pwdConfirm(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl pwdConfirm>>>>>> ");
		return boardDao.pwdConfirm(bvo);
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl boardUpdate>>>>>> ");
		return boardDao.boardUpdate(bvo);
	}

	@Override
	public int boardDelete(String k_num) {
		logger.info("(LOG)BoardServiceImpl boardDelete>>>>>> ");
		return boardDao.boardDelete(k_num);
	}

	@Override
	public BoardVO chaebunBoard() {
		logger.info("(LOG)BoardServiceImpl chaebunBoard>>>>>> ");
		return boardDao.chaebunBoard();
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		logger.info("(LOG)BoardServiceImpl boardListCnt>>>>>> ");

		return boardDao.boardListCnt(bvo);
	}
	
	
	
}
