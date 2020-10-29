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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * springboot集成的定时任务注意4个地方
 * 1)@EnableScheduling
 * 2)@Lazy(false)
 * 3)public TaskScheduler taskScheduler() 这个类确保多线程并发执行
 * 4)@Scheduled(cron = "0 0/1 * * * ?")
 *
 * 这个类主要表达对老婆的爱
 */
@Component
@EnableScheduling
@Lazy(false)
public class LoveController {

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

    @Scheduled(cron = "0 0 8 * * ?")
    public void one1(){
        System.out.println("好好工作,每天好心情~");
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void one2(){
        System.out.println("我的心里一直有你哦~");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void one5(){
        System.out.println("中午好好休息哦老婆");
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void one3(){
        System.out.println("最爱你的老公~一生一世~");
    }

    @Scheduled(cron = "0 0 17 * * ?")
    public void one4(){
        System.out.println("宝贝,下班别忘了打卡哦~");
    }

    @Scheduled(cron = "0 15 17 * * ?")
    public void one6(){
        System.out.println(" __                                        \n" +
                "   / /   ____ _   _____     __  ______  __  __\n" +
                "  / /   / __ \\ | / / _ \\   / / / / __ \\/ / / /\n" +
                " / /___/ /_/ / |/ /  __/  / /_/ / /_/ / /_/ / \n" +
                "/_____/\\____/|___/\\___/   \\__, /\\____/\\__,_/  \n" +
                "                         /____/               \n" +
                "    ____                              \n" +
                "   / __/___  ________ _   _____  _____\n" +
                "  / /_/ __ \\/ ___/ _ \\ | / / _ \\/ ___/\n" +
                " / __/ /_/ / /  /  __/ |/ /  __/ /    \n" +
                "/_/  \\____/_/   \\___/|___/\\___/_/     \n" +
                "                                      ");
    }
}
