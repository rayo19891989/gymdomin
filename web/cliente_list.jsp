<%@page import="java.io.FileReader"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="es.domin.beans.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <script type="text/javascript">
        h1{
            color:#640A08;
        }
         </script>
    </head>
    
    <body>
        
            <%
            out.println("Aplicación ejecutada en la máquina: ");
            try {
                BufferedReader br = new BufferedReader(new FileReader("/etc/hostname"));
                out.println("<b>" + br.readLine() + "</b>");
            } catch(FileNotFoundException ex) {
                out.println("<b>Nombre de servidor no encontrado</b><br>");
                out.println("Sólo se detectarán los nombres de servidores Ubuntu");
            }
        %>
    <center>
        <h1 style="background-color: #00cccc">Lista de clientes Gym Domin</h1>
        <table border="1" style="border: #00cccc">
            <tr>
                <th style="background-color:#00cccc">Nombre</th>
                <th style="background-color:#00cccc">Apellidos</th>
                <th style="background-color: #00cccc">Teléfono</th>
                <th style="background-color: #00cccc">Direccion</th>
                <th style="background-color: #00cccc">Ciudad</th>
                <th style="background-color: #00cccc">Fecha de Nacimiento</th>
              
                
            </tr>
        <% 
            ArrayList<Cliente> clienteList = (ArrayList)request.getAttribute("clienteList"); 
            for(Cliente cliente: clienteList) {
                out.println("<tr>");
                out.println("<td>"+cliente.getName()+"</td>");
                out.println("<td>"+cliente.getSurnames()+"</td>");
                out.println("<td>"+cliente.getPhoneNumber()+"</td>");
                out.println("<td>"+cliente.getAddress()+"</td>");
                out.println("<td>"+cliente.getCity()+"</td>");
                out.println("<td>"+cliente.getBirthDate()+"</td>");
                //Enlace para editar el registro
                String editLink = "Main?action=E&id="+cliente.getId();
                out.println("<td><a href='"+editLink+"'>Editar</td>");
                //Enlace para eliminar el registro con confirmación por parte del usuario
                String deleteLink = "Main?action=D&id="+cliente.getId();
                String deleteConfirmText = "Confirme que desea eliminar el contacto:\\n"+cliente.getName()+" "+cliente.getSurnames();
                out.println("<td><a href='"+deleteLink+"' onCLick='return confirm(\""+deleteConfirmText+"\")'>Suprimir</td>");
                
                out.println("</tr>");
            }
        %>
        </table>
        <br>
        <form method="get" action="Main">
            <input type="hidden" name="action" value="I">
            <input type="submit" value="Nuevo Contacto">
        </form>
        <form method="get" action="Main" target="_blank">
            <input type="hidden" name="action" value="X">
            <input type="submit" value="Exportar XML">
        </form>
    </center>
    </body>
</html>
