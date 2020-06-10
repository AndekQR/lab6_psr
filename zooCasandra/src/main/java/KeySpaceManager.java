import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;

import java.time.Duration;


public class KeySpaceManager {

    final private static Duration TIMEOUT = Duration.ofSeconds(10);

    private final String keySpaceName;
    private final CqlSession cqlSession;

    public KeySpaceManager(CqlSession cqlSession, String keySpaceName) {
            this.cqlSession = cqlSession;
            this.keySpaceName = keySpaceName;
    }

    public void selectKeySpaces() {
        ResultSet resultSet = cqlSession.execute("SELECT keyspace_name FROM system_schema.keyspaces;");
        resultSet.forEach(System.out::println);
    }

    public void createKeySpace() {
        SimpleStatement statement=new SimpleStatementBuilder("CREATE KEYSPACE IF NOT EXISTS " + keySpaceName + " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};").build();
        this.executeSimpleStatement(statement);
    }

    public void useKeySpace() {
        SimpleStatement statement = new SimpleStatementBuilder("USE " + keySpaceName + ";").build();
        this.executeSimpleStatement(statement);
    }

    public void dropKeySpace(){
        SimpleStatement statement = new SimpleStatementBuilder("DROP KEYSPACE IF EXISTS \" + keyspaceName + \";").build();
        this.executeSimpleStatement(statement);
    }

    public void executeSimpleStatement(SimpleStatement simpleStatement) {
        this.cqlSession.execute(simpleStatement.setTimeout(TIMEOUT));
    }
}
