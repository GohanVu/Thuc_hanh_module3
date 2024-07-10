package controllers;

import models.Product;
import services.IService;
import services.impl.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainControllers", value = "/product")
public class MainControllers extends HttpServlet {
    private static IService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
//            case "edit":
//                showEditForm(req, resp);
            case "create":
                showEditForm(req, resp);
                break;
            default:
                List<Product> products = productService.findAll();
                req.setAttribute("products",products);
                req.getRequestDispatcher("product/list.jsp").forward(req,resp);
        }
        }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Product> products = productService.findAll();
        req.setAttribute("cars", products);
        req.getRequestDispatcher("/product/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createStudent(req, resp);
                break;
            case "top":
                topStudent(req, resp);
                break;
        }
    }

    private void topStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int top = Integer.parseInt(req.getParameter("top"));
        List<Product> products = productService.findTop(top);
        req.setAttribute("products",products);
        req.getRequestDispatcher("/product/list.jsp").forward(req,resp);
    }


    private void createStudent(HttpServletRequest req, HttpServletResponse resp) throws  ServletException,IOException{
        String name = req.getParameter("name");
        Float price = Float.parseFloat(req.getParameter("price"));
        Float discount = Float.parseFloat(req.getParameter("discount"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        Product product = new Product(name,price,discount,quantity);
        productService.save(product);
        List<Product> products = productService.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/product/list.jsp").forward(req,resp);

    }
}

