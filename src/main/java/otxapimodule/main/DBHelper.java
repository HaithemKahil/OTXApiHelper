package otxapimodule.main;

import com.alienvault.otx.model.pulse.Pulse;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DBHelper {

    private MongoClient client;

    private MongoDatabase otxDatabase;
    public void connect(){
        client = new MongoClient("localhost", 27017);
        otxDatabase = client.getDatabase("OTXDatabase");
        System.out.println("Connected");
    }


    public void createCollection(String collectionName){
        otxDatabase.createCollection(collectionName);
        System.out.println("Collection created");
    }

    public MongoCollection<Document> getCollection(String collectionName){
        return otxDatabase.getCollection(collectionName);
    }

    public void insertPulseIfNotExists(MongoCollection collection,Document document){
        MongoCursor<Document> doc = collection.find(document).iterator();
        if(!doc.hasNext())collection.insertOne(document);
    }

    public static Pulse documentToPulse(Document doc){
        Pulse p = new Pulse();
        p.setName(doc.getString("Name"));
        p.setId(doc.getString("Id"));
        p.setDescription(doc.getString("Description"));
        p.setTlp(doc.getString("Tlp"));

        return p;
    }

    public static Document pulseToDocument(Pulse p){
        Document document = new Document().append("Id",p.getId()).append("Name",p.getName()).append("Description",p.getDescription()).append("Tlp",p.getTlp());
        return document;
    }

}
