/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Stores;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Stores", catalog = "Project4DB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stores.findAll", query = "SELECT s FROM Stores s")
    , @NamedQuery(name = "Stores.findByStoreID", query = "SELECT s FROM Stores s WHERE s.storeID = :storeID")
    , @NamedQuery(name = "Stores.findByStoreName", query = "SELECT s FROM Stores s WHERE s.storeName = :storeName")
    , @NamedQuery(name = "Stores.findByLongitude", query = "SELECT s FROM Stores s WHERE s.longitude = :longitude")
    , @NamedQuery(name = "Stores.findByLatitude", query = "SELECT s FROM Stores s WHERE s.latitude = :latitude")
    , @NamedQuery(name = "Stores.findByStoreAddress", query = "SELECT s FROM Stores s WHERE s.storeAddress = :storeAddress")
    , @NamedQuery(name = "Stores.findByCity", query = "SELECT s FROM Stores s WHERE s.city = :city")
    , @NamedQuery(name = "Stores.findByContactPerson", query = "SELECT s FROM Stores s WHERE s.contactPerson = :contactPerson")
    , @NamedQuery(name = "Stores.findByPhone", query = "SELECT s FROM Stores s WHERE s.phone = :phone")
    , @NamedQuery(name = "Stores.findByStoreImages", query = "SELECT s FROM Stores s WHERE s.storeImages = :storeImages")
    , @NamedQuery(name = "Stores.findByDescriptions", query = "SELECT s FROM Stores s WHERE s.descriptions = :descriptions")
    , @NamedQuery(name = "Stores.findByStoreState", query = "SELECT s FROM Stores s WHERE s.storeState = :storeState")})
public class Stores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "StoreID", nullable = false, length = 10)
    private String storeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "StoreName", nullable = false, length = 100)
    private String storeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Longitude", nullable = false, length = 20)
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Latitude", nullable = false, length = 20)
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "StoreAddress", nullable = false, length = 2147483647)
    private String storeAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "City", nullable = false, length = 100)
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ContactPerson", nullable = false, length = 100)
    private String contactPerson;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "StoreImages", nullable = false, length = 2147483647)
    private String storeImages;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "Descriptions", nullable = false, length = 2147483647)
    private String descriptions;
    @Column(name = "StoreState")
    private Boolean storeState;

    public Stores() {
    }

    public Stores(String storeID) {
        this.storeID = storeID;
    }

    public Stores(String storeID, String storeName, String longitude, String latitude, String storeAddress, String city, String contactPerson, String phone, String storeImages, String descriptions) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.storeAddress = storeAddress;
        this.city = city;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.storeImages = storeImages;
        this.descriptions = descriptions;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStoreImages() {
        return storeImages;
    }

    public void setStoreImages(String storeImages) {
        this.storeImages = storeImages;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Boolean getStoreState() {
        return storeState;
    }

    public void setStoreState(Boolean storeState) {
        this.storeState = storeState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeID != null ? storeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stores)) {
            return false;
        }
        Stores other = (Stores) object;
        if ((this.storeID == null && other.storeID != null) || (this.storeID != null && !this.storeID.equals(other.storeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Stores.Stores[ storeID=" + storeID + " ]";
    }
    
}
