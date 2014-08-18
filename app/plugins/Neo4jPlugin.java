package plugins;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Application;
import play.Logger;
import play.Plugin;
import utils.Neo4jUtils;

/**
 * Created by yangjungis@126.com on 14-8-18.
 */
public class Neo4jPlugin extends Plugin {

    private final Application app;

    private  Logger.ALogger logger = Logger.of(Neo4jPlugin.class);

    public Neo4jPlugin(Application app) {
        this.app = app;

    }


    @Override
    public void onStart() {
        Logger.info("Neo4j Plugin start...");
        Config config = ConfigFactory.load("neo4j");
        Neo4jUtils.init(config);
    }

    @Override
    public void onStop() {
        Logger.info("Neo4j shutdown...");
        Neo4jUtils.shutdown();
    }

    @Override
    public boolean enabled() {
        return true;
    }
}
