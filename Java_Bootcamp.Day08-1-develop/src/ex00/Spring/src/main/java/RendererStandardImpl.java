public class RendererStandardImpl implements Renderer{
    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public void render(String str){
        System.out.println(preProcessor.process(str));
    }
}
