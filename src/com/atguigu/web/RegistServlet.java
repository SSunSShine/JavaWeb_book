package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistServlet extends HttpServlet {

    //生成业务层对象来调用业务层来处理业务逻辑
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.利用servlet对象获取请求的参数（html表单中的name标签名的参数）
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //2.检查验证码是否正确
        if ("abcd".equalsIgnoreCase(code)) {
            //正确，检查用户名是否可用
            if (userService.existsUsername(name)) {
                //用户名不可用，跳回注册页面
                System.out.println("用户名[" + name + "]已存在！");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);//请求转发
            } else {
                //用户名可用，调用service保存到数据库
                userService.registerUser(new User(null,name,password,email));
                //跳到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }

        } else {
            System.out.println("验证码[" + code + "]错误！");
            //不正确，跳回注册页面
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }

    }
}
