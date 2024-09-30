package com.cod.crm.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cod.crm.mapper.CustomersMapper;
import com.cod.crm.model.Customers;
import com.cod.crm.model.CustomersExample;
import com.cod.crm.model.CustomersExample.Criteria;
import com.cod.crm.model.Users;
import com.cod.crm.responses.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class CustomerController {

    @Autowired
    private CustomersMapper customersMapper;

    //@Autowired
    //private CustomExample customExample;

    protected static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
    @RequestMapping(value = "/api/customers/list", method = RequestMethod.GET)
    public  Response<PageInfo<Customers>>   list(
         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageCount,
         @RequestParam(value = "pageIndex", defaultValue = "1") Integer page,
         @RequestParam(value = "name", defaultValue = "") String query
  
         ) 
         {

          
          if (page < 1) page=1;
          if (pageCount < 1) pageCount =1; 

          CustomersExample customersExample = new CustomersExample();

          PageHelper.startPage(page,pageCount);    


          Users users=new Users();


          Criteria criteria = customersExample.createCriteria();
//          if (!StringUtils.isEmptyOrWhitespace(query)) {
//                  criteria.andNameLike("%" + query + "%");    
//          }
          
          System.out.println(query);

          
          if (query != null && !query.isBlank() && isInteger(query)) {
        	  //query是id
        	  System.out.println(isInteger(query));
        	    criteria.andIdEqualTo(Integer.valueOf(query)); // 根据 id 过滤
        	} else if (query != null && !query.isBlank()) {
        	    // 处理 name 查询
        	    criteria.andNameLike("%" + query + "%");
        	    System.out.println(isInteger(query));
        	}
          if (!query.equals("")) query="%"+query+"%";

          


          
          List<Customers> userList = customersMapper.selectByExample(customersExample);

          PageInfo<Customers> pageInfo = new PageInfo<Customers>(userList);

          Response<PageInfo<Customers>> response=new Response<PageInfo<Customers>>();


          response.data=pageInfo;

          return response;
    }

 
    
    @RequestMapping(value = "/api/customers/get", method = RequestMethod.GET)
    public  Response<Customers>  get(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {

         Customers customers = customersMapper.selectByPrimaryKey(id);

         Response<Customers> response = new Response<Customers>();

         response.data = customers;

         return response;
    }


    @RequestMapping(value = "/api/customers/create", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  create(
         @RequestBody Customers customers
         ) 
    {

          //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          //String date= df.format(new Date());

          customers.setCreatedAt(new Date());
          customers.setUpdatedAt(new Date());

         customersMapper.insert(customers);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();
         response.data.put("id",customers.getName().toString());

         return response;
    }


    @RequestMapping(value = "/api/customers/update", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  update(
         @RequestBody Customers customers
         ) 
    {
          customers.setUpdatedAt(new Date());
         customersMapper.updateByPrimaryKey(customers);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();
         response.data.put("id",customers.getId().toString());

         return response;
    }



    @RequestMapping(value = "/api/customers/delete", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  delete(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {
         customersMapper.deleteByPrimaryKey(id);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();

         return response;
    }
         
}