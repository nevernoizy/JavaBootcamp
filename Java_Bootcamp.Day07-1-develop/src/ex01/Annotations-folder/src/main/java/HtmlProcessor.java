import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.util.Set;
@SupportedAnnotationTypes({"HtmlInput","HtmlForm"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(HtmlProcessor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //System.out.println("PROCESS START");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(HtmlForm.class)){
            HtmlForm htmlForm = annotatedElement.getAnnotation(HtmlForm.class);
            String fileName = htmlForm.fileName();
            String action = htmlForm.action();
            String method = htmlForm.method();
            try(FileWriter writer = new FileWriter("target/classes/" + fileName, false)){
                writer.write(String.format("<form action=\"%s\" method=\"%s\">%n", action, method));

            System.out.println(fileName + " " + action + " " + method);
            for (Element annotatedElement1 : roundEnv.getElementsAnnotatedWith(HtmlInput.class)){
                HtmlInput htmlInput = annotatedElement1.getAnnotation(HtmlInput.class);
                String type = htmlInput.type();
                String name = htmlInput.name();
                String placeholder = htmlInput.placeholder();
                writer.write(String.format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">%n",
                        htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
                System.out.println(type + " " + name + " " + placeholder);
            }
                writer.write("\t<input type=\"submit\" value=\"Send\">\n");
                writer.write("</form>");
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }


        //System.out.println("PROCESS END");
        return true;
        //return false;
    }


}
