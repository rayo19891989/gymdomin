/*
 * Copyright (C) 2014 Javier García Escobedo (javiergarbedo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.domin.beans;

import java.util.Date;



public class Cliente {
    private int id;
    private String nombre = "";
    private String apellidos = "";
    private String numtelefono = "";
    private String direccion = "";
    private String ciudad = "";
    private Date birthDate = null;
    private String photoFileName = "";

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellidos, 
            String numtelefono, String direccion, 
            String ciudad,Date birthDate, String photoFileName) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numtelefono = numtelefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.birthDate = birthDate;
        this.photoFileName = photoFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getSurnames() {
        return apellidos;
    }

    public void setSurnames(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPhoneNumber() {
        return numtelefono;
    }

    public void setPhoneNumber(String numtelefono) {
        this.numtelefono = numtelefono;
    }


    public String getAddress() {
        return direccion;
    }

    public void setAddress(String direccion) {
        this.direccion =direccion;
    }

    public String getCity() {
        return ciudad;
    }

    public void setCity(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}

