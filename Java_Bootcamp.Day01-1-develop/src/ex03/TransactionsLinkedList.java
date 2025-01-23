import java.util.LinkedList;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList{
    static private final LinkedList<Transaction> transactionList= new LinkedList<>();
    @Override
    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    @Override
    public void removeTransactionById(UUID uuid) throws Exception {
        for(Transaction tr : transactionList){
            if(tr.getIdentifier() == uuid){
                transactionList.remove(tr);
                return;
            }
        }
        throw new Exception("TransactionNotFoundException");
    }

    @Override
    public Transaction[] transformIntoArray() {
        Transaction[] res = new Transaction[transactionList.size()];
        transactionList.toArray(res);
        return res;
    }

}
