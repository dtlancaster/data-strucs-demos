import java.lang.Integer;

public class Prog {

	public static void main(String [] args) {
		
		if (args.length != 2) {
			System.out.println("Please execute: java Prog <n> <p>");
			System.out.println("n is the input size, and p is the program number.");
			System.exit(0);
		}

		int n = Integer.parseInt(args[0]);
		int p = Integer.parseInt(args[1]);

		switch(p) {
			case 1: prog1(n);
							break;
			case 2: prog2(n);
							break;
			case 3: prog3(n);
							break;
			case 4: prog4(n);
							break;
			default: System.out.println("Invalid program number. Only 1-4.");
		}
	}

	/*
		 The solution is 0, n, 2n, ..., (n-1)n.
	*/
	private static void prog1(int n) {
		for (int i = 0; i < n * n; i += n)
			System.out.println(i);
	}

	/*
		 The solution is 0, 1, 2, ..., n-1/.
	*/
	private static void prog2(int n) {
		for (int i = 0; i < n; ++i)
			System.out.println(i);
	}

	private static void prog3(int n) {
		Node [] array = new Node[n];
		int [] counts = new int[n];
		HashFunctions H = new HashFunctions(n);

		// First iterate through upto p or n^2 (case p < n^2 and p >= n^2 resp.), whichever is smaller, then keep track of where each key hashes.
		// If the key i hashes in index ind, increment count[ind] and store the key i in the linked list pointed by array[ind].
		// Also keep track of the index n_index where the largest set of keys are hashed.
		int p = 128189;
		int n_index = 0;
		for (int i = 0; i < p && i < n * n; ++i) {
			int ind = H.hash3(i);

			counts[ind]++;
			array[ind] = new Node(i, array[ind]);
			if (counts[ind] > counts[n_index]) {
				n_index = ind;
			}
		}
		
		// Print keys in the linked list pointed by array[n_index]. Suppose this list contains elements a, b, c, .... These are keys in the range 0 to p that hash to n_index.
		// Also print p + a, p + b, p + c, ..., then 2p + a, 2p + b, 2p + c, ... until n keys are printed. 
		int k = 0;
		int mul = p;
		int num = 0;
		while (k * mul + array[n_index].key < n * n && num < n) {
			for (Node cur = array[n_index]; cur != null && k * mul + cur.key < n * n && num < n; cur = cur.next, ++num) {
				System.out.println(k * mul + cur.key);
			}
			++k;
		}
	}

	/*
		 The only solution is to try where all keys are hashed from keys 0 to n^2, then take the index that contains n keys. 
	*/
	private static void prog4(int n) {
		Node [] array = new Node[n];
		int [] counts = new int[n];
		HashFunctions H = new HashFunctions(n);

		// Same solution as #3 -- keep track of where each key is hashed, and keep track of the count of the number of keys that are hashed to a particular index.
		boolean status = false;
		int n_index = 0;
		for (int i = 0; i < n * n && !status; ++i) {
			int ind = 	H.hash4(i);
			counts[ind]++;
			array[ind] = new Node(i, array[ind]);
			// We can stop searching if one of the counts reaches n.
			if (counts[ind] == n) {
				status = true;
				n_index = ind;
			}
		}
		if (!status) 
			System.out.println("Cannot find n keys that all hash. Something wrong.");
		else {
			for (Node cur = array[n_index]; cur != null; cur = cur.next)
				System.out.println(cur.key);
		}
	}
}
