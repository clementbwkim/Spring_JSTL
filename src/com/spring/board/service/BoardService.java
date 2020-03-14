package com.spring.board.service;

import java.util.List;

import com.spring.board.vo.BoardVO;

public interface BoardService {
	public List<BoardVO> boardList(BoardVO bvo);
	public int boardInsert(BoardVO bvo);
	public BoardVO boardDetail(BoardVO bvo);
	public int pwdConfirm(BoardVO bvo);
	public int boardUpdate(BoardVO bvo);
	public int boardDelete(String k_num);
	public BoardVO chaebunBoard();
	public int boardListCnt(BoardVO bvo);

	
}
