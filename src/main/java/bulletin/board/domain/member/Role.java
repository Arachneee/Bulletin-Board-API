package bulletin.board.domain.member;

public enum Role {
    USER,
    MANAGER,
    ADMIN;

    public boolean isAdmin() {
        return this.equals(ADMIN);
    }
}