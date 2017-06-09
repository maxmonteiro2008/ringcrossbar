package ucsd.crossbarring.matrix2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * This is the main class, representing a list of ToRs in a data center interconnected by a Crossbar Ring matrix
 * 
 */
public class NodeList {
    
	// Attributes
	private int matrixBase=0; //N in a NxN matrix
	private int numNodes=0;//total number of nodes in the matrix
	private byte numDirs=0;//number of lasers directions
	private byte numLasers=0; //number of lasers in each node
	private byte numDirCombs=0; //number of combinations of lasers and directions
	public Node[] nodes; //Array of Nodes representing the architecture of CrossBarRings 
	public int[] neighbors;// Neighbors of a node dirung possible nodes calculation
	public Stack<Node> stkNode; //supporting queue for backing track
	
	//======== Class Constructor  ==========
	public NodeList(int matrixBase, byte numDirs, byte numLasers){
		this.matrixBase = matrixBase;
		this.numNodes = matrixBase * matrixBase;
		this.numDirs=numDirs;
		this.numLasers=numLasers;
	    this.numDirCombs = (byte) (this.factorial(numDirs)/(this.factorial(numDirs-numLasers)*this.factorial(numLasers)));
		this.nodes = new Node[this.numNodes];
		this.neighbors = new int[2*(matrixBase-1)];
		this.stkNode = new Stack<Node>();
		for(int i=0;i<numNodes;i++){
			this.nodes[i]= new Node(i,numLasers);
		}    
	}
	
	//  =======  GETs and SETs  ==========
	public int getNumNodes(){
		return this.numNodes;
	}
	
	public byte getNumDirCombs() {
		// TODO Auto-generated method stub
		return this.numDirCombs;
	}
	public int getNumLasers (){
		return this.numLasers;
	}
	
		
	
	
	
	// ================== DOMAIN/BUSINESS METHODS =====================	
	
	// Reset nodes attributes from a desired note index (int: list_index) in the list
	protected void refreshNodeListFrom(int list_index) {
		// TODO Auto-generated method stub
		int remote=0;
		for(int local =  list_index; local< this.numNodes;local++){
			for (int laser=0; laser<this.nodes[local].usedLasers;laser++){
				remote = this.nodes[local].lasers[laser].dstNode;
				if(remote > list_index){
					this.nodes[remote].resLaser(local);
					this.nodes[local].resLaser(remote);	
				}
				
			}
		}
	}
	
	public void updateNode(Node node) {
		// TODO Auto-generated method stub
		
		//   ESTOU AQUI MAY/16/2016
		// Update the node based on the newly poped from possible nodes queue
		int dst=0;
		this.nodes[node.id] = node;
		for(int i=0; i < this.numLasers;i++){
			dst = node.lasers[i].dstNode;
			if (dst>node.id){
			System.out.println("Connecting: " + node.id +"->"+ dst); 
			this.nodes[dst].connect(node.id);
			}
		}
		
	}
	
	public int posNodes(int index, int r, Node n, int pos){
		int av = 0;
		int bv = n.usedLasers;
		
		while((index <= neighbors.length-1)&&(r>0)){
				n.setLaser(this.neighbors[index], (byte) 0);
				pos = this.posNodes(index+1, r-1, n, pos);
				n.resLaser(this.neighbors[index]);
				index++;	
		}
		if(n.usedLasers>=n.numLasers){
			
			for(int i=0; i<this.numLasers;i++)
				if(!this.nodes[n.lasers[i].dstNode].checkAvailableLasers()) av++;
					
			// check remote availability
			if(av>=bv){
				   Node clone = (Node) n.clone();
				   this.stkNode.push(clone);
				   System.out.println("Empilhado: " + n.id + "/" + n.lasers[0].dstNode +"/" + n.lasers[1].dstNode);
				   pos++;
			}
			else av = 0;
		}
		return pos;
		
	}
	
	
	//Given a Node, calculates the possible connections, resulting in possible nodes  
	public int possibleNodes(int index){
		
		int possible=0;
		int ind=0;
		int q,r;
		byte u;
				
		Node n = new Node(index, (byte)this.getNumLasers());
		
		for(int i=0;i<this.getNumLasers();i++){
			n.lasers[i].dir = this.nodes[index].lasers[i].dir;
			n.lasers[i].dstNode = this.nodes[index].lasers[i].dstNode;
		}
		n.usedLasers = nodes[index].usedLasers;
		u = (byte)this.numLasers;
		u = (byte)(u - n.usedLasers);
		
		if (this.nodes[index].usedLasers >= this.numLasers) return possible;
		else{
			q = index/this.matrixBase;
			r = index%this.matrixBase;
			
			// Calculating adjacents
			for(int i=0; i < this.matrixBase; i++)	if (n.id!= q*this.matrixBase+i) this.neighbors[ind++] = q*this.matrixBase+i;
			
			for(int i=0; i < this.matrixBase; i++) if(n.id!= i*this.matrixBase+r) this.neighbors[ind++] = i*this.matrixBase+r;
		}
			
		return this.posNodes(0,(int) u, n, possible);
	}
	
		
//  =============== AUXILIARY METHODS ===========================
	private int factorial(int num) {
		// TODO Auto-generated method stub
		if((num == 0)||(num == 1)) return 1;
		else return (num*this.factorial(num-1));
	}

	
	// ======= PRINTs ========================
	

	/*public void printLinks(FileWriter outputStream){
	    int i=0;
	    String s = new String();
	    	    
		for(i = 0; i<this.numNodes;i++){
	        
			if ((this.nodes[i].usedLasers & (byte) 1)== 1) 
				this.links[i]=1;
			else this.links[i]=0;
				
			if ((this.nodes[i].usedLasers & (byte) 4)== 4)
				this.links[i+this.numNodes]=1;
			else this.links[i+this.numNodes]=0;
		}
		    for(i=0;i<2*this.numNodes;i++){
				if(i!=0) s = s + ", ";
				s = s + this.links[i];
			}
			s =s + "\n";
			
	try{
		outputStream.write(s);
		
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	public void printAttribs(){
			System.out.println("matrixBase: "  + this.matrixBase );
			System.out.println("numNodes: "  + this.numNodes);
			System.out.println("numDirs: "  + this.numDirs);
			System.out.println("numLasers: "  + this.numLasers);
			System.out.println("numDirCombs: "  + this.numDirCombs);
			System.out.println();
		}
		
		public void printNodes(){
			for (int k=0;k<this.numNodes;k++){
				System.out.println("Node # "  + this.nodes[k].id );
				System.out.println("Used Lasers: "  + this.nodes[k].usedLasers);
				System.out.println();
				
			}
			
			
		}
		
		public void printPos(byte[] b) {
			for (int k=0;k<this.numDirCombs;k++)
				System.out.println("DirComb # "  + k + " = " + b[k]);
						
			System.out.println();
		}
		
	
}
