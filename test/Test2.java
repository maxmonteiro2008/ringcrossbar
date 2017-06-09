package ucsd.crossbarring.matrix2;


import java.util.Stack;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         
		//Time Measurement
		 Instant start, stop;
		 start = Instant.now();
		 
		  //======  SETUP  =====
		 // Main data structures
		 int n , l, ctrl, aux_id = 0;
		 boolean inicio = true;
		 Node node; //Auxiliary Node object to pop qNode queue
		 FileWriter outputStream = null;
		 
		
		 
	 	 	 	 	 
	 	 // Getting input arguments
	 	 n = Integer.parseInt(args[0]);
	     l = Integer.parseInt(args[1]);
	   
	    /* try {
			outputStream = new FileWriter("c:/test/barrings.txt");
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    
	     System.out.println("Beginning Calculations - "+ "Matrix Base: " + n +" #Nodes: " + n*n+ " Lasers: " + l);
	     // Creating a NodeList
	     NodeList nodeList = new NodeList (n, (byte)4, (byte)l);
	     Node[] b = new Node[2*n-2];
	     for (int i=0; i<2*n-2;i++){
	    	 b [i] = new Node(0,(byte) nodeList.getNumLasers());
	     }
	     
	
	    
	     //======  MAIN LOOP  =====
	     // Main loop control variable = inicio || qNode.size()
	     
	     inicio = true;
	     while ((nodeList.stkNode.size()!=0) || inicio){
	    
	    	 if( inicio == true ){
	    		 inicio = false; 
	    		 ctrl = 0;
	    	 } 
	    	 else {
	    		
	    		 node = nodeList.stkNode.pop();
	    		 ctrl = node.id;// Setup the beginning of new conditions
	    		 nodeList.refreshNodeListFrom(ctrl);
	    		 nodeList.updateNode(node);
	    		 ctrl++;
	    	 }// END IF-ELSE */
	                  
	         
	         while(ctrl < nodeList.getNumNodes()){ //SUPPORT LOOP
	             System.out.println("======================= CRTL "+ ctrl + " ==============================");	    
	        	
	        	//get the possible directions of node.id = ctrl and put it in b[]
	        	 
	        	 aux_id = nodeList.possibleNodes(ctrl); 
		    	          
	        	 // Get the first possible connection from the queue
	        	if ( (node = nodeList.stkNode.pop()) != null){
	        			 if ((byte)node.usedLasers >= (byte)nodeList.getNumLasers()) ctrl++;
	        			 nodeList.updateNode(node);
	        	}
	        	else ctrl = nodeList.getNumNodes()+10;
	        	 //}// END-IF: NO POSSIBLE DIRECTIONS
	        	 
		     }//SUPPORT LOOP
	         //nodeList.printLinks(outputStream);
	     }// MAIN LOOP
	     /*try {
	    	 outputStream.close();
	     } catch (IOException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	     }*/
		
	     
	     stop = Instant.now();
	     System.out.println("Execution time for " + nodeList.getNumNodes() + " nodes is: " + Duration.between(start, stop).toMillis() + " ms");
	}//MAIN

}
