import java.util.logging.Level;
import java.util.logging.Logger;
import com.mongodb.client.MongoClients;

public class connect {
    private static com.mongodb.client.MongoClient mongoClient;

    public static com.mongodb.client.MongoClient getMongoClient() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb+srv://myblogapp:myblogapp@cluster0.7b9elpd.mongodb.net/test");
        }
        return mongoClient;
    }
}
