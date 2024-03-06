package com.fsociety.authapi.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String requestURI = request.getRequestURI();
    String remoteAddr = request.getRemoteAddr();
    String method = request.getMethod();
    log.info("Request from {} to {} with method {}", remoteAddr, requestURI, method);
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
