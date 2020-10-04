package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) {
        //获取请求的参数，商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        Book book = bookService.queryBookById(id);
        //把图书信息转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(CartItem)添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        System.out.println(cartItem);
        System.out.println(cart);

        System.out.println("请求头Referer的值：" + req.getHeader("Referer"));

        //最后一个添加的商品名称
        req.getSession().setAttribute("lastName",cartItem.getName());

        //重定向回原来的商品所在地址页面
        try {
            resp.sendRedirect(req.getHeader("Referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) {
        //获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart != null) {
            cart.deleteItem(id);
            //重定向回原来购物车页面
            try {
                resp.sendRedirect(req.getHeader("Referer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void clear(HttpServletRequest req, HttpServletResponse resp) {
        //获取当前购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
            //重定向回购物车页面
            try {
                resp.sendRedirect(req.getHeader("Referer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) {
        //获取请求的参数：商品编号，商品数量
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        int count = WebUtils.parseInt(req.getParameter("count"),1);
        //获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null){
            cart.updateCount(id,count);
            //重定向回购物车展示页面
            try {
                resp.sendRedirect(req.getHeader("Referer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void ajaxAddItem(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        Book book = bookService.queryBookById(id);

        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        req.getSession().setAttribute("lastName",cartItem.getName());

        //返回购物车总的商品数量和最后一个添加的商品名称
        Map<String ,Object> resultMap = new HashMap<>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);

        resp.getWriter().write(resultMapJsonString);
    }
}
