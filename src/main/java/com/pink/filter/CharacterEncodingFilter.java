package com.pink.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf16");
        response.setCharacterEncoding("utf16");
        chain.doFilter(request,response);
    }

    public void destroy() {
    }
}
