package com.user.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.user.entity.User;
import com.user.service.UserService;
import com.user.util.MailUtils;


@Controller                                     
@Scope("prototype")                
@RequestMapping("/user")
public class UserController{
 
    //注入userService
    @Autowired
    private UserService userService;

     //跳转到用户登录页面
    @RequestMapping(value="/loginpage.do")
    public String loginpage() { 
        return "user/login";
    }
    
    //用户登录
    @RequestMapping(value="/userlogin.do",method=RequestMethod.POST)
    public ModelAndView login(String userName,String password,String verifyCode,ModelAndView mv,HttpSession session,HttpServletRequest request) {
        User user=userService.login(userName, password);
        //获取用户输入的校验码并进行比较
        String sessionVerifyCode = (String) session.getAttribute("verifyCodeValue");
        if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {         
        	 mv.addObject("message", "验证码错误!");
        	 mv.setViewName("/user/login");
        }else {
        	if(user!=null){
                //登录成功，将user对象设置到HttpSession作用范围域中
            	if(user.getState()==1) {
            		session.setAttribute("user", user);          
                    mv.setViewName("index");
            	}else if(user.getState()==0){
            		//登录失败，设置失败信息，并调转到登录页面       
                    mv.addObject("message","该用户账号未激活！") ;
                    System.out.println("该用户账号未激活！");
                    //登录失败跳转页面
                    mv.setViewName("/user/login");
            	}else {
                //登录失败，设置失败信息，并调转到登录页面       
                mv.addObject("message","用户名或密码错误，请重新输入") ;
                //登录失败跳转页面
                mv.setViewName("/user/login");
               }
     	    }
        }
        return mv;
    }

     //跳转到用户注册页面
    @RequestMapping(value="/registerpage.do")
    public String registerpage() { 
        return "user/register";
    }
    
    //检验用户名是否可以使用
    @RequestMapping(value="/findUserName.do")
    public void findUserName(String userName,HttpServletResponse response) throws IOException{      
       String username=userService.findUserName(userName);
       boolean flag=false;
       if(username==null) {
    	   System.out.print("用户名未被注册!");
    	   flag=false;   	 
       }else {
    	   System.out.print("用户名已被注册!");
    	   flag=true;
       }
       if(true == flag){  
           response.getWriter().write("true");   
       }else{
           response.getWriter().write("false");
       }     
    }
    
    //检验邮箱是否可以使用
    @RequestMapping(value="/findMail.do")
    public void findMail(String mailAddress,HttpServletResponse response) throws IOException{ 
       String mail=userService.findMail(mailAddress);
       boolean flag=false;
       if(mail==null) {
    	   System.out.print("邮箱未被注册!");
    	   flag=false;   	 
       }else {
    	   System.out.print("邮箱已被注册!");
    	   flag=true;
       }
       if(true == flag){  
           response.getWriter().write("true");   
       }else{
           response.getWriter().write("false");
       }
    }
    
   //用户注册
    @RequestMapping(value="/userregister.do",method=RequestMethod.POST)
    public ModelAndView register(User user,ModelAndView mv){
    	System.out.println(user);
        String userName=user.getUserName();
        
        String toMail=user.getMailAddress();
        String code = UUID.randomUUID().toString();
        user.setCode(code);
        System.out.println(user);

        /*Date currentTime= new Date();//获取注册用户时系统当前时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
    	System.out.println(sdf.format(currentTime));*/
    	String username=userService.findUserName(userName);
    	String mailAddress=toMail;
    	String mail=userService.findMail(mailAddress);
        // 如果数据库中没有该用户，可以注册，否则跳转页面
        if (username== null) {
        	if(mail== null) {
        		// 添加用户	
                userService.register(user);
                try {
    				MailUtils.secdMail(toMail, code);//发送邮件
    				System.out.printf("发送邮件成功");
    				mv.addObject("message", "注册成功!");
               	    mv.setViewName("/user/login");
    			} catch (MessagingException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                // 注册成功跳转到主页面
        	}else {
        		// 注册失败跳转到错误页面
            	mv.addObject("message", "注册失败，邮箱已被注册!");
           	    mv.setViewName("/user/register");
        	} 
        }else {
            // 注册失败跳转到错误页面
        	mv.addObject("message", "注册失败，用户名已被注册!");
       	    mv.setViewName("/user/register");
        } 	
        return mv;
    }
    
    //激活账号
	@RequestMapping(value="/activation.do")
    public String activation(@Param("code")String code) {
    	System.out.println("code="+code);
    	User user=userService.findByUserCode(code);
    	System.out.println("user="+user);
        if(user!=null) {
        	if(user.getState()==0) {
        		userService.updateStateByCode(code); 
        		System.out.println("账号激活成功，请登录！");
              	return "user/login";
        	}else {
        		System.out.println("账号已激活，请登录！");
        		return "user/login";
        	}
         }else {  
           	System.out.println("该账号不存在，请注册！");
           	return "user/register";
          }   
	}
	
	//跳转到找回密码页面
    @RequestMapping(value="/repswpage.do")
    public String repspage() { 
        return "user/updatepassword";
    }
    //邮箱发送验证码
    @RequestMapping(value="/repswmail.do")
    public void repswmail(String mailAddress,HttpSession session) { 
    	String toMail=mailAddress;
    	String authcode="";
    	Random random=new Random();
        for(int i=0;i<4;i++) {
            int n=random.nextInt(4);
            authcode+=n;
        }
        session.setAttribute("authcode", authcode);
        System.out.print("Sessionauthcode1="+authcode);
    	try {	
			MailUtils.rePasswordMail(toMail,authcode);//发送邮件					
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    }

    //重置密码
    @RequestMapping(value="/updatepassword.do")
    public ModelAndView updatepassword(String userName,String password,String authcode,ModelAndView mv,HttpServletRequest request) { 
    	User user=userService.findByUserName(userName);
    	HttpSession session=request.getSession();
    	String acode=(String) session.getAttribute("authcode");
    	System.out.print("Sessionauthcode2="+acode);
    	if(authcode.equals(acode)) {
    		if(user!=null) {
        		userService.updatePassword(userName, password);
        		mv.addObject("message","密码修改成功！") ;
                mv.setViewName("/user/login");
        	}else {
        		mv.addObject("message","用户名错误或用户名不存在！") ;
                mv.setViewName("/user/updatepassword");
        	}
    	}else {
    		mv.addObject("message","验证码错误！") ;
            mv.setViewName("/user/updatepassword");
    	}	
        return mv;
    }
    
	//退出账号
	@RequestMapping(value="/loginout.do")
    public String loginout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);		
		if(session==null){			
			return "";		
		}		
		session.removeAttribute("user");				
		return "user/login";
	}
	
	/* 获取校验码 */
    @RequestMapping(value="/getVerifyCode.do")
    public void generate(HttpServletResponse response, HttpSession session) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String verifyCodeValue = drawImg(output);
        // 将校验码保存到session中
        session.setAttribute("verifyCodeValue", verifyCodeValue);
        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {    
            e.printStackTrace();

        }
    }

    /* 绘制验证码 */
    private String drawImg(ByteArrayOutputStream output) {
        String code = "";
        // 随机产生4个字符
        for (int i = 0; i < 4; i++) {
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        // 调用Graphics2D绘画验证码
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /* 获取随机参数 */
    private char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
}