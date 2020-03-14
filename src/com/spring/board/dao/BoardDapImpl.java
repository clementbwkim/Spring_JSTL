package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.vo.BoardVO;


@Repository
public class BoardDapImpl implements BoardDao {
		@Autowired 
		private SqlSession session;
		
		
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		return session.selectList("boardList");
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		return session.insert("boardInsert");
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		return (BoardVO)session.selectOne("boardDetail");
	}

	@Override
	public int pwdConfirm(BoardVO bvo) {
		return (Integer)session.selectOne("pwdConfirm");
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		return session.update("boardUpdate");
	}

	@Override
	public int boardDelete(String k_num) {
		return session.delete("boardDelete", k_num);
	}

	@Override
	public BoardVO chaebunBoard() {
		return session.selectOne("chaebunBoard");
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		return (int)session.selectOne("boardListCnt");
	}

}
