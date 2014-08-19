package actors;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import utils.Neo4jUtils;

/**
 * Created by yangjungis@126.com on 14-8-19.
 */
public abstract class InstrumentedActor extends ActorStack {
    protected GraphDatabaseService neo4j = Neo4jUtils.neo4j();
    protected ExecutionEngine engine = Neo4jUtils.engine();
}
