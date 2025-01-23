public class Program {
    public static void main(String[] args) throws Exception {
        User user1 = new User( "Jack", 500);
        User user2 = new User( "Nick", 700);
        Transaction tr1 = new Transaction(user1, user2, "debit", 100);
        Transaction tr2 = new Transaction(user1, user2, "debit", 200);
        Transaction tr3 = new Transaction(user2, user1, "credit", -300);
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();
        transactionsLinkedList.addTransaction(tr1);
        transactionsLinkedList.addTransaction(tr3);
        transactionsLinkedList.addTransaction(tr2);
        transactionsLinkedList.removeTransactionById(tr2.getIdentifier());
        Transaction[] array = transactionsLinkedList.transformIntoArray();
        for(Transaction tr : array){
            System.out.println(tr.getRecipient().getUserName());
            System.out.println(tr.getSender().getUserName());
            System.out.println(tr.getTransferCategory());
            System.out.println(tr.getTransferAmount());
            System.out.println(tr.getIdentifier());
        }
    }
}
