package t1;

public class Puzzle {

	protected int[][] table;

	public Puzzle(int slots) {
		this.table = new int[slots][slots];

		for (int i = 0; i < slots; i++) {
			for (int j = 0; j < slots; j++) {
				this.table[i][j] = 0;
			}
		}
	}

	public int[][] getTable() {
		return this.table;
	}

	public void setTable(int[][] otherTable) {
		this.table = otherTable;
	}
	
	public void viewTable(){
		String output = "";
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table.length; j++) {
				output += " [" + table[i][j] + "] ";
			}
			output += "\n";
		}
		System.out.println("\n  ---TABLE---\n" + output);
	}
}
