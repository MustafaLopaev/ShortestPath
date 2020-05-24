package com.company;
import utility.GraphInput;
import utility.KeyboardReader;
import utility.SimpleGraph;
import utility.Vertex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main{


    static final String START_END_POINT = "41";  //41 represents Kocaeli as list of cities starts from 1

    public static void main(String[] args) throws IOException {

        BufferedWriter storedRoads = new BufferedWriter(new FileWriter("storedRoads.txt", true));
        utility.SimpleGraph graph = new utility.SimpleGraph();
        Hashtable<String, utility.Vertex> table = GraphInput.LoadSimpleGraph(graph);
        ArrayList<String> citiesToVisit = new ArrayList<String>();
        String destinationName;

        //Clear table to check correctly!
        for(Object o: table.values()){
            utility.Vertex v = (utility.Vertex) o;
            v.setData(null);
        }
        System.out.println("As you know start point is Kocaeli! \n");

        // asks user to input ending vertexes
        System.out.print("Enter the name of the destination cities: \n");
        for(int i = 0; i < 10; i++) {
            System.out.print((i+1) + ": ");
            destinationName = KeyboardReader.readString();
            System.out.println("Enter 0 to stop! ");

            utility.Vertex checkList = table.get(destinationName);
            while (checkList == null) {
                if(destinationName.equals("0")) break;
                System.out.print("That name does not exist. Try again: ");
                destinationName = KeyboardReader.readString();
                checkList = table.get(destinationName);
            }


            if(destinationName.equals("0")) break;
            //Stores data to find path
            citiesToVisit.add(destinationName);
        }

        HeapAlgorithm heapAlgorithm = new HeapAlgorithm();
        List<List<String>> roadPerms =  heapAlgorithm.permute(citiesToVisit);
        //System.out.println(roadPerms); //all possible permutation of road
        for(List<String> roadToFollow: roadPerms){
            //System.out.println(roadToFollow);
            storedRoads.write("41 ");
            start(graph, table, (ArrayList<String>) roadToFollow, storedRoads);
        }
        storedRoads.close();

        //start(graph, table, citiesToVisit);
    }

    public static void start(SimpleGraph graph, Hashtable<String, Vertex> table, ArrayList<String> citiesToVisit, BufferedWriter storedRoads) throws IOException {

        //Setting start point as Kocaeli
        utility.Vertex originVertex = table.get(START_END_POINT);
        int totalDistance = 0;

        for(String city: citiesToVisit){
            utility.Vertex destinationVertex = table.get(city);
            // find the shortest path between the two vertices using the SimpleGraph ADT
            totalDistance += pathfinder.Dijkstra.findDijkstra(originVertex, destinationVertex, graph, storedRoads);

            for(Object o : table.values()){
                utility.Vertex v = (utility.Vertex) o;
                v.setData(null);
            }
            originVertex = table.get(city);
        }
        totalDistance += pathfinder.Dijkstra.findDijkstra(originVertex, table.get(START_END_POINT), graph, storedRoads);
        for(Object o : table.values()){
            utility.Vertex v = (utility.Vertex) o;
            v.setData(null);
        }
        //System.out.println("");
        storedRoads.write("\n" + totalDistance + "\n\n");
    }

}