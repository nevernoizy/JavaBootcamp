import java.util.*;

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

    public void removeTransactionByUserId(int id, UUID uuid) {
        transactionList.removeIf(tr -> tr.getIdentifier().equals(uuid) && tr.getSender().getIdentifier() == id);
    }
    public  Transaction[] validateTransactions(){
        LinkedList<UUID> duplicates = new LinkedList<>();
        LinkedList<Transaction> unique = new LinkedList<>();
        Set<UUID> set = new HashSet<>();
        for(Transaction tr : transactionList){
            if(!set.add(tr.getIdentifier())){
                duplicates.add(tr.getIdentifier());
            }
        }
        for(Transaction tr : transactionList){
            if(!duplicates.contains(tr.getIdentifier())){
                unique.add(tr);
            }
        }
        Transaction[] res = new Transaction[unique.size()];
        unique.toArray(res);
        return res;
    }

}
