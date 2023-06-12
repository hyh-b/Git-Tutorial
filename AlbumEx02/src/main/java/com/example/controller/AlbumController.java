package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.BoardDAO;
import com.example.model.BoardTO;
import com.example.model.CmtDAO;
import com.example.model.CmtTO;
import com.example.model.FileDAO;
import com.example.model.FileTO;

@RestController
public class AlbumController {
	@Autowired
	private BoardDAO bDao;
	@Autowired
	private FileDAO fDao;
	@Autowired
	private CmtDAO cDao;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request) {
		ArrayList<BoardTO> lists = bDao.boardList();
		ArrayList<FileTO> flists = fDao.fileList();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_list1");
		modelAndView.addObject("lists",lists);
		modelAndView.addObject("flists",flists);
		
		return modelAndView;
	}
	@RequestMapping("/write.do")
	public ModelAndView write(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_write1");
		
		return modelAndView;
	}
	
	@RequestMapping("/view.do")
	public ModelAndView view(HttpServletRequest request) {
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		
		to = bDao.boardView(to);
		
		FileTO to2 = new FileTO();
		to2.setPseq(request.getParameter("seq"));
		
		List<FileTO> fileTOList = fDao.fileView(to2);
		
		CmtTO cto = new CmtTO();
		cto.setPseq(request.getParameter("seq"));
		
		ArrayList<CmtTO> clists = cDao.cmtViewList(cto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_view1");
		modelAndView.addObject("to",to);
		modelAndView.addObject("to2",to2);
		modelAndView.addObject("to3",fileTOList);
		modelAndView.addObject("clists",clists);
		return modelAndView;
	}
	
	@RequestMapping("/delete.do")
	public ModelAndView delete(HttpServletRequest request) {
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		
		to = bDao.boardDelete(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_delete1");
		modelAndView.addObject("to",to);
		return modelAndView;
	}
	
	@RequestMapping("/write_ok.do")
	public ModelAndView write_ok(HttpServletRequest request, MultipartFile[] upload) {
		BoardTO to = new BoardTO();
		to.setSubject( request.getParameter( "subject" ) );
		to.setWriter( request.getParameter( "writer" ) );
		String mail="";
		if( !request.getParameter("mail1").equals("") && !request.getParameter("mail2").equals("") ) {
			mail = request.getParameter( "mail1" ) + "@" + request.getParameter( "mail2" ) ;	
		}
		to.setMail( mail);
		to.setPassword(request.getParameter("password"));
		to.setContent( request.getParameter( "content" ) );
		to.setCmtyes(request.getParameter("mail"));
		to.setWip( request.getRemoteAddr() );
		
		int flag = bDao.boardWriteOk(to);
		
		//String pseq = bDao.maxseq();
		
		FileTO to2 = new FileTO();
	
		
		try {
			for(MultipartFile up : upload) {
				String extension = up.getOriginalFilename().substring(up.getOriginalFilename().lastIndexOf("."));
				String filename = up.getOriginalFilename().substring(0,up.getOriginalFilename().lastIndexOf("."));
				
				long timestamp = System.currentTimeMillis();
				String newfilename = filename + "-"+timestamp + extension;
				up.transferTo(new File(newfilename));
				
				to2.setPseq(to.getSeq());
				to2.setLatitude(request.getParameter("latitude"));
				to2.setLongitude(request.getParameter("longitude"));
				to2.setFilename(newfilename);
				to2.setFilesize(up.getSize());
				int flag2 = fDao.fileWriteOk(to2);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_write1_ok");
		modelAndView.addObject("flag",flag);
		//modelAndView.addObject("flag2",flag2);
		return modelAndView;
	}
	
	@RequestMapping("/delete_ok.do")
	public ModelAndView delete_ok(HttpServletRequest request) {
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		to.setPassword(request.getParameter("password"));
		
		FileTO to2 = new FileTO();
		to2.setPseq(request.getParameter("seq"));
		
		CmtTO to3 = new CmtTO();
		to3.setPseq(request.getParameter("seq"));
		
		int flag = bDao.boardDeleteOk(to);
		int flag2 = fDao.fileDeleteOk(to2);
		int flag3 = cDao.cmt_boardDeleteOk(to3);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_delete1_ok");
		modelAndView.addObject("flag",flag);
		modelAndView.addObject("flag2",flag2);
		modelAndView.addObject("flag3",flag3);
		return modelAndView;
	}
	
	@RequestMapping("/cwrite_ok.do")
	public ModelAndView cwrite_ok(HttpServletRequest request) {
		CmtTO to = new CmtTO();
		to.setPseq(request.getParameter("seq"));
		to.setWriter(request.getParameter("cwriter"));
		to.setPassword(request.getParameter("cpassword"));
		to.setContent(request.getParameter("ccontent"));
		//System.out.println(request.getParameter("mail"));
		int cflag = cDao.cmtWriteOk(to);
		
		String toEmail = request.getParameter("mail");
		String toName = "이름";
		String subject = "제목2";
		String content = "내용2";
		System.out.println("cmtyes :"+request.getParameter("cmtyes"));
		if(request.getParameter("cmtyes")!=null && !request.getParameter("cmtyes").isEmpty()) {
		this.mailSender1(toEmail, toName, subject, content);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cmt_write_ok");
		modelAndView.addObject("cflag",cflag);
		
		return modelAndView;
	}
	
	@RequestMapping("/cdelete.do")
	public ModelAndView cdelete(HttpServletRequest request) {
		CmtTO to = new CmtTO();
		to.setSeq(request.getParameter("cseq"));
		to.setPassword(request.getParameter("cpassword1"));
		to.setPseq(request.getParameter("seq"));
		int flag = cDao.cmtDeleteOk(to);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cmt_delete_ok");
		modelAndView.addObject("flag",flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/modify.do")
	public ModelAndView modify(HttpServletRequest request) {
		BoardTO to = new BoardTO();
		to.setSeq(request.getParameter("seq"));
		FileTO to2 = new FileTO();
		to2.setPseq(request.getParameter("seq"));
		
		to = bDao.boardModify(to);
		List<FileTO> flists = fDao.fileView(to2);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_modify1");
		modelAndView.addObject("to",to);
		modelAndView.addObject("flists",flists);
		
		return modelAndView;
	}
	
	@RequestMapping("/modify_ok.do")
	public ModelAndView modify_ok(HttpServletRequest request, MultipartFile[] upload) {
		BoardTO to = new BoardTO();
		
		
		to.setSeq(request.getParameter("seq"));
		to.setWriter(request.getParameter("writer"));
		to.setSubject( request.getParameter( "subject" ) );
		to.setMail( "" ) ;
		if( !request.getParameter("mail1").equals("") 
				&& !request.getParameter("mail2").equals("") ) {
			to.setMail( request.getParameter( "mail1" ) + "@" + request.getParameter( "mail2" ) );	
		}
		to.setPassword( request.getParameter( "password" ) );
		to.setContent( request.getParameter( "content" ) );
		
		int flag = bDao.boardModifyOk(to);
		System.out.println("파일1: "+upload);
		System.out.println("파일 개수1: "+upload.length);
		if(upload !=null && upload.length > 0 && !upload[0].isEmpty()) {
			FileTO to2 = new FileTO();
			to2.setPseq(request.getParameter("seq"));
			int flag3 = fDao.fileDeleteOk(to2);
			for(MultipartFile up : upload) {
				String extension = up.getOriginalFilename().substring(up.getOriginalFilename().lastIndexOf("."));
				String filename = up.getOriginalFilename().substring(0,up.getOriginalFilename().lastIndexOf("."));
				
				long timestamp = System.currentTimeMillis();
				String newfilename = filename + "-"+timestamp + extension;
				
				try {
					up.transferTo(new File(newfilename));
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			to2.setPseq(to.getSeq());
			to2.setLatitude(request.getParameter("latitude"));
			to2.setLongitude(request.getParameter("longitude"));
			to2.setFilename(newfilename);
			to2.setFilesize(up.getSize());
			int flag2 = fDao.fileWriteOk(to2);
			
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board_modify1_ok");
		modelAndView.addObject("to",to);
		modelAndView.addObject("flag",flag);
		return modelAndView;
	}
	
	public void mailSender1(String toEmail,String toName, String subject, String content) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		simpleMailMessage.setSentDate(new Date());
		
		javaMailSender.send(simpleMailMessage);
		
		System.out.println("전송 완료");
	}
	
	
	
}
