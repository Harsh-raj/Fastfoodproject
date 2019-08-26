package practice;

public class billdata {

    public String Fname;
    public int Billno;
    public int Billamt;
    public Long Mobileno;
        
    public billdata(int Billno,String Fname,long Mobileno, int Billamt) {
        this.Fname = Fname;
        this.Billno = Billno;
        this.Billamt = Billamt;
        this.Mobileno = Mobileno;
    }
    
    public String getFname() {
        return Fname;
    }

    public int getBillno() {
        return Billno;
    }
    
    public int getBillamt() {
        return Billamt;
    }
    
    public long getMobileno() {
        return Mobileno;
    }
}
