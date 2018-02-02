/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Customers.Customers;
import Entities.Customers.CustomersFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@Named(value = "accountController")
@SessionScoped
public class AccountController implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;

    private String username;
    private String password;
    private boolean rememberMe = false;
    private Customers currentCustomer = null;

    public AccountController() {
        isChecked();
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customers getCurrentCustomer() {
        return currentCustomer;
    }

    private void isChecked() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cName = cookie.getName();
                String cValue = cookie.getValue();
                
                switch (cName) {
                    case "cUsername":
                        username = cValue;
                        break;
                    case "cPassword":
                        password = cValue;
                        break;
                    case "cRememberMe":
                        rememberMe = cValue.equals("true");
                        if (!rememberMe) {
                            username = null;
                            password = null;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public String checkLogin(HttpSession session) {
        currentCustomer = customersFacade.checkLogin(username.trim().toLowerCase(), password);
        if (currentCustomer != null) {
            session.setAttribute("user", username.trim().toLowerCase());
            session.setMaxInactiveInterval(120 * 60);
            Cookie cRememberMe = new Cookie("cRememberMe", String.valueOf(rememberMe));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            if (rememberMe) {
                Cookie cUserId = new Cookie("cUsername", username);
                Cookie cPassword = new Cookie("cPassword", password);                
                int maxAge = 60 * 60 * 24 * 30; //1 month
                cUserId.setMaxAge(maxAge);
                cPassword.setMaxAge(maxAge);
                cRememberMe.setMaxAge(maxAge);
                
                response.addCookie(cUserId);
                response.addCookie(cPassword);
            }
            response.addCookie(cRememberMe);
            return "account";
        }
        return "signin";
    }

    public String logout(HttpSession session) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            for (Cookie cookie : cookies) {
                String cName = cookie.getName();
                System.out.println(cName);
                if (cName.equals("cUsername") || cName.equals("cPassword") || cName.equals("cRememberMe")) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);                    
                    response.addCookie(cookie);
                }
            }
        }
        session.removeAttribute("user");
        currentCustomer = null;
        username = "";
        password = "";
        rememberMe = false;
        return "index";
    }

    public String update() {
        return "profile";
    }
}
