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

/*참고 : @RestController >> @Controller + @ResponseBody
 * 기존의 특정한 jsp와 같은 뷰를 만들어 내는 것이 아닌 REST 방식의 데이터 처리를 위해서 사용하는(데이터 자체를 반환) 어노테이션이다*/



@Controller
@RequestMapping(value="/replies")
public class ReplyController {
		Logger logger = Logger.getLogger(ReplyController.class);
		
		@Autowired
		private ReplyService replyService;
		
		/*댓글 목록 구현하기 
		 * @return List<ReplyVO>
		 * 참고: @PathVariable는 URI의 경로에서 원하는 데이터를 추출하는 용도로 사용 
		 * ResponseEntity 타임은 개발자가 직접 결과 데이터와 HTTP의 상태 코드를 직접
		 * 제어할 수 있는 클래스이다. 404나 500 같은 상태코드를 전송하고 싶은 데이터와 함께 
		 * 전송할 수 있기 때문에 좀더 세밀한 제어가 가능.
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
		
		/*댓글 글쓰기 구현하기
		 * @return String
		 * 참고 : @RequestBody*/
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
		
		/*댓글 수정 구현하기 
		 * @return 
		 * 참고 : REST 방식에서 UPDATE 작업은 PUT, PATCH 방식을 이용해서 처리.
		 * 전체 데이터를 수정하는 경우에는 PUT을 이용하고, 
		 * 일부의 데이터를 수정하는 경우에는 PATCH를 이용 
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
		
		/*댓글 삭제 구형하기 
		 * @return
		 * 참고: REST방식에서 DELETE 작업은 DELETE방식을 이용해서 처리.
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
