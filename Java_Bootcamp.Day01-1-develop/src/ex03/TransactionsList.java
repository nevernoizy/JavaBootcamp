import java.util.UUID;

interface TransactionsList {
    void addTransaction(Transaction transaction);
    void removeTransactionById(UUID uuid) throws Exception;
    Transaction[] transformIntoArray();
}
