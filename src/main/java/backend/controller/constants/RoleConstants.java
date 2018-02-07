package backend.controller.constants;

public enum RoleConstants {

    //DO NOT ADD
    ADMIN(1),
    USER(2);

    private long id;

    RoleConstants(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
