/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Brands.Brands;
import Entities.Brands.BrandsFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "brandController")
@SessionScoped
public class BrandController implements Serializable {

    @EJB
    private BrandsFacadeLocal brandsFacade;
    
    public BrandController() {
    }
    
    public List<Brands> getBrands() {
        return brandsFacade.findAll();
    }
}
