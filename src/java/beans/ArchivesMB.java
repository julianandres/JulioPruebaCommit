/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataBase.ConexionDB;
import dataEjb.SubProcessEJB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author julian
 */
@ManagedBean
@SessionScoped
public class ArchivesMB implements Serializable {

    private String destination;

    @ManagedProperty("#{loginBean}")
    private LoginBean logBean;

    @ManagedProperty("#{mainMB}")
    private MainMB mainmb;
    
    @EJB
    private SubProcessEJB ejbSubProcess;
    /**
     * Creates a new instance of archivesMB
     *
     * @param event
     */
    private boolean stateNgb;
    
    private boolean stateRgb;
    
        @PostConstruct
        public void init() {
        
        System.out.println("hola");
        System.out.println(logBean.getUsername());
        if(mainmb.getSubProcessSelect().getFotonoir()==0){
            setStateNgb(false);
        }else
        {
            setStateNgb(true);
        }
        if(mainmb.getSubProcessSelect().getFotorgb()==0){
            setStateRgb(false);
        }else
        {
            setStateRgb(true);
        }
    }

    public boolean isStateRgb() {
        return stateRgb;
    }

    public void setStateRgb(boolean stateRgb) {
        this.stateRgb = stateRgb;
    }
        
    
    public boolean isStateNgb() {
        return stateNgb;
    }

    public void setStateNgb(boolean stateNgb) {
        this.stateNgb = stateNgb;
    }
    
    public LoginBean getLogBean() {
        return logBean;
    }

    public void setLogBean(LoginBean logBean) {
        this.logBean = logBean;
    }

    public MainMB getMainmb() {
        return mainmb;
    }

    public void setMainmb(MainMB mainmb) {
        this.mainmb = mainmb;
    }

    public void handleFileUploadNoir(FileUploadEvent event) {
        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), "FotoNoir\\");
            ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(),"fotonoir");
            stateNgb=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFileUploadRGB(FileUploadEvent event) {
        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), "FotoRGB\\");
            ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(),"fotorgb");
            stateRgb=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String fileName, InputStream in, String tipo) {
        destination = "D:\\" + logBean.getUsername() + "\\" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "\\" + mainmb.getSubProcessSelect().getNombre() + "\\" + tipo;
        try {
            if (new File(destination).mkdirs()) {
                System.out.println("create Exited");
                OutputStream out = new FileOutputStream(new File(destination + fileName));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();

                System.out.println("New file created!");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Succesful", fileName + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error", fileName + " no fue subido, ya existe .");
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("Problem Create");
            }
            System.out.println("prueba");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
