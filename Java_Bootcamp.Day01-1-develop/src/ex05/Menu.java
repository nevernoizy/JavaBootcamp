import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService transactionService;
    private final Scanner scanner;
    public Menu() {
        transactionService = new TransactionsService();
        scanner = new Scanner(System.in);
    }
    private void printMenu(){
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV – remove a transfer by ID");
        System.out.println("6. DEV – check transfer validity");
        System.out.println("7. Finish execution");
    }

    private void firstOption(){
        System.out.println("Enter a user name and a balance");
        String name = scanner.next();
        int balance = scanner.nextInt();
        User user1 = new User(name,balance);
        transactionService.addUser(user1);
    }
    private  void secondOption(){
        System.out.println("Enter a user ID");
        int userId = scanner.nextInt();
        try {
            User user1 = transactionService.getUserById(userId);
            System.out.println(user1.getUserName());
            System.out.println(user1.getBalance());
        } catch (Exception ex){
            System.out.println("user not found");
        }
    }
    private void thirdOption(){
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        int senderId = scanner.nextInt();
        int recipientId = scanner.nextInt();
        int transferAmount = scanner.nextInt();
        try{
            transactionService.performTransferTransaction(senderId, recipientId, transferAmount);
            System.out.println("The transfer is completed");
        } catch(Exception ex){
            System.out.println("wrong input data");
        }
    }
    private void fourthOption(){
        System.out.println("Enter a user ID");
        int userId = scanner.nextInt();
        Transaction[] transactionArray = transactionService.getTransfersOfUser(userId);
        for(Transaction tr: transactionArray){
            System.out.println("To " + tr.getRecipient().getUserName() + " " +
                    tr.getTransferAmount() + " with id = " + tr.getIdentifier());
        }
    }
    private void fifthOption(){
        System.out.println("Enter a user ID and a transfer ID");
        int userId = scanner.nextInt();
        String uuidStr = scanner.next();
        UUID uuid = UUID.fromString(uuidStr);

        //System.out.println(uuid);

        transactionService.removeTransactionByUserId(userId, uuid);
    }

    private void sixthOption(){
        System.out.println("Check results:");
        Transaction[] unvalidatedTransactionArray = transactionService.validateTransactions();
        for(Transaction tr : unvalidatedTransactionArray){
            System.out.println(tr.getSender().getUserName() +  "(id = " + tr.getSender().getIdentifier() +
                    ") has an unacknowledged transfer id = " + tr.getIdentifier() +
                    "from " + tr.getRecipient().getUserName() + "(id = " + tr.getRecipient().getIdentifier() +
                    ") for " + tr.getTransferAmount());
        }
    }
    private void mainFunc(int config){
        int n = 0;
        while(n!=7){
            printMenu();
            if(scanner.hasNextInt()) {
                n = scanner.nextInt();
                try {
                    switch (n) {
                        case 1:
                            firstOption();
                            break;
                        case 2:
                            secondOption();
                            break;
                        case 3:
                            thirdOption();
                            break;
                        case 4:
                            fourthOption();
                            break;
                        case 5:
                            if (config == 1) {
                                fifthOption();
                            } else {
                                System.out.println("only in dev mode");
                            }
                            break;
                        case 6:
                            if (config == 1) {
                                sixthOption();
                            } else {
                                System.out.println("only in dev mode");
                            }
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("wrong option");
                            break;
                    }
                } catch(Exception ex){
                    System.out.println("something was wrong");
                }
            } else {
                System.out.println("wrong option");
                scanner.next();
            }
            System.out.println("---------------------------");
        }
    }

    public void realManFunc(){
        System.out.println("type dev to enter dev mode or anything to default mode");
        String str = scanner.next();
        if (str.equals("dev")){
            mainFunc(1);
        } else {
            mainFunc(0);
        }
    }

}
