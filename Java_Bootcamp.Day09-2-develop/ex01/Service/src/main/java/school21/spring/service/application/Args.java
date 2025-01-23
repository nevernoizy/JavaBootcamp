package school21.spring.service.application;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--server-port", required = true)
    private Integer port;

    public Integer getPort() {
        return port;
    }
}
