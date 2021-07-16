
package servlets;

import business.BookInv;
import business.ConnectionPool;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bekalu
 */
public class UpdateInventoryServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String URL="/UpdateInv.jsp";
        String msg="", sql, bookcd;
        int Onhand = 0;
 
        try {           
            BookInv b = (BookInv) request.getSession().getAttribute("book");
            BookInv n = new BookInv();
            n.setBookcd(b.getBookcd());
            n.setAuthor(b.getAuthor());
            n.setTitle(b.getTitle());
            n.setStoreid(b.getStoreid()); 
            try {
                Onhand = Integer.parseInt(request.getParameter("update"));
                if(Onhand < 100 & Onhand >= 0) {
                    n.setOnhand(Onhand);
                } else {
                    msg="Invalid Amount.<br>";
                }
            } catch(NumberFormatException e) {
                msg = "OnHand Error: " + e.getMessage() + "<br>";
            }
            if(msg.isEmpty()) { 
                ConnectionPool pool = ConnectionPool.getInstance();
                Connection conn = pool.getConnection();
                sql = "UPDATE bookinv SET OnHand = ? " +
                      "WHERE storeID = ? " +
                      "AND bookID = '"+n.getBookcd()+"'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, n.getOnhand());
                ps.setDouble(2, n.getStoreid());
                int r = ps.executeUpdate();
                if(r == 0) {
                    msg+="OnHand was not updated!<br>";
                } else if(r == 1) {
                    URL = "/StoreSelection.jsp";
                    msg+="OnHand Updated!<br>";
                    b = n;
                    request.getSession().setAttribute("book", b);
                } else {
                    msg+="Warning: multiple OnHand records changed...<br>";
                }
                pool.freeConnection(conn);
            }    
        } catch(SQLException e) {
             msg+= "Sql error: " + e.getMessage() + "<br>";
        } catch(Exception e){
            msg+="Servlet Error: " + e.getMessage() + "<br>";
        }
        request.setAttribute("msg", msg);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request, response);
        
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
