package Controllers;

import Entities.Stores.Stores;
import Entities.Stores.StoresFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

@Named(value = "storeController")
@SessionScoped
public class StoreControllers implements Serializable {

    @EJB
    private StoresFacadeLocal storesFacade;
    
    public StoreControllers() {
    }
    
    public List<String> getCities() {
        return storesFacade.getCities();
    }
    
    public List<Stores> getStores() {
        return storesFacade.findAll();
    }
}
