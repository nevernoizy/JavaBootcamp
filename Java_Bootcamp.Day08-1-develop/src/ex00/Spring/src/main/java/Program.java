import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "context.xml");
        Printer printer = context.getBean("PrinterWithDateErrToLower", Printer.class);
        printer.print("Hello!");

    }
}
