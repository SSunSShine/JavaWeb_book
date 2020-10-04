package com.atguigu.dao;

import com.atguigu.pojo.User;

public interface UserDao {



    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 如果返回null，说明没有这个用户，反之亦然
     */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名和密码查询
     * @param username
     * @return 如果返回null，说明用户名或密码错误，反之亦然
     */
    public User queryUserByUsernameAndPassword(String username,String password);

    /**
     * 保存用户信息
     * @param uer
     * @return
     */
    public int saveUser(User uer);
}