/**
 * 
 */
package com.bytesgo.littleproxy.model;

/**
 * @author leeyazhou
 *
 */
public class ProxyAuthorization {

    private String username;
    private String password;

    public ProxyAuthorization() {
    }

    public ProxyAuthorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
