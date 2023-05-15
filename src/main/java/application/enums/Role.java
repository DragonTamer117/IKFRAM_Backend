package application.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    GUEST(0),
    CUSTOMER(1),
    MODERATOR(2),
    ADMIN(3);

    private final int value;

    public int getValue() {
        return value;
    }
}
