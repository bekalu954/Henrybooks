
package business;

/**
 *
 * @author Bekalu
 */
public class BookInv {
    
    private int onhand = 0, storeid = 0;
    private String title = "", author = "", bookcd = "", newbookcd = "";
    private double price = 0;
    
    public BookInv() {
        this.storeid = 0;
        this.bookcd = "";
        this.newbookcd = "";
        this.onhand = 0;
        this.title = "";
        this.author = "";
        this.price = 0;
    }
    
    public void setStoreid(int storeid) {this.storeid = storeid;}
    public void setBookcd(String bookcd) {this.bookcd = bookcd;}
    public void setnewcd(String newbookcd) {this.newbookcd = newbookcd;}
    public void setOnhand(int onhand) {this.onhand = onhand;}
    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setPrice(double price) {this.price = price;}
    
    public int getStoreid() {return storeid;}
    public String getBookcd() {return bookcd;}
    public String getnewcd() {return newbookcd;}
    public int getOnhand() {return onhand;}
    public String getTitle() {return title;}
    public String getAuthor() {return this.author;}
    public double getPrice() {return price;}
    
     public boolean bookFound() {
        if(this.bookcd.length() > 0) {
            return newbookcd.equals(this.bookcd);
        }
        return false;
    }
}
