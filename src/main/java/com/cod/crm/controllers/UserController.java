package com.cod.crm.controllers;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cod.crm.mapper.UsersMapper;
import com.cod.crm.model.Users;



@RestController
public class UserController {
	
	@Autowired
	private UsersMapper usersMapper;
	
	
	
	@RequestMapping(value = "/api/user/create", method = RequestMethod.POST)
	public HashMap<String,String> create(
			@RequestBody Users users
			)
    {
		

		
		HashMap<String,String> response = new HashMap<String,String>();
     
		
		// 确保用户名和密码不为空
	    if (users.getUsername() == null || users.getPassword() == null) {
	        response.put("error", "用户名和密码不能为空！");
	        return response;
	    }

		users.setCreatedAt(new Date());
		users.setUpdatedAt(new Date());
		
//    Users user=new Users();
//	user.setUsername("tiff");
//	user.setPassword("tiff");
		int result = usersMapper.insertSelective(users);
	
	if (result > 0) {
        response.put("id", users.getId().toString());
    } else {
        response.put("error", "用户创建失败，请重试。");
    }

    return response;
	
	
	}
	
	
//    @RequestMapping(value = "/api/custom/create", method = RequestMethod.POST)
//    public  Response<HashMap<String,String>>  create(
//         @RequestBody Custom custom
//         ) 
//    {
//
//          //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//          //String date= df.format(new Date());
//
//          custom.setCreatedAt(new Date());
//          custom.setUpdatedAt(new Date());
//
//         customMapper.insert(custom);
//
//         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();
//
//         response.data=new HashMap<String,String>();
//         response.data.put("id",custom.getId().toString());
//
//         return response;
//    }


	
	
	
	
	
	
	
	@RequestMapping(value = "/api/user/delete", method = RequestMethod.POST)
    public  HashMap<String,String>  delete(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {
		usersMapper.deleteByPrimaryKey(id);

         HashMap<String,String> response = new HashMap<String,String>();

//         response.data=new HashMap<String,String>();

         return response;
    }
	
	
	
	@RequestMapping(value = "/api/user/update", method = RequestMethod.POST)
    public  HashMap<String,String>  update(
         @RequestBody Users users
         ) 
    {
		  users=usersMapper.selectByPrimaryKey(users.getId());
		  users.setUsername("Tiff0322");
		  
          users.setUpdatedAt(new Date());
          usersMapper.updateByPrimaryKey(users);

         HashMap<String,String> response = new HashMap<String,String>();

//         response.data=new HashMap<String,String>();
//         response.data.put("id",custom.getId().toString());

         return response;
    }
	
	
	
    @RequestMapping(value = "/api/user/get", method = RequestMethod.GET)
    public  Users   get(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {

         Users users = usersMapper.selectByPrimaryKey(id);


//         response.data = custom;

         return users;
    }
    
    
    
//    @RequestMapping(value = "/api/user/list", method = RequestMethod.GET)
//    public  Response<PageInfo<Custom>>   list(
//         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageCount,
//         @RequestParam(value = "pageIndex", defaultValue = "1") Integer page,
//         @RequestParam(value = "name", defaultValue = "") String query
//  
//         ) 
//         {
//
//          if (!query.equals("")) query="%"+query+"%";
//          if (page < 1) page=1;
//          if (pageCount < 1) pageCount =1; 
//
//          CustomExample customExample = new CustomExample();
//
//          PageHelper.startPage(page,pageCount);    
//
//
//          User user=new User();
//
//
//          Criteria criteria = customExample.createCriteria();
//          if (!StringUtils.isEmptyOrWhitespace(query)) {
//                  criteria.andNameLike("%" + query + "%");    
//          }
//          
//          List<Custom> userList = customMapper.selectByExample(customExample);
//
//          PageInfo<Custom> pageInfo = new PageInfo<Custom>(userList);
//
//          Response<PageInfo<Custom>> response=new Response<PageInfo<Custom>>();
//
//
//          response.data=pageInfo;
//
//          return response;
//    }


}

