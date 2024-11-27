package org.example;

import javax.xml.ws.Endpoint;

public class Deploy {
    public static void main(String[] args){
        String bindingURI = "http://localhost:8078/WebServiceProj";
        Service webService = new Service();
        Endpoint.publish(bindingURI, webService);
        System.out.println("Server started at: " + bindingURI);
    }
}
