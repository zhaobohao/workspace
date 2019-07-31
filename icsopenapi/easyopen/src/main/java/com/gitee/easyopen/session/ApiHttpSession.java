package com.gitee.easyopen.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;

import org.springframework.util.Assert;

/**
 * @author tanghc
 */
@SuppressWarnings("deprecation")
public class ApiHttpSession implements HttpSession, Serializable {
    private static final long serialVersionUID = 946272038219216222L;

    private final String id;

    private final long creationTime = System.currentTimeMillis();

    private int maxInactiveInterval;

    private long lastAccessedTime = System.currentTimeMillis();

    private final ServletContext servletContext;

    private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

    private boolean invalid = false;

    private boolean isNew = true;

    /**
     * Create a new ApiHttpSession
     */
    public ApiHttpSession() {
        this(null);
    }

    /**
     * Create a new ApiHttpSession.
     * 
     * @param servletContext
     *            the ServletContext that the session runs in
     */
    public ApiHttpSession(ServletContext servletContext) {
        this(servletContext, null);
    }

    /**
     * Create a new ApiHttpSession.
     * 
     * @param servletContext
     *            the ServletContext that the session runs in
     * @param id
     *            a unique identifier for this session
     */
    public ApiHttpSession(ServletContext servletContext, String id) {
        this.servletContext = servletContext;
        this.id = this.buildId(id);
    }

    protected String buildId(String id) {
        return (id != null ? id : UUID.randomUUID().toString().replace("-", "").toUpperCase());
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void access() {
        this.lastAccessedTime = System.currentTimeMillis();
        this.isNew = false;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("getSessionContext");
    }

    @Override
    public Object getAttribute(String name) {
        Assert.notNull(name, "Attribute name must not be null");
        return this.attributes.get(name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return new Vector<String>(this.attributes.keySet()).elements();
    }

    @Override
    public String[] getValueNames() {
        return this.attributes.keySet().toArray(new String[this.attributes.size()]);
    }

    @Override
    public void setAttribute(String name, Object value) {
        Assert.notNull(name, "Attribute name must not be null");
        if (value != null) {
            this.attributes.put(name, value);
            if (value instanceof HttpSessionBindingListener) {
                ((HttpSessionBindingListener) value).valueBound(new HttpSessionBindingEvent(this, name, value));
            }
        } else {
            removeAttribute(name);
        }
    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        Assert.notNull(name, "Attribute name must not be null");
        Object value = this.attributes.remove(name);
        if (value instanceof HttpSessionBindingListener) {
            ((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
        }
    }

    @Override
    public void removeValue(String name) {
        removeAttribute(name);
    }

    /**
     * Clear all of this session's attributes.
     */
    public void clearAttributes() {
        for (Iterator<Map.Entry<String, Object>> it = this.attributes.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();
            String name = entry.getKey();
            Object value = entry.getValue();
            it.remove();
            if (value instanceof HttpSessionBindingListener) {
                ((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
            }
        }
    }

    @Override
    public void invalidate() {
        this.invalid = true;
        clearAttributes();
    }

    public boolean isInvalid() {
        return this.invalid;
    }

    public void setNew(boolean value) {
        this.isNew = value;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    /**
     * Serialize the attributes of this session into an object that can be
     * turned into a byte array with standard Java serialization.
     * 
     * @return a representation of this session's serialized state
     */
    public Serializable serializeState() {
        HashMap<String, Serializable> state = new HashMap<String, Serializable>();
        for (Iterator<Map.Entry<String, Object>> it = this.attributes.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();
            String name = entry.getKey();
            Object value = entry.getValue();
            it.remove();
            if (value instanceof Serializable) {
                state.put(name, (Serializable) value);
            } else {
                // Not serializable... Servlet containers usually automatically
                // unbind the attribute in this case.
                if (value instanceof HttpSessionBindingListener) {
                    ((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
                }
            }
        }
        return state;
    }

    /**
     * Deserialize the attributes of this session from a state object created by
     * {@link #serializeState()}.
     * 
     * @param state
     *            a representation of this session's serialized state
     */
    @SuppressWarnings("unchecked")
    public void deserializeState(Serializable state) {
        Assert.isTrue(state instanceof Map, "Serialized state needs to be of type [java.util.Map]");
        this.attributes.putAll((Map<String, Object>) state);
    }

}