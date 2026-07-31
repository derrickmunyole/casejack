package com.example.casemanagementplatform.common.tenant;

import com.example.casemanagementplatform.cases.CaseService;

/**
 * Holds the tenant identifier for the current request thread.
 * <p>
 * Set by {@link TenantFilter} at the start of each request and read
 * by {@link CaseService} (and any future tenant-scoped code) to resolve
 * "which tenant is this operation for" without threading a tenantId
 * parameter through every method call.
 * <p>
 * Callers must ensure {@link #clear()} runs after each request — servlet
 * containers reuse threads across requests, so a value left set here
 * would silently leak into whatever unrelated request reuses the same
 * thread next.
 */
public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    /**
     * Sets the tenant id for the current thread.
     *
     * @param tenantId the tenant identifier to associate with this thread
     */
    public static void setTenantId(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    /**
     * Returns the tenant id set for the current thread, or {@code null}
     * if none has been set (e.g. this code is running outside a request
     * that went through {@link TenantFilter}).
     *
     * @return the current tenant id, or {@code null} if unset
     */
    public static String getTenantId() {
        return CURRENT_TENANT.get();
    }

    /**
     * Clears the tenant id for the current thread. Must be called after
     * each request to prevent leaking a tenant id into a later request
     * that reuses the same thread.
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
