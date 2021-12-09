package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class GraphJsonDeserializer implements JsonDeserializer<DirectedWeightedGraph> {

    @Override
    public DirectedWeightedGraph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        DirectedWeightedGraph graph =new DirectedWeightedGraphImpl();
        JsonArray edges= jsonObject.get("Edges").getAsJsonArray();
        JsonArray nodes = jsonObject.get("Nodes").getAsJsonArray();

        for (JsonElement node : nodes) {
          int  key = node.getAsJsonObject().get("id").getAsInt();
          String geo = node.getAsJsonObject().get("pos").getAsString();

            String[] pos = geo.split(",");
            double  x = Double.parseDouble(pos[0]);
            double  y = Double.parseDouble(pos[1]);
            double  z = Double.parseDouble(pos[2]);
            GeoLocation geoLocation = new GeoLocationImpl(x,y,z);
            NodeData curr = new NodeDataImpl(geoLocation, key);
            graph.addNode(curr);
        }
        for (JsonElement edge : edges)
        {
         int src = edge.getAsJsonObject().get("src").getAsInt();
         double weight = edge.getAsJsonObject().get("w").getAsDouble();
         int dest = edge.getAsJsonObject().get("dest").getAsInt();
         graph.connect(src, dest, weight);

        }
        return (DirectedWeightedGraphImpl) graph;
    }


}