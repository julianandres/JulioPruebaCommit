/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dataEjb.ProcessEJB;
import dataEjb.SubProcessEJB;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.Proceso;
import models.SubProceso;
import org.primefaces.context.RequestContext;

/**
 *
 * @author JULIAN
 */

@ManagedBean
@SessionScoped
public class MainMB implements Serializable {

    public MainMB() {
        
    }
    
    @ManagedProperty("#{loginBean}")
    private LoginBean logBean;
    
    @PostConstruct
    public void init() {
        processTable = processEjb.findProcesobyIdUsuario(logBean.getUsername());
        System.out.println("hola");
        System.out.println(logBean.getUsername());
        setSubirFotos(false);
    }

    public LoginBean getLogBean() {
        return logBean;
    }

    public void setLogBean(LoginBean logBean) {
        this.logBean = logBean;
    }
    
    private boolean subirFotos;

    public boolean isSubirFotos() {
        return subirFotos;
    }

    public void setSubirFotos(boolean subirFotos) {
        this.subirFotos = subirFotos;
    }
    
    @EJB
    private ProcessEJB processEjb;
    
    @EJB
    private SubProcessEJB subProcessEjb;
    
    private List<Proceso> processTable;
    private Proceso processSelect;
    
    private List<SubProceso> subProcessTable;
    private SubProceso subProcessSelect;

    public List<SubProceso> getSubProcessTable() {
        return subProcessTable;
    }

    public void setSubProcessTable(List<SubProceso> subProcessTable) {
        this.subProcessTable = subProcessTable;
    }

    public SubProceso getSubProcessSelect() {
        return subProcessSelect;
    }

    public void setSubProcessSelect(SubProceso subProcessSelect) {
        this.subProcessSelect = subProcessSelect;
    }
    
    public List<Proceso> getProcessTable() {
        return processTable;
    }

    public void setProcessTable(List<Proceso> processTable) {
        this.processTable = processTable;
    }

    public Proceso getProcessSelect() {
        return processSelect;
    }

    public void setProcessSelect(Proceso processSelect) {
        this.processSelect = processSelect;
    }
    
    public void seleccionarSubProceso() {
        if (subProcessSelect != null) {
            if (subProcessSelect.getDisponibilidad() == 1) {
                setSubirFotos(true);
            } else {
                setSubirFotos(false);
            }
        }
    }
    public void abrirProceso(){
        if(processSelect!=null){
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("view", "processPage.xhtml");
            subProcessTable = subProcessEjb.findSubProcesobyIdProceso(processSelect.getId());
         }
        else {
         //TODO poner aqui el mensaje de seleccionar uno
        }  
    } 
}
