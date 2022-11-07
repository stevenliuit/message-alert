package com.message.alert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 类上面的两个注解不能缺少
 *@RunWith(SpringRunner.class)
 *@SpringBootTest(classes = 启动类（引导类）.class)
 * 当此测试类所在的包与启动类所在的包：在同一级包下或是启动类所在包的子包
 *测试方法的注解不能缺少
 *@Test
 *直接注入UserService对象就能够实现测试接口的调用，记得加@Autowired。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlertApplication.class)
public class MainTesting {
 
    @Test
    public void initTest() {
        String str = "junit test";
        System.out.println(str);
    }
}