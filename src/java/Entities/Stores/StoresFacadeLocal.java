/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Stores;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface StoresFacadeLocal {

    void create(Stores stores);

    void edit(Stores stores);

    void remove(Stores stores);

    Stores find(Object id);

    List<Stores> findAll();

    List<Stores> findRange(int[] range);

    int count();

    List<String> getCities();
    
}
