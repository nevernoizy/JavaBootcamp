public class RendererErrImpl implements Renderer{
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public void render(String str){
        System.err.println(preProcessor.process(str));
    }
}
