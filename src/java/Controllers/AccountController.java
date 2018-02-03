/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Customers.Customers;
import Entities.Customers.CustomersFacadeLocal;
import java.io.File;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
    private Part file;
    private String oldPass;
    private String newPass;
    private String confirmPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public AccountController() {

        isChecked();
    }

    public String getErrPass() {
        return errPass;
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
        if (file != null && file.getSize() > 0) {
            String dirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/Avatars").replace("build\\web", "web");
            String fullFilename = file.getSubmittedFileName();
            int lastIndex = fullFilename.lastIndexOf('.');
            //String filename = fullFilename.substring(0, lastIndex);
            String extension = fullFilename.substring(lastIndex + 1, fullFilename.length());
            try (InputStream input = file.getInputStream()) {
                String filename = currentCustomer.getEmail() + "." + extension;
                Files.copy(input, new File(dirPath + "/" + filename).toPath(), StandardCopyOption.REPLACE_EXISTING);
                currentCustomer.setAvatar("images/Avatars/" + filename);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(currentCustomer.getEmail()+":"+currentCustomer.getFirstName()+":"+currentCustomer.getLastName()+":"+currentCustomer.getPassword()+":"+currentCustomer.getPhone()+":"+currentCustomer.getAvatar()+":"+currentCustomer.getGender()+":"+currentCustomer.getCreatedDate());
        customersFacade.edit(currentCustomer);
        return "profile";
    }

    private String errPass;

    public String changePassword() {
        if (currentCustomer.getPassword().equals(oldPass) && confirmPass.equals(newPass)) {
            currentCustomer.setPassword(newPass);
            customersFacade.edit(currentCustomer);
            oldPass = "";
            newPass = "";
            confirmPass = "";
            return "profile";
        }
        if (!currentCustomer.getPassword().equals(oldPass)) {
            errPass = "+ Old password is wrong.";
            return "profile";
        }
        errPass = "+ Confirm password does not match with New Password.";
        return "profile";
    }

}
