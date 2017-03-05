/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataBase.ConexionDB;
import dataEjb.SubProcessEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author julian
 */
@ManagedBean
@SessionScoped
public class ArchivesMB implements Serializable {
    
    private String destination;
    private String destinationImage;
    @ManagedProperty("#{loginBean}")
    private LoginBean logBean;
    
    @ManagedProperty("#{mainMB}")
    private MainMB mainmb;
    private File prueba;
    StreamedContent imageen;
    
    @EJB
    private SubProcessEJB ejbSubProcess;
    /**
     * Creates a new instance of archivesMB
     *
     * @param event
     */
    private boolean stateNgb;
    
    private boolean stateRgb;
    private boolean subirFotos;
    private boolean selecttypephoto;
    
    @PostConstruct
    public void init() {
        setSelecttypephoto(true);
        setSubirFotos(true);
        System.out.println("hola");
        System.out.println(logBean.getUsername());
        if (mainmb.getSubProcessSelect().getFotonoir() == 0) {
            setStateNgb(false);
        } else {
            setStateNgb(true);
        }
        if (mainmb.getSubProcessSelect().getFotorgb() == 0) {
            setStateRgb(false);
        } else {
            setStateRgb(true);
        }
        destinationImage = "hola";
        
    }

    public boolean isSelecttypephoto() {
        return selecttypephoto;
    }
    
    public void setSelecttypephoto(boolean selecttypephoto) {
        this.selecttypephoto = selecttypephoto;
    }
    
    public boolean isSubirFotos() {
        return subirFotos;
    }
    
    public void setSubirFotos(boolean subirFotos) {
        this.subirFotos = subirFotos;
    }
    
    public StreamedContent getImageen() {
        return imageen;
    }
    
    public void setImageen(StreamedContent imageen) {
        this.imageen = imageen;
    }
    
    public File getPrueba() {
        return prueba;
    }
    
    public void setPrueba(File prueba) {
        this.prueba = prueba;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getDestinationImage() {
        return destinationImage;
    }
    
    public void setDestinationImage(String destinationImage) {
        this.destinationImage = destinationImage;
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
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), "FotoNoir/");
            ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(), "fotonoir", "1");
            stateNgb = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFileUploadRGB(FileUploadEvent event) {
        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream(), "FotoRGB/");
            ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(), "fotorgb", "1");
            stateRgb = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileNoir() {
        deleteFile("file", "FotoNoir/");
        ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(), "fotonoir", "0");
        stateNgb = false;
    }

    public void deleteFileRGB() {
        deleteFile("file", "FotoRGB/");
        ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(), "fotorgb", "0");
        stateRgb = false;
    }
    
    public void copyFile(String fileName, InputStream in, String tipo) {
        destination = "/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + tipo;
        destinationImage = destination + fileName;
        System.out.println(destination);
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
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", fileName + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", fileName + " no fue subido, ya existe .");
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("Problem Create");
            }
            System.out.println("prueba");
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteFile(String fileName, String tipo) {
        destination = "/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + tipo;
        System.out.println(destination);
        File directiorioAborrar = new File(destination);
        borrarDirectorio(directiorioAborrar);
        
        if (directiorioAborrar.delete()) {
            System.out.println("Eliminado con exito");
            
            System.out.println("se ha eliminado");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", "Eliminado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error eliminando");
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("Problem Create");
        }
        System.out.println("prueba");
    }

    public void borrarDirectorio(File directorio) {
        File[] ficheros = directorio.listFiles();
        for (File fichero : ficheros) {
            if (fichero.isDirectory()) {
                borrarDirectorio(fichero);
            }
            fichero.delete();
        }
    }

    public void irUploadPhotoNGB() {
        setSubirFotos(true);
        setSelecttypephoto(true);        
    }

    public void irUploadPhotoRGB() {
        setSubirFotos(true);
        setSelecttypephoto(false);
    }

    public void verFotoNoir() {
        setSubirFotos(false);
        String dir="/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + "FotoNoir/" ;
        File directorio = new File(dir);
        File[] ficheros = directorio.listFiles();
        for (File fichero : ficheros) {
            try {
                if (fichero.isDirectory()) {
                    borrarDirectorio(fichero);
                }
                prueba = fichero;
                imageen = new DefaultStreamedContent(new FileInputStream(prueba));
                System.out.println("prueba");
            } //destinationImage = "/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + "FotoNoir/";
            catch (FileNotFoundException ex) {
                Logger.getLogger(ArchivesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void verFotoRGB() {
        setSubirFotos(false);
        String dir="/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + "FotoRGB/" ;
        File directorio = new File(dir);
        File[] ficheros = directorio.listFiles();
        for (File fichero : ficheros) {
            try {
                if (fichero.isDirectory()) {
                    borrarDirectorio(fichero);
                }
                prueba = fichero;
                imageen = new DefaultStreamedContent(new FileInputStream(prueba));
                System.out.println("prueba");
            } //destinationImage = "/home/julian/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + "FotoNoir/";
            catch (FileNotFoundException ex) {
                Logger.getLogger(ArchivesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public String finalizarSubProceso(){
            if(ejbSubProcess.updateSubProceso(mainmb.getSubProcessSelect(), "estado", "1")){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Finalizado", "Registro de fotografías completado");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "mainPage.xhtml?faces-redirect=true";
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error registrando cambios");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "";
            }
    }
    
}
