/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.ConnectionPool;
import business.Store;
import business.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bekalu
 */
public class LogonServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
               String URL = "/Logon.jsp", msg="";
        String sql, uid="";
        int patt;
        User u;
        ArrayList<Store> stores = new ArrayList<>();
        
        try {
//            User Authentication
            uid = request.getParameter("userid").trim();
            patt = Integer.parseInt(request.getParameter("password").trim());

            Class.forName("com.mysql.jdbc.Driver");
            
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            sql = "SELECT * FROM users WHERE userID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uid);
            ResultSet r = ps.executeQuery();

            if(r.next()) {
                u = new User();
                u.setUserid(Integer.parseInt(uid));
                u.setPassword(r.getInt("UserPassword"));
                u.setPwdattempt(patt);
                if(u.isAuthenticated()) {
                    u.setUsername(r.getString("UserName"));
                    u.setStoreid(r.getInt("StoreID"));
                    u.setAdminlevel(r.getString("AdminLevel"));
                    URL = "/StoreSelection.jsp";
                    msg += "User " + uid + " Authenticated";
                    request.getSession().setAttribute("u", u);
                } else {
                    msg += "User " + uid + " found but not Authenticated";
                }
            } else {
                msg += "Member not found.<br>";
            }
            //End of User Authentication
           //Store Selection List
            sql = "SELECT * FROM stores ORDER BY StoreName";
            r = ps.executeQuery(sql);
            while(r.next()) {
                Store st = new Store();
                st.setStoreid(r.getInt("StoreID"));
                st.setStorename(r.getString("StoreName"));
                st.setStoreaddr(r.getString("StoreAddr"));
                st.setStoreemp(r.getInt("StoreEmp"));
                stores.add(st);
            } 
            if(stores.size() > 0) {
                request.getSession().setAttribute("stores", stores);
            } else {
                msg = "No stores read from stores table.<br>";
            }
            //End of Store Selection
            r.close();
            pool.freeConnection(conn);
        } catch(NumberFormatException e) {
            msg += "Password not numeric.<br>";
        } catch(SQLException e) {
            msg = "SQL Exception: " + e.getMessage() + "<br>";
        } catch(ClassNotFoundException e) {
            msg += "Servlet error: " + e.getMessage() + "<br>";
        }
        request.setAttribute("msg", msg);
        Cookie userid = new Cookie("userid",uid);
        userid.setMaxAge(60*10);
        userid.setPath("/");
        response.addCookie(userid);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request,response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
