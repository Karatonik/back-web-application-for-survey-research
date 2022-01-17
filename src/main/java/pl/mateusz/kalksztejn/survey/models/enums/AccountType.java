package pl.mateusz.kalksztejn.survey.models.enums;

public enum AccountType {
    admin,
    consumer,
    creator;

    public static AccountType increaseType(AccountType accountType) {
        if (accountType.equals(AccountType.consumer)) {
            return AccountType.creator;
        }
        if (accountType.equals(AccountType.creator)) {
            return AccountType.admin;
        }
        return accountType;
    }

    public static AccountType reduceType(AccountType accountType) {
        if (accountType.equals(AccountType.creator)) {
            return AccountType.consumer;
        }
        if (accountType.equals(AccountType.admin)) {
            return AccountType.creator;
        }
        return accountType;
    }
}
