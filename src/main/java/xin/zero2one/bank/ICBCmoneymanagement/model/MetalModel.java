package xin.zero2one.bank.ICBCmoneymanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhoujundong on 2018/10/8.
 */
@Entity
@Table(name="metal")
public class MetalModel {

    @Id
    @GeneratedValue
    private Long id;
    private String upordown;
    private String lowmiddleprice;
    private String updown_d;
    private String buyprice;
    private String unit;
    private String prodcode;
    private String ebuyprice;
    private String openprice_dr;
    private String openprice_dv;
    private String metalname;
    private String esellprice;
    private String metalflag;
    private String middleprice;
    private String updown_y;
    private String sellprice;
    private String openprice_yr;
    private String topmiddleprice;
    private String currcode;
    private Long time;
    // 0 : gold. 1: silver
    private int type;

    public Long getId() {
        return id;
    }

    public String getUpordown() {
        return upordown;
    }

    public void setUpordown(String upordown) {
        this.upordown = upordown;
    }

    public String getLowmiddleprice() {
        return lowmiddleprice;
    }

    public void setLowmiddleprice(String lowmiddleprice) {
        this.lowmiddleprice = lowmiddleprice;
    }

    public String getUpdown_d() {
        return updown_d;
    }

    public void setUpdown_d(String updown_d) {
        this.updown_d = updown_d;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getEbuyprice() {
        return ebuyprice;
    }

    public void setEbuyprice(String ebuyprice) {
        this.ebuyprice = ebuyprice;
    }

    public String getOpenprice_dr() {
        return openprice_dr;
    }

    public void setOpenprice_dr(String openprice_dr) {
        this.openprice_dr = openprice_dr;
    }

    public String getOpenprice_dv() {
        return openprice_dv;
    }

    public void setOpenprice_dv(String openprice_dv) {
        this.openprice_dv = openprice_dv;
    }

    public String getMetalname() {
        return metalname;
    }

    public void setMetalname(String metalname) {
        this.metalname = metalname;
    }

    public String getEsellprice() {
        return esellprice;
    }

    public void setEsellprice(String esellprice) {
        this.esellprice = esellprice;
    }

    public String getMetalflag() {
        return metalflag;
    }

    public void setMetalflag(String metalflag) {
        this.metalflag = metalflag;
    }

    public String getMiddleprice() {
        return middleprice;
    }

    public void setMiddleprice(String middleprice) {
        this.middleprice = middleprice;
    }

    public String getUpdown_y() {
        return updown_y;
    }

    public void setUpdown_y(String updown_y) {
        this.updown_y = updown_y;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }

    public String getOpenprice_yr() {
        return openprice_yr;
    }

    public void setOpenprice_yr(String openprice_yr) {
        this.openprice_yr = openprice_yr;
    }

    public String getTopmiddleprice() {
        return topmiddleprice;
    }

    public void setTopmiddleprice(String topmiddleprice) {
        this.topmiddleprice = topmiddleprice;
    }

    public String getCurrcode() {
        return currcode;
    }

    public void setCurrcode(String currcode) {
        this.currcode = currcode;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
