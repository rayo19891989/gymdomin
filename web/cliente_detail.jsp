<%@page import="es.domin.beans.Cliente"%>
<%@page import="es.domin.clientes.Main"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    //En request se reciben los datos enviados desde Main
    Cliente cliente = (Cliente)request.getAttribute("cliente");    
    String action = request.getParameter("action");    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body style="color:#00cccc">
    <center>
        
        <h1>Lista de clientes Gym Domin</h1>
        
        <table>
        <!-- Se añade enctype="multipart/form-data" para la subida de archivos -->
        <form method="post" action="Main" enctype="multipart/form-data">
        <!--<form method="post" action="Main">-->
            <input type="hidden" name="id" value="<%=cliente.getId()%>">
            <tr>
                <td>Nombre:</td>
                <td><input type="text" name="nombre" value="<%=cliente.getName()%>"></td>
                
            </tr>
            
            <tr>
                <td>Apellidos:</td>
                <td><input type="text" name="apellidos" value="<%=cliente.getSurnames()%>"></td>
                
            </tr>
            
            <tr>
                <td>Teléfono:</td>
                <td><input type="text" name="numtelefono" value="<%=cliente.getPhoneNumber()%>"></td>
                
            </tr>
            
            <tr>
                <td>Dirección:</td>
                <td><input type="text" name="direccion" value="<%=cliente.getAddress()%>"></td>
                
            </tr>
            
            <tr>
                <td>Ciudad:</td>
                <td> <input type="text" name="ciudad" value="<%=cliente.getCity()%>"></td>
                
            </tr>
            
            
            <% 
                String strBirthDate = "";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if(cliente.getBirthDate()!=null) {
                    strBirthDate = dateFormat.format(cliente.getBirthDate());
                }
                
            %>
            <tr>
                <td>Fecha nacimiento:</td>
                <td> <input type="text" name="birth_date" value="<%=strBirthDate%>"></td>
                
            </tr>
            
            <tr>
                <td>Foto:</td>
                <td> <img src='<%=Main.SAVE_DIR+"/"+cliente.getPhotoFileName()%>' width="128px"></td>
                
            </tr>
             </table>
            
             
            
            
               <br>
            <input type="checkbox" name="deletePhoto">Borrar foto (tendrá efecto después de guardar)<br>
            <input type="file" name="photoFileName"><br><br>
            
            <%  //Botón guardar para editar o insertar
                if(action.equals(Main.ACTION_EDIT_REQUEST)) {
                    out.print("<input type='submit' value='Guardar'>");
                    out.print("<input type='hidden' name='action' value='"+Main.ACTION_EDIT_RESPONSE+"'>");
                } else if(action.equals(Main.ACTION_INSERT_REQUEST)) {
                    out.print("<input type='submit' value='Añadir'>");
                    out.print("<input type='hidden' name='action' value='"+Main.ACTION_INSERT_RESPONSE+"'>");
                }
            %>
        </form>
        
        <%-- Botón para Cancelar cambios.
            Para que se muestre de nuevo la lista no hay que indicar 
            ninguna acción y volver a cargar Main --%>
        <form method="post" action="Main">
            <input type="hidden" name="action" value="">
            <input type="submit" value="Cancelar">
        </form>
    </center>
       
    </body>
</html>
