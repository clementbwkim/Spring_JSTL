package com.spring.reply.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.reply.service.ReplyService;
import com.spring.reply.vo.ReplyVO;

/*���� : @RestController >> @Controller + @ResponseBody
 * ������ Ư���� jsp�� ���� �並 ����� ���� ���� �ƴ� REST ����� ������ ó���� ���ؼ� ����ϴ�(������ ��ü�� ��ȯ) ������̼��̴�*/



@Controller
@RequestMapping(value="/replies")
public class ReplyController {
		Logger logger = Logger.getLogger(ReplyController.class);
		
		@Autowired
		private ReplyService replyService;
		
		/*��� ��� �����ϱ� 
		 * @return List<ReplyVO>
		 * ����: @PathVariable�� URI�� ��ο��� ���ϴ� �����͸� �����ϴ� �뵵�� ��� 
		 * ResponseEntity Ÿ���� �����ڰ� ���� ��� �����Ϳ� HTTP�� ���� �ڵ带 ����
		 * ������ �� �ִ� Ŭ�����̴�. 404�� 500 ���� �����ڵ带 �����ϰ� ���� �����Ϳ� �Բ� 
		 * ������ �� �ֱ� ������ ���� ������ ��� ����.
		 * */
		@ResponseBody
		@RequestMapping(value="/all/{k_num}.kbw", method={RequestMethod.GET,RequestMethod.POST})
		public ResponseEntity<List<ReplyVO>> list(@PathVariable("k_num") String k_num){
			logger.info("ReplyController list>>>>> ");
			ResponseEntity<List<ReplyVO>> entity = null;
			try{
				entity = new ResponseEntity<>(replyService.replyList(k_num), HttpStatus.OK);
			}catch(Exception e){
				e.printStackTrace();
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			return entity;
		}
		
		/*��� �۾��� �����ϱ�
		 * @return String
		 * ���� : @RequestBody*/
		@ResponseBody
		@RequestMapping(value="/replyInsert")
		public ResponseEntity<String> replyInsert(@RequestBody ReplyVO rvo){
			logger.info("ReplyController replyInsert>>>>> ");
			ResponseEntity<String> entity = null;
			int result;
			try{
				ReplyVO cb = replyService.chaebunReply();

				String c = cb.getR_num();
				
				if(1==c.length()){
					c="000" + c;
				}
				if(2==c.length()){
					c="00"+c;
				}
				if(3==c.length()){
					c="0"+c;
				}
				c="R"+c;
				
				rvo.setR_num(c);
				
					result = replyService.replyInsert(rvo);
					if(result==1){
						entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
					}
			}catch(Exception e){
				e.printStackTrace();
				entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return entity;
			
		}
		
		/*��� ���� �����ϱ� 
		 * @return 
		 * ���� : REST ��Ŀ��� UPDATE �۾��� PUT, PATCH ����� �̿��ؼ� ó��.
		 * ��ü �����͸� �����ϴ� ��쿡�� PUT�� �̿��ϰ�, 
		 * �Ϻ��� �����͸� �����ϴ� ��쿡�� PATCH�� �̿� 
		 * */
		@ResponseBody
		@RequestMapping(value="/{r_num}.kbw", method={RequestMethod.PUT,RequestMethod.PATCH})
		public ResponseEntity<String> replyUpdate(@PathVariable("r_num") String r_num, @RequestBody ReplyVO rvo){
			logger.info("ReplyController replyUpdate>>>>> ");
			ResponseEntity<String> entity = null;
			try{
				rvo.setR_num(r_num);
				replyService.replyUpdate(rvo);
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}catch(Exception e){
				e.printStackTrace();
				entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		
		/*��� ���� �����ϱ� 
		 * @return
		 * ����: REST��Ŀ��� DELETE �۾��� DELETE����� �̿��ؼ� ó��.
		 * */
		@ResponseBody
		@RequestMapping(value="/{r_num}.kbw", method=RequestMethod.DELETE)
		public ResponseEntity<String> replyDelete(@PathVariable("r_num")String r_num){
			logger.info("ReplyController replyDelete>>>>> ");
			ResponseEntity<String> entity = null;
			try{
				replyService.replyDelete(r_num);
				entity = new ResponseEntity<String>("SUCCESS" , HttpStatus.OK);
			}catch(Exception e){
				e.printStackTrace();
				entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
}
