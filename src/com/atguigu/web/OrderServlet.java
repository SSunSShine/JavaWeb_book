package com.atguigu.web;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        //先获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取Userid
        User loginUser = (User) req.getSession().getAttribute("user");

        if(loginUser == null){
            try {
                req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        Integer userId = loginUser.getId();
        //调用orderService.createOrder(Cart,userId)生成订单
        String orderId = orderService.createOrder(cart,userId);

        req.getSession().setAttribute("orderId",orderId);
        try {
            resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showAllOrders(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.showAllOrders();

        req.setAttribute("orders",orders);

        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);

    }

    protected void showMyOrders(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        List<Order> orders = orderService.showMyOrders(user.getId());

        req.setAttribute("orders",orders);

        req.getRequestDispatcher("/pages/order/order.jsp").forward(req,resp);

    }

    protected void sendOrder(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");

        orderService.sendOrder(orderId);

        resp.sendRedirect(req.getContextPath() + "/orderServlet?action=showAllOrders");
    }

    protected void receiveOrder(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");

        orderService.receiveOrder(orderId);

        resp.sendRedirect(req.getContextPath() + "/orderServlet?action=showAllOrders");

    }

    protected void showOrderDetail(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");

        List<OrderItem> orderItems = orderService.showOrderDetail(orderId);

        req.setAttribute("orderItems",orderItems);

        req.getRequestDispatcher("/pages/order/order_detail.jsp").forward(req,resp);

    }
}
