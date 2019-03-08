package com.wulis.jsoup.common.webInterceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AssetInterceptor implements HandlerInterceptor {

    @Override
    //在请求处理之前进行调用(Controller方法调用之前)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;    //如果false，停止流程，api被拦截
    }

    @Override
    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        if (!StringUtils.isEmpty(modelAndView)) {
            String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/";
            modelAndView.addObject("basePath", basePath);
        }
    }

    @Override
    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
