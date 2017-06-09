/**
 * 
 */
package ucsd.crossbarring.matrix2;

/**
 * @author Maxwell E Monteiro
 * @ version 1.0
 */

/**
 * This is the auxiliary class, representing a ToR in a rack
 * 
 */

//Auxiliar class to represent the lasers used in a ToR
class LaserConn{
	int dir; //direction (int: 0 - n) represent the laser (fiber) used
	int dstNode;  //destination (in) represents the node for which the laser is pointing 
   
	 public LaserConn(int d, int dstn){
	       dir = d;
	       dstNode = dstn;
     }

}

// Main class which represents a ToR
public class Node {

	protected int id=0;
	protected byte numLasers=0;
	protected byte usedLasers=0;
	protected LaserConn[] lasers;
	
	public Node(int node_id, byte num_lasers){
		this.id = node_id;
		this.numLasers =  num_lasers;
		lasers = new LaserConn[num_lasers];
		for (int k=0;k<num_lasers;k++)lasers[k] = new LaserConn(-1, -1);
		
	}
	
	// Set a specific laser to connect to a specific destination (int: dst_node)node in some direction (int: dir)
	
	public Node clone() {
			// TODO Auto-generated method stub
			Node clone = new Node(this.id, this.numLasers);
			for(int i=0; i<this.numLasers;i++){
				clone.lasers[i].dir = this.lasers[i].dir;
				clone.lasers[i].dstNode = this.lasers[i].dstNode;
			}
			clone.usedLasers = this.usedLasers;
			return clone;
		}	
	//Connect a node to the destinations registered into LaserConn[]
	public void connect(int index) {
		// TODO Auto-generated method stub
		
		for(int c=0;c<this.numLasers;c++){
			if(this.lasers[c].dstNode==-1){
				this.lasers[c].dstNode = index;
				this.lasers[c].dir = (byte) 0 ;
				this.usedLasers++;
				break;
			}
			
		}
	}
	
	//Reset (int: -1) all lasers in any directio	
	public void resAllLasers() {
		// TODO Auto-generated method stub
		for (int k=0;k<this.numLasers;k++){
			lasers[k].dstNode =-1;
			lasers[k].dir =-1;
		}
		this.usedLasers=0;
	}
	
	// Reset (int: -1) a laser connected with a specific node (dst_node) 
	public void resLaser(int dst_node) {
		// TODO Auto-generated method stub
		for(int i=0; i< this.usedLasers;i++){
			if(this.lasers[i].dstNode == dst_node){
				this.lasers[i].dstNode = - 1;
				this.lasers[i].dir = -1;
				this.usedLasers--;
			};
		    
		}
	 }
	
	
	// Set a specific laser to connect to a specific destination (int: dst_node)node in some direction (int: dir)
	public void setLaser(int dstNode, byte dir) {
		// TODO Auto-generated method stub
		this.lasers[this.usedLasers].dir = dir;
		this.lasers[this.usedLasers].dstNode = dstNode;
		this.usedLasers++;
	}
	
	// Verify if there is a laser transceiver available for new connection
	public boolean checkAvailableLasers() {
		// TODO Auto-generated method stub
		if (this.usedLasers>=this.numLasers)return false;
		else return true;
	}

	
//=======================  PRINTING ===================================================	
	
	
	public void printNode(){
		System.out.println("id: " + this.id);
		System.out.println("usedLasers: " + this.usedLasers);
		//System.out.println("usedDirs: " + this.usedDirs);
	}




	



	
}
