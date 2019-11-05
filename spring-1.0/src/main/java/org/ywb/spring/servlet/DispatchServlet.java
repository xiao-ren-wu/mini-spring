package org.ywb.spring.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/1 7:50
 * @since 1.8
 */
public class DispatchServlet extends HttpServlet {

    private Properties p = new Properties();

    private Map<String,Object> beanMap = new ConcurrentHashMap<>();

    private List<String> beanName = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 定位
        doLoadConfig();
        //加载
        doScanner();
        // 自动依赖注入
        doResource();
        // 如果是SpringMVC会有handlerMapping

        // 将@RequestMapping中配置的URL和method关联上
        initHandlerMapping();
    }

    private void initHandlerMapping() {

    }

    private void doResource() {

    }

    private void doScanner() {

    }

    private void doLoadConfig() {
        
    }
}
