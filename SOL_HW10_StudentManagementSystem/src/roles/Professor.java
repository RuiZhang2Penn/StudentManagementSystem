package roles;


/**
 * A class representing a professor.
 */
public class Professor extends User {

    /**
     * Creates a professor.
     * @param id of professor
     * @param name of professor
     * @param username of professor
     * @param password of professor
     */
    public Professor(String id, String name, String username, String password) {
        super(id, name, username, password);
    }
}
