
package business;

/**
 *
 * @author Bekalu
 */
public class Store {
    private int storeid, storeemp;
    private String storename, storeaddr;
    
    public Store() {
        this.storeid = 0;
        this.storeemp = 0;
        this.storename = "";
        this.storeaddr = "";
    }

    public void setStoreid(int storeid) {this.storeid = storeid;}
    public void setStoreemp(int storeemp) {this.storeemp = storeemp;}
    public void setStorename(String storename) {this.storename = storename;}
    public void setStoreaddr(String storeaddr) {this.storeaddr = storeaddr;}
    
    public int getStoreid() {return storeid;}
    public int getStoreemp() {return storeemp;}
    public String getStorename() {return storename;}
    public String getStoreaddr() {return storeaddr;}
}
