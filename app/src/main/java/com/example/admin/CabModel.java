package com.example.admin;

public class CabModel {
    String cabname,cabnumber,cabprice,cabpic,cabrating,cabstatus;
    CabModel(){

    }


    public CabModel(String cabname, String cabnumber, String cabprice, String cabpic, String cabrating, String cabstatus) {
        this.cabname = cabname;
        this.cabnumber = cabnumber;
        this.cabprice = cabprice;
        this.cabpic = cabpic;
        this.cabrating = cabrating;
        this.cabstatus = cabstatus;
    }

    public String getCabname() {
        return cabname;
    }

    public void setCabname(String cabname) {
        this.cabname = cabname;
    }

    public String getCabnumber() {
        return cabnumber;
    }

    public void setCabnumber(String cabnumber) {
        this.cabnumber = cabnumber;
    }

    public String getCabprice() {
        return cabprice;
    }

    public void setCabprice(String cabprice) {
        this.cabprice = cabprice;
    }

    public String getCabpic() {
        return cabpic;
    }

    public void setCabpic(String cabpic) {
        this.cabpic = cabpic;
    }

    public String getCabrating() {
        return cabrating;
    }

    public void setCabrating(String cabrating) {
        this.cabrating = cabrating;
    }

    public String getCabstatus() {
        return cabstatus;
    }

    public void setCabstatus(String cabstatus) {
        this.cabstatus = cabstatus;
    }
}
