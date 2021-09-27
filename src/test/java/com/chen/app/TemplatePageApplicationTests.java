package com.chen.app;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class TemplatePageApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private Configuration configuration;

    @Test
    public void testTemplate() {
        //构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");//模板文件后缀
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //构造上下文(Model)
        Context context = new Context();
        context.setVariable("name", "蔬菜列表");
        context.setVariable("array", new String[]{"土豆", "番茄", "白菜", "芹菜"});


        //渲染模板
        FileWriter write = null;
        try {
            write = new FileWriter("d:\\test\\result.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateEngine.process("example", context, write);
    }

    @Test
    public void createPage(){
        //获取模板名字
        String templateName = "fpage.html";

        //文件生存的路径
        String path = "d:\\test";

        //文件路径如果不存在，则创建
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        //获取文件名字
        String fileName = "page1.html";

        //获取模板对象
        Template template = null;
        try {
            template = configuration.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //模板处理，获取生成的文件字符串
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("name", "蔬菜列表");
        dataMap.put("array", new String[]{"土豆", "番茄", "白菜", "芹菜"});
        String content = null;
        try {
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        //生成文件
        try {
            FileUtils.writeStringToFile(new File(path,fileName),content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
