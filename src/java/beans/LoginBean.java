/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataBase.ConexionDB;
import dataEjb.UserEJB;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import models.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author julian
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UserEJB userEjb;
    private String username;
    private String password;
    private boolean login = false;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean estaLogeado() {
        return login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (username != null) {
            Usuario usu = userEjb.findUserByLogin(username);
            if (usu.getPassword() != null) {
                if (username.equals(usu.getLogin()) && password != null
                        && password.equals(usu.getPassword())) {
                    login = true;
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", usu.getNombre());
                } else {
                    login = false;
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                            "Credenciales no válidas");
                }
            } else {
                login = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                        "Credenciales no válidas");
            }
        } else {
            login = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", login);
        if (login) {
            context.addCallbackParam("view", "uploadPage.xhtml");
        }
    }

    public String logout() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("view", "index.xhtml");
        context.addCallbackParam("estaLogeado", login);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        login = false;
        return "index.xhtml";
    }

    public String iniciarSistema() {
        String destino;
        Usuario us = userEjb.findUserByID(1);
        System.out.println(us.getNombre());
        if (login) {
            destino = "uploadPage.xhtml";
        } else {
            destino = "loginPage.xhtml";
        }

        return destino;
    }

}
