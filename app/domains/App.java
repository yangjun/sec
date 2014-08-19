package domains;

import java.io.Serializable;

/**
 * Created by yangjungis@126.com on 14-8-19.
 */
public class App implements Serializable {

    private String name;

    private String did;

    public App(String name, String did) {
        this.name = name;
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public String getDid() {
        return did;
    }
}
