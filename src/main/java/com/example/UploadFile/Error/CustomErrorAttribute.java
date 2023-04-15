package com.example.UploadFile.Error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Map;

@Component
public class CustomErrorAttribute extends DefaultErrorAttributes {//response of client

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);//return map contain set of attribute suach as error,message,path

        errorAttributes.put("locale", webRequest.getLocale().toString());
        errorAttributes.put("success", Boolean.FALSE);
        errorAttributes.put("status", errorAttributes.get("error"));
        errorAttributes.put("exception", errorAttributes.get("message"));
        errorAttributes.put("details", Arrays.asList(errorAttributes.get("message")));
        errorAttributes.remove("error");
        errorAttributes.remove("path");

        return errorAttributes;



    }
}
