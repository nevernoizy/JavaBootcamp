public class PrinterWithPrefixImpl implements Printer{
    private final Renderer renderer;
    private String prefix = "prefix";

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void print(String str){
        renderer.render(prefix + " " + str);
    }
    public void setPrefix(String str){
        this.prefix = str;
    }
}
