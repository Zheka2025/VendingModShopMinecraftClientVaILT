package net.milkbowl.vault.economy;

public class EconomyResponse
{
    public final double amount;
    public final double balance;
    public final ResponseType type;
    public final String errorMessage;
    
    public EconomyResponse(final double amount, final double balance, final ResponseType type, final String errorMessage) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }
    
    public boolean transactionSuccess() {
        switch (this.type) {
            case SUCCESS: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public enum ResponseType
    {
        SUCCESS(1), 
        FAILURE(2), 
        NOT_IMPLEMENTED(3);
        
        private int id;
        
        private ResponseType(final int id) {
            this.id = id;
        }
        
        int getId() {
            return this.id;
        }
    }
}
