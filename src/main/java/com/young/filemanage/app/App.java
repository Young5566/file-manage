package com.young.filemanage.app;

//import org.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/15 16:19
 */

@ComponentScan(basePackages = "com.young.filemanage")

//此处用 tk.mybatis.. ，tk用于SQLProvider
@MapperScan(basePackages = "com.young.filemanage.mapper")
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
