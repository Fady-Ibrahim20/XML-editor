/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
   
   import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.Test;
import  static org.junit.Assert.*;
import java.awt.Color;
   

/**
 *
 * @author Fady Ibrahim
 */

public class Graph {
    public static int graph(ArrayList<String> string, int [][] adjmat,int [] ids){
        int id=1,follower=1,num_of_users=0,l=1;
        String temp;
        for(int i=0;i<string.size();i++){

            if(string.get(i).matches("<user>")){
                i=i+2;
                id=Character.getNumericValue(string.get(i).charAt(0));
                ids[l]=id;
                num_of_users++;
                l++;
            }if(string.get(i).matches("<follower>")){
                i=i+2;
                follower=Character.getNumericValue(string.get(i).charAt(0));
                adjmat[id][follower]=1;
            }
        }
        return num_of_users;
    }

    public  static DefaultDirectedGraph<String, DefaultEdge> createGraph(int [][] arr,int []ids ,int users)
    {
        try{
            File imgFile = new File("graph.png");
            imgFile.createNewFile();
        }catch(IOException ex){
            System.out.print("error");
        }
        DefaultDirectedGraph<String, DefaultEdge> directedGraph
                = new DefaultDirectedGraph<>(DefaultEdge.class);
        ArrayList<String> string=new ArrayList<String>();
        string.add("s");
        for(int i=1;i<=users;i++){
            string.add(String.valueOf(ids[i]));

        }
        for(int i=1;i<=users;i++){
            directedGraph.addVertex(string.get(i));

        }

        for(int i=1;i<=users;i++){
            for(int j=1;j<=users;j++){
                if(arr[i][j] == 1){
                    directedGraph.addEdge(string.get(i), string.get(j));


                }
            }
        }
        return directedGraph;
    }

    public static void visualize(DefaultDirectedGraph<String, DefaultEdge> g) throws IOException {

        JGraphXAdapter<String, DefaultEdge> graphAdapter =
                new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("graph.png");
        ImageIO.write(image, "PNG", imgFile);
        assertTrue(imgFile.exists());
    }
}
        
