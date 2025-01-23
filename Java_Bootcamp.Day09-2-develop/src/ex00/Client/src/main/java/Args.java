import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--port", required = true)
    private Integer port;

    public Integer getPort() {
        return port;
    }
}

