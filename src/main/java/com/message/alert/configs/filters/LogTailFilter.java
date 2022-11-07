package com.message.alert.configs.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogTailFilter implements Filter {

    private final Logger logger =  LoggerFactory.getLogger(LogTailFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(requestWrapper,responseWrapper);

        logger.info(new StringBuilder(100)
                .append("请求url:")
                .append(requestWrapper.getRequestURI())
                .append("--请求body:")
                .append(new String(requestWrapper.getContentAsByteArray()))
                .toString());

        logger.info(new StringBuilder(70)
                .append("请求url:")
                .append(requestWrapper.getRequestURI())
                .append("--响应body:")
                .append(new String(responseWrapper.getContentAsByteArray()))
                .toString());
        responseWrapper.copyBodyToResponse();
    }

    @Override
    public void destroy() {

    }
}
