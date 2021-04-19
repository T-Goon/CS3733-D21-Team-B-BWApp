package edu.wpi.teamB;

import edu.wpi.teamB.database.*;
import edu.wpi.teamB.entities.Edge;
import edu.wpi.teamB.entities.Node;
import edu.wpi.teamB.entities.NodeType;
import edu.wpi.teamB.pathfinding.Graph;
import edu.wpi.teamB.util.CSVHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseHandlerTest {
    private static String resourcesPath;
    private static DatabaseHandler db;
    private static List<Node> testNodes;

    @BeforeAll
    static void initDB() {
        db = DatabaseHandler.getDatabaseHandler("test.db");
        resourcesPath = "/edu/wpi/teamB/database/load";
        Graph.setGraph(db);

        testNodes = new ArrayList<>();

        Node targetNode0 = new Node("bWALK00501", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode1 = new Node("bWALK00502", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode2 = new Node("bWALK00503", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode3 = new Node("bWALK00504", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode4 = new Node("bWALK00505", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode5 = new Node("bWALK00506", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode6 = new Node("bWALK00507", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode7 = new Node("bWALK00508", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode8 = new Node("bWALK00509", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode9 = new Node("bWALK00510", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode10 = new Node("bPARK00101", 179, 1895, "1", "Parking", "PARK", "Left Parking Lot Spot 1", "LLot1");
        testNodes.add(targetNode0);
        testNodes.add(targetNode1);
        testNodes.add(targetNode2);
        testNodes.add(targetNode3);
        testNodes.add(targetNode4);
        testNodes.add(targetNode5);
        testNodes.add(targetNode6);
        testNodes.add(targetNode7);
        testNodes.add(targetNode8);
        testNodes.add(targetNode9);
        testNodes.add(targetNode10);
    }

    @BeforeEach
    void resetDB() {
        db.loadDatabase(null, null);
    }

    @Test
    public void simpleParseNodes() {
        Node target = new Node("testNode",
                0,
                -992,
                "1",
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");
        Node actual = CSVHandler.loadCSVNodes(resourcesPath + "/SimpleTestNodes.csv").get(0);
        assertEquals(target.toString(), actual.toString());
    }

    @Test
    public void complexParseNodesLength() {
        List<Node> nodes = CSVHandler.loadCSVNodes(resourcesPath + "/ComplexTestNodes.csv");
        assertEquals(32, nodes.size());
    }

    @Test
    public void complexParseNodesValues() {
        Node target = new Node("bWALK00501", 1872, 1965, "1", "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        List<Node> nodes = CSVHandler.loadCSVNodes(resourcesPath + "/ComplexTestNodes.csv");
        List<String> expanded = nodes.stream().map(Node::toString).collect(Collectors.toList());
        assertTrue(expanded.contains(target.toString()));
    }

    @Test
    public void simpleParseEdges() {
        Edge target = new Edge("bPARK00101_bWALK00101", "bPARK00101", "bWALK00101");
        Edge actual = CSVHandler.loadCSVEdges(resourcesPath + "/SimpleTestEdges.csv").get(0);
        assertEquals(target.toString(), actual.toString());
    }

    @Test
    public void complexParseEdgesLength() {
        List<Edge> nodes = CSVHandler.loadCSVEdges(resourcesPath + "/ComplexTestEdges.csv");
        assertEquals(31, nodes.size());
    }

    @Test
    public void complexParseEdgesValues() {
        Edge target = new Edge("bPARK01201_bWALK00501", "bPARK01201", "bWALK00501");
        List<Edge> nodes = CSVHandler.loadCSVEdges(resourcesPath + "/ComplexTestEdges.csv");
        List<String> expanded = nodes.stream().map(Edge::toString).collect(Collectors.toList());
        assertTrue(expanded.contains(target.toString()));
    }

    @Test
    void fillDatabase() {
        List<Edge> edges = new ArrayList<>();

        Edge targetEdge = new Edge("bPARK01201_bWALK00501", "bWALK00502", "bWALK00501");
        edges.add(targetEdge);

        db.loadDatabase(testNodes, edges);
        Map<String, Node> outNodes = db.getNodes();
        assert (outNodes.values().containsAll(testNodes));
        Map<String, Edge> outEdges = db.getEdges();
        assertEquals(outEdges.values().toArray()[0], targetEdge);
    }

    @Test
    public void testUpdateNode() {
        List<Node> actual = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Node target = new Node("testNode",
                0,
                -992,
                "1",
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");
        actual.add(target);
        db.loadDatabase(actual, edges);

        String nodeID = target.getNodeID();
        int xcoord = 1;
        int ycoord = 2;
        String floor = "3";
        String building = "Parking";
        String nodeType = "PARK";
        String longName = "Left Parking Lot Spot 10";
        String shortName = "LLot10";
        db.updateNode(new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName));

        Map<String, Node> nodes = db.getNodes();
        assertEquals(1, nodes.get("testNode").getXCoord());
        assertEquals(2, nodes.get("testNode").getYCoord());
        assertEquals("3", nodes.get("testNode").getFloor());
        assertEquals("Parking", nodes.get("testNode").getBuilding());
        assertEquals("PARK", nodes.get("testNode").getNodeType());
        assertEquals("Left Parking Lot Spot 10", nodes.get("testNode").getLongName());
        assertEquals("LLot10", nodes.get("testNode").getShortName());
    }

    @Test
    public void testUpdateEdge() {
        List<Node> nodes = new ArrayList<>();
        List<Edge> actual = new ArrayList<>();
        Edge target = new Edge("bPARK01201_bWALK00501", "test_start", "test_end");
        Node start = new Node("test_start", 0, 0, "0", "0", "0", "test", "t");
        Node end = new Node("test_end", 0, 0, "0", "0", "0", "test", "t");
        actual.add(target);
        nodes.add(start);
        nodes.add(end);
        db.loadDatabase(nodes, actual);

        String edgeID = target.getEdgeID();
        String startNode = "test_start";
        String endNode = "test_end";
        db.updateEdge(new Edge(edgeID, startNode, endNode));

        Map<String, Edge> edges = db.getEdges();
        assertEquals("test_start", edges.get("bPARK01201_bWALK00501").getStartNodeID());
        assertEquals("test_end", edges.get("bPARK01201_bWALK00501").getEndNodeID());
    }

    @Test
    public void testAddNode() {
        Node target = new Node("testNode",
                0,
                -992,
                "1",
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");

        db.addNode(target);

        Map<String, Node> nodes = db.getNodes();
        assertEquals(0, nodes.get("testNode").getXCoord());
        assertEquals(-992, nodes.get("testNode").getYCoord());
        assertEquals("1", nodes.get("testNode").getFloor());
        assertEquals("test_building", nodes.get("testNode").getBuilding());
        assertEquals("NODETYPE", nodes.get("testNode").getNodeType());
        assertEquals("Name With Many Spaces", nodes.get("testNode").getLongName());
        assertEquals("N W M S", nodes.get("testNode").getShortName());
    }

    @Test
    public void testAddEdge() {
        Node start = new Node("test_start", 0, 0, "0", "0", "0", "test", "t");
        Node end = new Node("test_end", 0, 0, "0", "0", "0", "test", "t");

        db.addNode(start);
        db.addNode(end);

        Edge target = new Edge("bPARK01201_bWALK00501", "test_start", "test_end");


        db.addEdge(target);

        Map<String, Edge> edges = db.getEdges();
        assertEquals("test_start", edges.get("bPARK01201_bWALK00501").getStartNodeID());
        assertEquals("test_end", edges.get("bPARK01201_bWALK00501").getEndNodeID());
    }

    @Test
    public void testRemoveNode() {
        Node target = new Node("testNode",
                0,
                -992,
                "1",
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");

        db.addNode(target);
        assertFalse(db.getNodes().isEmpty());

        db.removeNode(target.getNodeID());
        assertTrue(db.getNodes().isEmpty());
    }

    @Test
    public void testRemoveEdge() {
        Node start = new Node("test_start", 0, 0, "0", "0", "0", "test", "t");
        Node end = new Node("test_end", 0, 0, "0", "0", "0", "test", "t");
        db.addNode(start);
        db.addNode(end);

        Edge target = new Edge("bPARK01201_bWALK00501", "test_start", "test_end");

        db.addEdge(target);
        assertFalse(db.getEdges().isEmpty());

        db.removeEdge(target.getEdgeID());
        assertTrue(db.getEdges().isEmpty());
    }

    @Test
    public void testGetNodesByCategory() {
        db.loadDatabaseNodes(testNodes);

        List<Node> result = db.getNodesByCategory(NodeType.PARK);
        List<Node> expected = new ArrayList<>();
        expected.add(testNodes.get(10));

        assertEquals(expected, result);


    }
}