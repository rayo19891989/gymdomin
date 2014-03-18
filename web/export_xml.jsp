<?xml version="1.0" encoding="UTF-8"?>
<%-- La línea anterior debe ir siempre la primera si se genera un XML --%>

<%@page import="java.util.ArrayList"%>
<%@page import="es.domin.beans.Cliente"%>

<%-- Se informa que el contenido va a ser XML --%>
<%@page contentType="text/xml" pageEncoding="UTF-8"%>

<cliente>
<% 
    ArrayList<Cliente> clienteList = (ArrayList)request.getAttribute("clienteList"); 
    for(Cliente cliente: clienteList) {
        out.println("<cliente>");
        out.println("<id>"+cliente.getId()+"</id>");
        out.println("<nombre>"+cliente.getName()+"</nombre>");
        out.println("<apellidos>"+cliente.getSurnames()+"</apellidos>");
        out.println("<numtelefono>"+cliente.getPhoneNumber()+"</numtelefono>");
        out.println("<direccion>"+cliente.getAddress()+"</direccion>");
        out.println("<ciudad>"+cliente.getCity()+"</ciudad>");
        out.println("<birth_date>"+cliente.getBirthDate()+"</birth_date>");
        out.println("<photo_file_name>"+cliente.getPhotoFileName()+"</photo_file_name>");
        out.println("</cliente>");
    }
%>
</cliente>
