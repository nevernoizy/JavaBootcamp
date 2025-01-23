import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer{
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void print(String str){
        renderer.render(LocalDateTime.now() + " " + str);
    }
}
