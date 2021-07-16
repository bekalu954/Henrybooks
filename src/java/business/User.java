
package business;

/**
 *
 * @author Bekalu
 */
public class User {
    private int userid , storeid, password, pwdattempt;
    private String username, adminlevel;
    
    public User() {
        this.userid = 0;
        this.storeid = 0;
        this.password = 0;
        this.pwdattempt = 0;
        this.username = "";
        this.adminlevel = "";
    }

    public void setUserid(int userid) {this.userid = userid;}
    public void setStoreid(int storeid) {this.storeid = storeid;}
    public void setPassword(int password) {this.password = password;}
    public void setPwdattempt(int pwdattempt) {this.pwdattempt = pwdattempt;}
    public void setUsername(String username) {this.username = username;}
    public void setAdminlevel(String adminlevel) {this.adminlevel = adminlevel;}
    
    public int getUserid() {return userid;}
    public int getStoreid() {return storeid;}
    public int getPassword() {return password;}
    public int getPwdattempt() {return pwdattempt;}
    public String getUsername() {return username;}
    public String getAdminlevel() {return adminlevel;}
    
    public boolean isAuthenticated() {
        if(this.password > 0) {
            if(this.password == this.pwdattempt) {
                   return true;
            }
        }
        return false;
    }
}
