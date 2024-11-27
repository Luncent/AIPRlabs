
package org.example.cont;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Service", targetNamespace = "http://example.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Service {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://example.org/Service/getFileContentRequest", output = "http://example.org/Service/getFileContentResponse")
    public String getFileContent(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns double
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://example.org/Service/calcRequest", output = "http://example.org/Service/calcResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://example.org/Service/calc/Fault/Exception")
    })
    public double calc(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0)
        throws Exception_Exception
    ;

    /**
     * 
     * @return
     *     returns cont.StringArray
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://example.org/Service/getFilesRequest", output = "http://example.org/Service/getFilesResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://example.org/Service/getFiles/Fault/IOException")
    })
    public StringArray getFiles()
        throws IOException_Exception
    ;

}