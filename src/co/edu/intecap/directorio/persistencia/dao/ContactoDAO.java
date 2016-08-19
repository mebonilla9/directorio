/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.intecap.directorio.persistencia.dao;

import co.edu.intecap.directorio.persistencia.conexion.ConexionBD;
import co.edu.intecap.directorio.persistencia.vo.Contacto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author incap001
 */
public class ContactoDAO {

    private Connection cnn;

    public ContactoDAO(Connection cnn) {
        this.cnn = cnn;
    }

    public void insertar(Contacto entidad) {
        try {
            String sql = "insert into contacto (nombre,correo,telefono,id_tipo_contacto) values(?,?,?,?)";
            PreparedStatement sentencia = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, entidad.getNombre());
            sentencia.setString(2, entidad.getCorreo());
            sentencia.setString(3, entidad.getTelefono());
            sentencia.setLong(4, entidad.getIdTipoContacto());
            sentencia.executeUpdate();
            ResultSet resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                entidad.setIdContacto(resultado.getLong(1));
            }
        } catch (Exception e) {
            System.out.println("Error al Insertar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
    }

    public void editar(Contacto entidad) {
        try {
            String sql = "update contacto set nombre = ?, correo = ?, telefono = ?, id_tipo_contacto = ? where id_contacto = ?";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            sentencia.setString(1, entidad.getNombre());
            sentencia.setString(2, entidad.getCorreo());
            sentencia.setString(3, entidad.getTelefono());
            sentencia.setLong(4, entidad.getIdTipoContacto());
            sentencia.setLong(5, entidad.getIdContacto());
            sentencia.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al Modificar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
    }

    public List<Contacto> consultar() {
        List<Contacto> listaContactos = new ArrayList<>();
        try {
            String sql = "select * from contacto";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Contacto miContacto = new Contacto();
                miContacto.setIdContacto(resultado.getLong("id_contacto"));
                miContacto.setNombre(resultado.getString("nombre"));
                miContacto.setCorreo(resultado.getString("correo"));
                miContacto.setTelefono(resultado.getString("telefono"));
                miContacto.setIdTipoContacto(resultado.getLong("id_tipo_contacto"));
                listaContactos.add(miContacto);
            }
        } catch (Exception e) {
            System.out.println("Error al Consultar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
        return listaContactos;
    }

    public Contacto consultar(long id) {
        Contacto miContacto = new Contacto();
        try {
            String sql = "select * from contacto where id_contacto = ?";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            sentencia.setLong(1, id);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                miContacto.setIdContacto(resultado.getLong("id_contacto"));
                miContacto.setNombre(resultado.getString("nombre"));
                miContacto.setCorreo(resultado.getString("correo"));
                miContacto.setTelefono(resultado.getString("telefono"));
                miContacto.setIdTipoContacto(resultado.getLong("id_tipo_contacto"));
            }
        } catch (Exception e) {
            System.out.println("Error al Consultar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
        return miContacto;
    }
    
    public void eliminar(Contacto entidad){
        try {
            String sql = "delete from contacto where id_contacto = ?";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            sentencia.setLong(1, entidad.getIdContacto());
            sentencia.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al Eliminar");
            e.printStackTrace(System.err);
        } finally{
            ConexionBD.desconectar(cnn);
        }
    }

}
