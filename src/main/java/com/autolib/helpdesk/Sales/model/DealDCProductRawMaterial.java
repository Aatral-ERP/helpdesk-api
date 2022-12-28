package com.autolib.helpdesk.Sales.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "deal_dc_product_raw_materials")
public class DealDCProductRawMaterial implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int dealId;
    private String invoiceNo;
    @Column(name = "raw_material_product_id")
    private int rawMaterialProductId;
    @Column(name = "dc_product_id")
    private int dcProductId;
    private String name;
    private String description;
    private String uom;
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dc_product_row_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DealDCProducts dealDCProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getRawMaterialProductId() {
        return rawMaterialProductId;
    }

    public void setRawMaterialProductId(int rawMaterialProductId) {
        this.rawMaterialProductId = rawMaterialProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DealDCProducts getDealDCProducts() {
        return dealDCProducts;
    }

    public void setDealDCProducts(DealDCProducts dealDCProducts) {
        this.dealDCProducts = dealDCProducts;
    }

    public int getDcProductId() {
        return dcProductId;
    }

    public void setDcProductId(int dcProductId) {
        this.dcProductId = dcProductId;
    }

    @Override
    public DealDCProductRawMaterial clone() throws CloneNotSupportedException {
        try {
            return (DealDCProductRawMaterial) super.clone();
        } catch (CloneNotSupportedException e) {
            throw e;
        }
    }
}
