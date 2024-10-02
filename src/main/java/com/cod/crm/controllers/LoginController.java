//package com.cod.crm.controllers;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class LoginController {
//	
//	@RequestMapping(value="/api/login/test",method=RequestMethod.GET)
//	public String test() {
//		return "hello";
//		
//	}
//
//}

package com.cod.crm.controllers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cod.crm.mapper.UsersMapper;
import com.cod.crm.model.Users;
import com.cod.crm.model.UsersExample;
import com.cod.crm.model.UsersExample.Criteria;
import com.cod.crm.responses.Response;
//import com.cod.crm.responses.ResponseLogout;

@RestController
public class LoginController  {

    @Autowired
    private UsersMapper usersMapper;



    @RequestMapping(value = "/api/login/login", method = RequestMethod.POST)
    public  Response<Users> login(
         @RequestParam(value = "username", defaultValue = "") String username,
         @RequestParam(value = "password", defaultValue = "") String password
     ) 
    {
          Response<Users> response= new Response<Users>();


          System.out.println("username " + username);
          System.out.println("password " + password);

          UsersExample usersExample = new UsersExample();
          Criteria criteria =  usersExample.createCriteria();
          
          criteria.andUsernameEqualTo(username);
          criteria.andPasswordEqualTo(password);
 
          List<Users> users  = usersMapper.selectByExample(usersExample);
          Users user=null;
          
//          Users user = new Users(); // 确保 user 被初始化
          if(users.size() > 0 )  user=users.get(0);

          if(user == null )
          {
               response.code="USER_NOT_EXISTS";
               response.message="用户不存在或者账号密码错误";
               response.data=null;

               return response;
          }



          String token = this.generateRandom(32);

          user.setToken(token);

          usersMapper.updateByPrimaryKey(user);


          response.data = user;

         return response;
    }

    @RequestMapping(value = "/api/login/register", method = RequestMethod.POST)
    public  Response<Users> register(
         @RequestParam(value = "username", defaultValue = "") String username,
         @RequestParam(value = "password", defaultValue = "") String password
     ) 
    {
          Response<Users> response= new Response<Users>();


          System.out.println("username " + username);
          System.out.println("password " + password);


          UsersExample usersExample = new UsersExample();
          Criteria criteria =  usersExample.createCriteria();
          
          criteria.andUsernameEqualTo(username);
 
          List<Users> users  = usersMapper.selectByExample(usersExample);
          Users user=null;
          if(users.size() > 0 )  user=users.get(0);

          if(user != null )
          {
               response.code="USER_ALREDY_EXISTS";
               response.message="用户已存在";
               response.data=null;

               return response;
          }


          user=new Users();
          user.setUsername(username);
          user.setPassword(password);

          usersMapper.insert(user);


          response.data = user;

         return response;
    }


    private String generateRandom(int length) {

         String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmnopqrstuvwxyz1234567890"; // 36 letter.

         Random rand = new Random();
         StringBuilder res = new StringBuilder();
         for (int i = 0; i < length; i++) {
              int randIndex = rand.nextInt(aToZ.length());
              res.append(aToZ.charAt(randIndex));
         }
         return res.toString();
    }
 
    
//    @RequestMapping(value = "/api/login/logout", method = RequestMethod.POST)
//    public  ResponseLogout  logout(
//     ) 
//    {
//         User user = Auth.get();
//        
//    
//         System.out.println("User is " + user);
//          if(user != null)
//          {
//               user.setToken("");
//               userMapper.updateByPrimaryKey(user);
//               
//          }
//
//          ResponseLogout response = new ResponseLogout();
//
//
//
//        return response;
//    }
//
//
//
//    @RequestMapping(value = "/api/login/me", method = RequestMethod.GET)
//    public  Response<User> me(
//     ) 
//    {
//
//          User user = Auth.get();
//    
//          Response<User> response = new Response<>();
//          response.data=user;
//
//          return response;
//    }



         
}
