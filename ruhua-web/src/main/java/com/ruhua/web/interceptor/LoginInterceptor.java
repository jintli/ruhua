package com.ruhua.web.interceptor;

import com.ruhua.api.IWebContext;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.LoginAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * 已经登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


    /**
     * 登录页面的URL
     */
    private String loginUrl;
    @Resource
    private UserDao userDao;
    /**
     * 登录的页面URL  当未登录访问已登录的页面时，自动跳转到该页面
     * @param loginUrl
     */
    public void setLoginUrl(String loginUrl){
        this.loginUrl = loginUrl;
    }
    /**
     * 在Controller方法前进行拦截
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String strUrl = request.getRequestURI();
        //如果handler controller实现了接口，则设置上下文
        BaseInfo loginAccount = null;
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        if(((HandlerMethod) handler).getBean() instanceof IWebContext) {
            ((IWebContext)((HandlerMethod) handler).getBean()).setWebContext(request, response,this.loginUrl);
            loginAccount = ((IWebContext)((HandlerMethod) handler).getBean()).getLoginAccount();
        }
        Login login = method
                .getMethodAnnotation(Login.class);
        //不需要登录
        if(null == login ){
            return true;
        }
        if (loginAccount == null){
            String strToUrl = "index.html?tourl=" + URLEncoder.encode(strUrl, "utf-8");
            if ("GET".equalsIgnoreCase(request.getMethod())){
                response.sendRedirect(strToUrl);//转到登录页
            }else{
                //Json返回数据
                PrintWriter printWriter = null;
                try{
                    String strJson = "请登录";
                    response.setContentType("application/json");
                    printWriter = response.getWriter();
                    printWriter.print(strJson);
                    printWriter.flush();
                }
                finally{
                    if (printWriter != null){
                        printWriter.close();
                    }
                }
            }
            return false;
        } else {
            if(!userDao.login(loginAccount)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This implementation is empty.
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler != null && handler instanceof IWebContext
                && modelAndView != null && "GET".equalsIgnoreCase(request.getMethod())){
            //当get的时候，系统自动封闭loginAccount到modelAndView里
            modelAndView.addObject("loginAccount", ((IWebContext)handler).getLoginAccount());
        }
    }

    /**
     * 在Controller方法后进行拦截
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}