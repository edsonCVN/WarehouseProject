package ggc.core;

public class Acquisition extends Transaction {

    public Acquisition(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    }

    public boolean isPaid() {
        return true;
    }

    public String toString() {
        return "COMPRA|" + super.getId() + "|" + super.getPartner().getKey() + "|" + super.getProduct().getId() + "|"
                + super.getQuantity() + "|" + Math.round(getBaseValue()) * super.getQuantity() + "|"
                + super.getTransactionDate().getDate();
    }

}
