public class Program {
    public static void main(String[] args) throws Exception {
        User user1 = new User( "Jack", 500);
        User user2 = new User( "Nick", 700);
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(user1);
        transactionsService.addUser(user2);
        System.out.println(transactionsService.getUsersBalance(user1));
        transactionsService.performTransferTransaction(user1.getIdentifier(), user2.getIdentifier(),-200);
        transactionsService.performTransferTransaction(user1.getIdentifier(), user2.getIdentifier(),300);
        System.out.println(transactionsService.getUsersBalance(user1));
        Transaction[] array = transactionsService.getTransfersOfUser(user1.getIdentifier());
        for(Transaction tr : array){
            System.out.println(tr.getRecipient().getUserName());
            System.out.println(tr.getSender().getUserName());
            System.out.println(tr.getTransferCategory());
            System.out.println(tr.getTransferAmount());
            System.out.println(tr.getIdentifier());
        }
        System.out.println("---------------------");
        transactionsService.removeTransactionByUserId(user1.getIdentifier(), array[0].getIdentifier());
        Transaction[] array1 = transactionsService.validateTransactions();
        for(Transaction tr : array1){
            System.out.println(tr.getRecipient().getUserName());
            System.out.println(tr.getSender().getUserName());
            System.out.println(tr.getTransferCategory());
            System.out.println(tr.getTransferAmount());
            System.out.println(tr.getIdentifier());
        }
    }
}
