package com.example.webflux.threadpool;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class DecorateContext {
    private final SecurityContext securityContext;
    private SecurityContext originalSecurityContext;

    private final RequestAttributes delegateRequestAttributes;

    DecorateContext(){
        this.securityContext = SecurityContextHolder.getContext();
        this.delegateRequestAttributes = this.cloneCurrentRequestAttributes();
    }

    protected void decorate(){
        this.originalSecurityContext = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(securityContext);

    }

    protected void recoverAndClear(){
        if(originalSecurityContext == null){
            SecurityContextHolder.clearContext();
        }else {
            SecurityContextHolder.setContext(originalSecurityContext);
        }
        RequestContextHolder.resetRequestAttributes();
    }

    private RequestAttributes cloneCurrentRequestAttributes() {
        RequestAttributes clonedRequestAttribute;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            if(requestAttributes == null || !(requestAttributes instanceof ServletRequestAttributes)){
                return null;
            }

            DecorateHttpServletRequest request = new DecorateHttpServletRequest(((ServletRequestAttributes) requestAttributes).getRequest());
            clonedRequestAttribute = new ServletRequestAttributes(request, ((ServletRequestAttributes) requestAttributes).getResponse());
            return clonedRequestAttribute;
        } catch (Exception e) {
            return null;
        }
    }
}
