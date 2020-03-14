package com.spring.board.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.service.BoardService;
import com.spring.board.vo.BoardVO;
import com.spring.common.file.FileUploadUtil;
import com.spring.common.page.Paging;
import com.spring.common.util.Util;

@Controller
@RequestMapping(value="/board")
public class BoardController {
		private static final String CONTEXT_PATH = "board";
		Logger logger = Logger.getLogger(BoardController.class);
		
		@Autowired
		private BoardService boardService;
		
		@RequestMapping(value="/boardList", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView boardList(@ModelAttribute BoardVO bvo){
			logger.info("boardList ȣ�� ����");
			//���� �⺻�� ����
			if(bvo.getOrder_by()==null) bvo.setOrder_by("k_num");
			if(bvo.getOrder_sc()==null) bvo.setOrder_sc("DESC");
			
			//���� ������ Ȯ��
			logger.info("order_by =" + bvo.getOrder_by());
			logger.info("order_sc =" + bvo.getOrder_sc());
			
			//�˻� ������ Ȯ��
			logger.info("search =" + bvo.getSearch());
			logger.info("keyword =" + bvo.getKeyword());
			
			//������ ��
			Paging.setPage(bvo);
			
			//��ü ���ڵ� �� ����
			int total = boardService.boardListCnt(bvo);
			logger.info("total = " + total);
			
			//�۹�ȣ �缳�� 
			int count = total -(Util.nvl(bvo.getPage())-1)*Util.nvl(bvo.getPageSize());
			logger.info("cnt = " + count);

			
			List<BoardVO> boardList = boardService.boardList(bvo);
			System.out.println("list >>> : " + boardList);
			System.out.println("list.toSring >>> : " + boardList.toString());
			
			ModelAndView mav = new ModelAndView();
			
			mav.addObject("boardList", boardList);

			mav.addObject("count", count);
			mav.addObject("total", total);
			mav.addObject("data", bvo);
			
			mav.setViewName(CONTEXT_PATH + "/boardList");
			return mav;
			
		}//end of boardList
		
		@RequestMapping(value="/writeForm")
		public String writeForm(){
			logger.info("writeForm ȣ�� ����");
			return "board/writeForm";
		}//end of writeForm
		
		@RequestMapping(value="/boardInsert", method={RequestMethod.POST,RequestMethod.GET})
		public ModelAndView boardInsert(@ModelAttribute BoardVO bvo, HttpServletRequest req) {
			logger.info("boardInsert ȣ�� ����");
			logger.info("fileName : " + bvo.getFile().getOriginalFilename());
			logger.info("k_title : " + bvo.getK_title());
			
			ModelAndView mav = new ModelAndView();

			String k_file ="";
			try {
				k_file = FileUploadUtil.fileUpload(bvo.getFile(), req);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("k_file.toString()" + k_file.toString());
			bvo.setK_file(k_file);
			
			BoardVO cb = boardService.chaebunBoard();

			String c = cb.getK_num();
			
			if(1==c.length()){
				c="000" + c;
			}
			if(2==c.length()){
				c="00"+c;
			}
			if(3==c.length()){
				c="0"+c;
			}
			c="B"+c;
			
			bvo.setK_num(c);
			System.out.println("VO >>> : " + bvo.toString());

			int result = boardService.boardInsert(bvo);
			System.out.println("result >>> : " + result);
			
			if(result == 1){
				
				List<BoardVO> list = boardService.boardList(bvo);
				System.out.println("listSize >>> : " + list.size());
				System.out.println("list >>> : " + list);
				
				mav.addObject("boardList", list);
				mav.setViewName(CONTEXT_PATH + "/boardList");
				
			}else{
				mav.setViewName(CONTEXT_PATH + "/board");
			}
			
			return mav;
			
		}//end of boardInsert

		@RequestMapping(value="/boardDetail")
		public ModelAndView boardDetail(@ModelAttribute BoardVO bvo){

			logger.info("boardDetail ȣ�� ����");
			logger.info("k_num : "+ bvo.getK_num());
			
			BoardVO detail = new BoardVO();
			detail = boardService.boardDetail(bvo);
			System.out.println("detail >>> : " + detail);
			
			if(detail !=null && (detail.equals(""))){
				detail.setK_content(detail.getK_content().toString().replaceAll("\n", "<br>"));
			}
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("detail", detail);
			mav.setViewName(CONTEXT_PATH + "/boardDetail");
			return mav;
					
					
			
		}//end of boardDetail
		
		/*
		 * ��й�ȣ Ȯ��
		 * @param k_num 
		 * @param k_pwd
		 * @return int 
		 * ���� : @ResponseBody�� ���޵� �並 ���ؼ� ����ϴ� ���� �ƴ϶� 
		 * HTTP Response Body�� ���� ����ϴ� ���
		 */
		@ResponseBody
		@RequestMapping(value="/pwdConfirm")
		public String pwdConfirm(@ModelAttribute BoardVO bvo){
			logger.info("pwdConfirm ȣ�� ����");
			//�Ʒ� �������� �Է� ������ ���� ���°� ���� (1 or 0)
			int result = boardService.pwdConfirm(bvo);
			System.out.println("result >>> : " + result);
			
			logger.info("result : " + result);
			return result+"";
					
		}//end of pwdConfirm.
		
		@RequestMapping(value="/updateForm", method=RequestMethod.POST)
		public ModelAndView updateForm(@ModelAttribute BoardVO bvo){
			logger.info("updateForm ȣ�� ����");
			BoardVO updateData = new BoardVO();
			updateData = boardService.boardDetail(bvo);
			System.out.println("updateData >>> : " + updateData);
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("updatedata", updateData);
			mav.setViewName(CONTEXT_PATH +"/updateForm");
			return mav;

		}//end of updateForm.

		@RequestMapping(value="/boardUpdate")
		public ModelAndView boardUpdate(@ModelAttribute BoardVO bvo, HttpServletRequest req) throws IllegalStateException, IOException{
			logger.info("boardUpdate ȣ�� ����");
			
			int result = 0;
			String k_file = "";
			ModelAndView mav = new ModelAndView();

			
			if(!bvo.getFile().isEmpty()){
				FileUploadUtil.fileDelete(bvo.getK_file(), req);
				k_file = FileUploadUtil.fileUpload(bvo.getFile(), req);
				System.out.println("k_file >>> : " + k_file);
				bvo.setK_file(k_file);
			}else{
				logger.info("÷������ ����");
				bvo.setK_file("");
			}
			logger.info("k_file : " + bvo.getK_file());
			result = boardService.boardUpdate(bvo);
			System.out.println("result >>> : " + result);
			
			
			if(result == 1){
				
				List<BoardVO> list = boardService.boardList(bvo);
				System.out.println("list >>> : " + list);
				mav.addObject("list", list);
				mav.setViewName("redirect:/board/boardList.kbw");
				//mav.setViewName(CONTEXT_PATH + "/boardList");
				
				//url = "/board/boardList.kbw";
				//System.out.println("url >>> :" + url); //���� �� ������� �̵� 
				//�Ʒ� url�� ���� �� �� �������� �̵� 
				//url="/board/boardDetail.kbw? k_num"+bvo.getK_num();
				
			}
			return mav;
		}//end of boardUpdate.

		@RequestMapping(value="/boardDelete")
		public ModelAndView boardDelete(@ModelAttribute BoardVO bvo, HttpServletRequest req) throws IOException{
			logger.info("boardDelete ȣ�� ����");

			ModelAndView mav = new ModelAndView();
			
			FileUploadUtil.fileDelete(bvo.getK_file(), req);
			int result = boardService.boardDelete(bvo.getK_num());
			System.out.println("result >>> : " + result);
			
			if(result == 1){
				List<BoardVO> list = boardService.boardList(bvo);
				System.out.println("list >>> : " + list);
				
				mav.addObject("boardList", list);
				mav.setViewName(CONTEXT_PATH + "/boardList");
				//mav.setViewName("redirect:/board/boardList.kbw");

			}
			return mav;
		}
		
		@RequestMapping(value="/chaebunBoard")
		public ModelAndView chaebunBoard(@RequestParam("k_num") String k_num){
			logger.info("chaebunBoard ȣ�� ����");
			ModelAndView mav = new ModelAndView();
			
			if(k_num.equals("")){
				mav.addObject("mode", "chaebun");
			}else{
				mav.addObject("BoardVO", boardService.chaebunBoard());
				mav.addObject("mode", "chaebun");
			}
				mav.setViewName(CONTEXT_PATH + "/boardList");
			
			return mav;
			
		}

}
