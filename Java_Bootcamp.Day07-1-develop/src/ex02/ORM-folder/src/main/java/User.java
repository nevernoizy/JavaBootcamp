@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", type = "text")
    private String firstName;
    @OrmColumn(name = "last_name", type = "text")
    private String lastName;
    @OrmColumn(name = "age", type = "integer")
    private Integer age;

    // setters/getters
}
