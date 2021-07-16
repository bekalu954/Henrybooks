
package servlets;

import business.BookInv;
import business.ConnectionPool;
import business.Store;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bekalu
 */
public class ViewInventoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         String sql, msg="", bookcd;
        String URL="/ViewInventory.jsp";
        int storeid;
        Store st = null;
        BookInv b;
        ArrayList<BookInv> books = new ArrayList();
        ArrayList<Store> stores;
        String action = request.getParameter("action");
        if(action == null) {
            action = "Inventory";
        }
        
        if(action.equals("Inventory")) {
//          View store Inventory
        try {
            stores = (ArrayList<Store>)
                    request.getSession().getAttribute("stores");
            storeid = Integer.parseInt(
                        request.getParameter("storeid"));
                    for(Store store : stores) {
                        if(store.getStoreid() == storeid) {
                            request.getSession().setAttribute("store", store);
                        }
                    }
            sql = "SELECT bookinv.storeID, bookinv.bookID, "
                    + "title, author, price, OnHand "
                    + "FROM bookinv, booklist "
                    + "WHERE bookinv.bookid = booklist.bookID "
                    + "AND bookinv.storeID = ? "
                    + "ORDER BY bookID";
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,storeid);
            ResultSet r = ps.executeQuery();
            while(r.next()) {
                b = new BookInv();
                b.setStoreid(storeid);
                b.setBookcd(r.getString("BookID"));
                b.setOnhand(r.getInt("OnHand"));
                b.setTitle(r.getString("Title"));
                b.setAuthor(r.getString("Author"));
                b.setPrice(r.getDouble("Price"));
                books.add(b);
            }
            request.getSession().setAttribute("books", books);  
            r.close();
            pool.freeConnection(conn);
        } catch(Exception e) {
            URL = "/StoreSelection.jsp";
            msg += "Inventory Error: " + e.getMessage();
        }
//        If User if 'Admin'
        } else if(action.equals("update")) {
//            Select book for Update
            try {
                b = new BookInv();
                b.setnewcd(request.getParameter("bookcd").trim());
                books = (ArrayList<BookInv>)request.getSession().getAttribute("books");
                for(BookInv book : books) {
                    if(book.getBookcd().equals(b.getnewcd())) {
                        b.setBookcd(book.getBookcd());
                        b.setTitle(book.getTitle());
                        b.setAuthor(book.getAuthor());
                        b.setOnhand(book.getOnhand());
                        b.setStoreid(book.getStoreid());
                        request.getSession().setAttribute("book", b);
                    }
                }
                if(b.bookFound()) {
                    URL = "/UpdateInv.jsp";
                    msg = "Book found!";
                } else {
                    URL = "/StoreSelection.jsp";
                    msg = "Could not find book.";
                }
            } catch(Exception e) {
                msg += "record Error: " + e.getMessage() + ".<br>";
            }
        }
        request.setAttribute("msg", msg);
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
