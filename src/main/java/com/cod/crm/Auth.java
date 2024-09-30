package com.cod.crm;

import org.springframework.stereotype.Component;

import com.cod.crm.model.Users;


//这个注解不能忘记
@Component
public class Auth 
{
    
    //使用 ThreadLocal 来实现线程共享数据
    private  static final ThreadLocal<Users> storage = new ThreadLocal<>();


    public static void set(Users users)
    {
        storage.set(users);
    }
    public static Users get()
    {
        return storage.get();
    }

    public static void clear()
    {
        storage.remove();

    }
}