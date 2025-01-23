import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.sql.*;
@SupportedAnnotationTypes({"OrmColumnId","OrmColumn","OrmEntity"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(OrmManager.class)
public class OrmManager extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Connection connection = null;
            connection = getConnection();


        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(OrmEntity.class)){
            OrmEntity ormEntity = annotatedElement.getAnnotation(OrmEntity.class);
            String table = ormEntity.table();
            System.out.println(table);
            String query1 = "CREATE TABLE IF NOT EXISTS " + table + " (id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY);";

            for (Element annotatedElement1 : roundEnv.getElementsAnnotatedWith(OrmColumnId.class)){
                OrmColumnId ormColumnId = annotatedElement1.getAnnotation(OrmColumnId.class);
                try(PreparedStatement preparedStatement = connection.prepareStatement(query1)){
                    preparedStatement.executeUpdate();
                    System.out.println(query1);
                } catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }

            for (Element annotatedElement2 : roundEnv.getElementsAnnotatedWith(OrmColumn.class)){
                OrmColumn ormColumn = annotatedElement2.getAnnotation(OrmColumn.class);
                String name = ormColumn.name();
                String type = ormColumn.type();
                String alter = "ALTER TABLE "+ table +" ADD COLUMN " + name + " " + type + ";";
                try(PreparedStatement preparedStatement = connection.prepareStatement(alter)){
                    preparedStatement.executeUpdate();
                    System.out.println(alter);
                } catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }

        }
        return true;
    }
    public void save(Object entity){}

    public void update(Object entity){}

    //public <T> T findById(Long id, Class<T\> aClass){}
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:test;","sa", "sa");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
        return connection;

    }

}
