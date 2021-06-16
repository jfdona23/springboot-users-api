package ub.prog3.simpleapi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(produces = "application/json; charset=utf-8")
public class MainController {

    @Autowired
    private UserRepository repository;
    
    // Ruta por default
    @GetMapping("*")
    public String defaultRoute() {
        return "{\"msg\": \"URL incorrecto\"}";
    }

    // Listar usuarios
    @GetMapping("/usuarios")
    public List<UserEntity> getAllUsers() {
        Logger logger = LoggerFactory.getLogger(MainController.class);
        logger.info("Listando todos los usuarios");

        return repository.findAll();
    }

    // Listar solo un usuario por su ID
    @GetMapping("/usuarios/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        Logger logger = LoggerFactory.getLogger(MainController.class);
        logger.info("Mostrando la informacion del usuario: "+id);

        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Agregar una lista de usuarios
    @PostMapping("/usuarios")
    public String addUsers(@RequestBody List<UserEntity> usuarios) {
        Logger logger = LoggerFactory.getLogger(MainController.class);

        for (UserEntity usuario : usuarios) {
            logger.info("Agregando al usuario "+usuario.nombre);
            repository.save(usuario);
        }
        return "{\"msg\": \"Usuarios agregados correctamente\"}";
    }

    // Modificar un usuario
    @PutMapping("/usuarios/{id}")
    public UserEntity updateUserById(@RequestBody UserEntity nuevoUsuario, @PathVariable Long id) {
        Logger logger = LoggerFactory.getLogger(MainController.class);
        logger.info("Modificando el usuario con el id "+id);

        return repository.findById(id).map(usuario -> {
            usuario.setNombre(nuevoUsuario.getNombre());
            usuario.setPwd(nuevoUsuario.getPwd());
            return repository.save(usuario);
        }).orElseGet(() -> {
            logger.info("El usuario con el id "+id+" no existe...creando nuevo usuario");
            return repository.save(nuevoUsuario);
        });
    }

    // Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    public void deleteUserById(@PathVariable Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new UserNotFoundException(id);
        }
    }
}
