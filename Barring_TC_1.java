package ucsd.crossbarring.matrix2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Barring_TC_1 {

	int index = 2;
	Node node = new Node(index);
	

	@Test
	public void NodeTest_id() {
		
		assertEquals(index,node.id);
	}

	@Test
	public void NodeTest_usedLasers() {
		node.loadDirs((byte)3);
		assertEquals(2,node.usedLasers);
	}

}
