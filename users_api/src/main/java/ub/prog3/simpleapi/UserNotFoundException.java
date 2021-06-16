package ub.prog3.simpleapi;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(Long id) {

        super("No se pudo hallar al usuario con el ID "+id);
    }
}
