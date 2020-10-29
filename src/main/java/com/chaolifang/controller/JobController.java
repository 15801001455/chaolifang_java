package com.chaolifang.controller;

import com.chaolifang.pojo.Book;
import com.chaolifang.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Lazy(false)
public class JobController {

    @Autowired
    private BookService bookService;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * update jyc 必须加这个Bean 否则EnableScheduling是单线程的,会串行化阻塞
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }

    //@Scheduled(cron = "0 0 8 * * ?") //正式环境 每天8点发送
    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendMailDelayReturnBook(){
        List<Book> unReturnedBook = bookService.getUnReturnedBook();
        if(unReturnedBook != null && unReturnedBook.size() > 0){
            for(Book book : unReturnedBook){
                // update jyc 先不发邮件了 让老婆在控制台能看到谁没有还书就行了!!!也不用想方设法隐藏我发邮件的用户名和密码了
                System.out.println(book.getBorrowPerson());
                //sendMailTemp();
            }
        }
    }

    /**
     * 这里需要真实的用户名密码 想想怎么隐藏先 要不传到github上就暴露了
     * spring.mail.password
     * spring.mail.host          smtp.126.com
     * spring.mail.username      15801001455
     * spring.mail.properties.mail.smtp.auth  true
     * spring.mail.properties.mail.smtp.starttls.enable  true
     * spring.mail.properties.mail.smtp.starttls.required  true
     */
    public void sendMailTemp(String env,String toEmail,String[] cc,String subject){
        String text = "";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("15801001455@126.com");
            helper.setTo(toEmail);
            helper.setCc(cc);
            helper.setSubject(subject);
            text = "";
            helper.setText(text,true);
            javaMailSender.send(message);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Scheduled(cron = "0 0/1 * * * ?")
    public void test1(){//测试串行化单线程 前提是没有配置taskScheduler这个Bean的时候
        String curName = Thread.currentThread().getName() ;
        System.out.println("当前时间:"+LocalDateTime.now()+"  任务execute2对应的线程名: "+curName);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void test2(){//测试串行化单线程 前提是没有配置taskScheduler这个Bean的时候
        String curName = Thread.currentThread().getName() ;
        System.out.println("当前时间:"+LocalDateTime.now()+"  任务execute2对应的线程名: "+curName);
    }*/
}