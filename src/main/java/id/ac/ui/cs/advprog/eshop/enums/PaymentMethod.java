package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentMethod {

    CASH_ON_DELIVERY("COD"),
    VOUCHER("VOUCHER"),
    BANK_TRANSFER("BANK_TRANSFER");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String method) {
        for (PaymentMethod m : values()) {
            if (m.value.equals(method)) {
                return true;
            }
        }
        return false;
    }

    public static PaymentMethod fromValue(String method) {
        for (PaymentMethod m : values()) {
            if (m.value.equals(method)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + method);
    }
}