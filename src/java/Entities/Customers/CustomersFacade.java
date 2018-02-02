/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Customers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Stateless
public class CustomersFacade extends AbstractFacade<Customers> implements CustomersFacadeLocal {

    @PersistenceContext(unitName = "tShopProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersFacade() {
        super(Customers.class);
    }

    @Override
    public Customers checkLogin(String username, String password) {
        Query q = em.createQuery("SELECT c FROM Customers c WHERE c.email = :email and c.password = :password");
        q.setParameter("email", username);
        q.setParameter("password", password);
        try {
            return (Customers) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }        
    }
    
}
