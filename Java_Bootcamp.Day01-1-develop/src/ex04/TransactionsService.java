import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TransactionsService {
    private final TransactionsLinkedList transactionsLinkedList;
    private final UsersArrayList usersArrayList;

    public TransactionsService(){
        this.transactionsLinkedList = new TransactionsLinkedList();
        this.usersArrayList = new UsersArrayList();
    }

    public void addUser(User user){
        usersArrayList.addUser(user);
    }
    public int getUsersBalance(User user){
        return user.getBalance();
    }
    public void performTransferTransaction(int senderId, int recipientId, int transferAmount) throws Exception{
        int senderIdBuff = senderId;
        int recipientIdBuff = recipientId;
        int transferAmountBuff = transferAmount;

        if(transferAmount<0) {
            senderIdBuff = recipientId;
            recipientIdBuff = senderId;
            transferAmountBuff = -transferAmountBuff;
        }
        if(transferAmountBuff>usersArrayList.getUserById(senderIdBuff).getBalance()){
            throw new Exception("IllegalTransactionException");
        } else {
            Transaction tr1 = new Transaction(usersArrayList.getUserById(senderIdBuff),
                    usersArrayList.getUserById(recipientIdBuff), "debit",
                    transferAmountBuff);
            transactionsLinkedList.addTransaction(tr1);
            Transaction tr2 = new Transaction(usersArrayList.getUserById(recipientIdBuff),
                    usersArrayList.getUserById(senderIdBuff), "credit",
                    -transferAmountBuff);
            tr2.setIdentifier(tr1.getIdentifier());
            transactionsLinkedList.addTransaction(tr2);
            usersArrayList.getUserById(senderIdBuff).setBalance(
                    usersArrayList.getUserById(senderIdBuff).getBalance()-transferAmountBuff);
            usersArrayList.getUserById(recipientIdBuff).setBalance(
                    usersArrayList.getUserById(recipientIdBuff).getBalance()+transferAmountBuff);
        }
    }
    public Transaction[] getTransfersOfUser(int userId){
        Transaction[] res;
        res = transactionsLinkedList.transformIntoArray();
        List<Transaction> list = new ArrayList<>(Arrays.asList(res));
        list.removeIf(tr -> tr.getSender().getIdentifier() != userId
                /*&& tr.getRecipient().getIdentifier() != userId*/);
        res = list.toArray(new Transaction[list.size()]);
        return res;
    }

    public void removeTransactionByUserId(int userId, UUID uuid){
        transactionsLinkedList.removeTransactionByUserId(userId, uuid);
    }

    public Transaction[] validateTransactions(){
        return transactionsLinkedList.validateTransactions();
    }
}
