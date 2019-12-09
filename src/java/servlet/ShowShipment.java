/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import dao.ShipmentDAO;
import dao.ProductDAO;
import model.Shipment;
import model.User;

/**
 *
 * @author Minh Đức
 */
@WebServlet(name = "ShowShipment", urlPatterns = {"/ShowShipment"})
public class ShowShipment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowShipment</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowShipment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ShipmentDAO sd = new ShipmentDAO();
        ProductDAO pd = new ProductDAO();
        ArrayList<Shipment> listShipment = sd.getAllShipment(user.getId(), 0);
        for(int i = 0;i < listShipment.size();i++){
            String item = "item";
            item += i;
            String idShipment = request.getParameter(item);
            if(idShipment != null){
                sd.updateShipmentStatus(Integer.parseInt(idShipment), 1);
                Shipment shipment = sd.getShipment(Integer.parseInt(idShipment));
                pd.updateRemain(-shipment.getQuantity(), shipment.getProduct().getId());
            }
        }
        listShipment = sd.getAllShipment(user.getId(), 1);
        request.setAttribute("listShipment", listShipment);
        RequestDispatcher rd = request.getRequestDispatcher("");
        rd.forward(request, response);
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
