package ub.prog3.simpleapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.codec.digest.DigestUtils;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nombre;
    public String pwd;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPwd() {
        return pwd;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPwd(String pwd) {
        this.pwd = DigestUtils.sha256Hex(pwd);
    }
}
