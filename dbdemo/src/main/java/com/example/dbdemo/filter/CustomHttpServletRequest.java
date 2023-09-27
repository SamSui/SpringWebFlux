package com.example.dbdemo.filter;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ensureCachedBody();
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // Create a reader from cachedContent
        // and return it
        ensureCachedBody();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    private void ensureCachedBody() throws IOException {
        if (this.cachedBody == null) {
            InputStream requestInputStream = getRequest().getInputStream();
            this.cachedBody = IOUtils.toByteArray(requestInputStream);
        }
    }
}
