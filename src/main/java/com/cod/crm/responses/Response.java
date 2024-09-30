package com.cod.crm.responses;


//return respnse format data
public class Response<T>
{

    public String code = "SUCCESS";   // "SUCCESS"  or  other 
    public String message = "";  //message for user 

    // Data
   public T data  =null;
}