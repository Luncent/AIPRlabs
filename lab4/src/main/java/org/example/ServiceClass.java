package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class ServiceClass {
    @WebMethod
    public String bonjour(String name) {
        System.out.println(name);
        return String.format("Bonjour %s", name);
    }
}

