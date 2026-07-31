package com.example.casemanagementplatform.common.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Implements Springboot's OncePerRequestFilter to ensure the filter
 * for the tenant id happens exactly once per HTTP request
 */

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "x-tenant-ID";

    /**
     * Resolves the tenant id from the {@code X-Tenant-ID} header and sets it in
     * {@link TenantContext} for the duration of the request. Requests missing
     * the header are rejected immediately with a 400 response rather than
     * proceeding with no tenant context.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the remaining filter chain to continue if a tenant id is present
     * @throws ServletException if the underlying filter chain throws one
     * @throws IOException if writing the error response fails
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tenantId = request.getHeader(TENANT_HEADER);

        // check for the absence of a tenant id and return an error message if missing
        if (tenantId == null || tenantId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Missing required header 'x-tenant-id'\"}");
            return;
        }

        /* set the tenant-id for the request scope and clear
         it at the end to prevent leakage in subsequent requests */
        try {
            TenantContext.setTenantId(tenantId);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }

    }
}
