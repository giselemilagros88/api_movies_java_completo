package com.ar.cac.api;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método init, puedes dejarlo vacío si no necesitas inicialización específica
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Permitir acceso desde cualquier origen
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // Métodos permitidos
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Cabeceras permitidas
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Preflight (OPTIONS) cache timeout (10 minutes)
        httpResponse.setHeader("Access-Control-Max-Age", "600");

        // Si es una solicitud OPTIONS, simplemente termina la solicitud
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continúa con el siguiente filtro en la cadena
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Método destroy, puedes dejarlo vacío si no necesitas limpieza específica
    }
}