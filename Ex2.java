import api.*;
/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        DirectedWeightedGraphAlgorithms algo = new DirectedWeightedGraphAlgorithmsImpl();
        algo.init(g);
        algo.load(json_file);
        DirectedWeightedGraph ans = algo.copy();
        return ans;
    }

    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        DirectedWeightedGraphAlgorithms algo = new DirectedWeightedGraphAlgorithmsImpl();
        algo.init(getGrapg(json_file));
        algo.load(json_file);
        DirectedWeightedGraphAlgorithms ans = algo;
        return ans;
    }

    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        new GUI(alg.getGraph());
    }

    public static void main(String[] args) {
        String file = args[0];
        runGUI(file);

    }


}