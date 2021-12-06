package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class GraphJsonDeserializer implements JsonDeserializer<DirectedWeightedGraph> {

    @Override
    public DirectedWeightedGraph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        DirectedWeightedGraph G=new DirectedWeightedGraphImpl();

        JsonObject edge = jsonObject.get("Edges").getAsJsonObject();
        JsonObject node = jsonObject.get("Nodes").getAsJsonObject();

        for (Map.Entry<String, JsonElement> set : node.entrySet())
        {
            JsonElement jsonValueElement = set.getValue(); //the value of the hashmap as json element
            String pos = jsonValueElement.getAsJsonObject().get("pos").getAsString();
            String[] posSplite=pos.split(",");
            Double[] DoublePos=new Double[3];
            for (int i = 0; i <3 ; i++) {
                DoublePos[i]= Double.valueOf(posSplite[i]);
            }
            GeoLocation geo=new GeoLocationImpl(DoublePos[0],DoublePos[1],DoublePos[2]) ;
            int id = jsonValueElement.getAsJsonObject().get("id").getAsInt();
            NodeData add=new NodeDataImpl(geo,id);
            G.addNode(add);
        }
        for (Map.Entry<String, JsonElement> set : edge.entrySet())
        {
            JsonElement jsonValueElement = set.getValue(); //the value of the hashmap as json element
            int src = jsonValueElement.getAsJsonObject().get("src").getAsInt();
            int w = jsonValueElement.getAsJsonObject().get("w").getAsInt();
            int dest = jsonValueElement.getAsJsonObject().get("dest").getAsInt();
            G.connect(src,dest,w);
        }

        return G;
    }
}