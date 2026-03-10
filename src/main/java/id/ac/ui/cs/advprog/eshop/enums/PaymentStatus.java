package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentStatus {

    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED"),
    FAILED("FAILED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String status) {
        for (PaymentStatus s : values()) {
            if (s.value.equals(status)) {
                return true;
            }
        }
        return false;
    }

    public static PaymentStatus fromValue(String status) {
        for (PaymentStatus s : values()) {
            if (s.value.equals(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid payment status: " + status);
    }
}