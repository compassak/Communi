import com.comm.entity.Message;
import com.comm.entity.Session;
import com.comm.services.MessageService;
import com.comm.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.User;
import com.user.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class MsgExTest {

    private static ApplicationContext context;

    @BeforeClass
    public static void setUP(){
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void insertMessageTest(){
        MessageService messageService = (MessageService) context.getBean("messageService");
        SessionService sessionService = (SessionService) context.getBean("sessionService");
        Date date = new Date();
        messageService.insertMessage(new Message(3, 1, "那你去物管啊", "text", false, date));
        sessionService.updateOrInsertSession(new Session(3,1,date));
        List<Session> sessions= sessionService.selectUserSession(3);
        for (Session session : sessions){
            System.out.println(session);
        }
    }

    @Test
    public void selectRecentSessionTest() throws IOException {
        Message message = new Message(3, 1, "那你去物管啊", "text", false, new Date());
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(message));
        String jsonStr = "{\"chatUserId\":1,\"text\":\"那你去物管啊\",\"type\":\"text\",\"flag\":true,\"time\":1587031196251}";
        System.out.println(mapper.readValue(jsonStr, Message.class));
    }

    @Test
    public void controllerTest(){
        User user = new User();
        user.setId(3);
        MessageService messageService = (MessageService) context.getBean("messageService");
        SessionService sessionService = (SessionService) context.getBean("sessionService");
        UserService userService = (UserService) context.getBean("userServiceImpl");

        List<Session> sessions = sessionService.selectUserSession(user.getId());
        LinkedHashMap<User,Integer> userMsg = new LinkedHashMap<>();
        int unReadNum;
        for (Session ses : sessions){
            if(ses.getUserId1() != user.getId()){
                unReadNum = messageService.selectUnreadMsgEx(ses.getUserId1(), user.getId()).size();
                User user1 = userService.findByUserId(ses.getUserId1());
                userMsg.put(user1, unReadNum);
            }else {
                unReadNum = messageService.selectUnreadMsgEx(ses.getUserId2(), user.getId()).size();
                User user1 = userService.findByUserId(ses.getUserId2());
                userMsg.put(user1, unReadNum);
            }
        }
        System.out.println(userMsg);
    }
}
