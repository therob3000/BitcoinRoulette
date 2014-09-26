package gui.game;


public class Coord{
	public int row;
	public int col;
	public Coord(int x, int y){
		this.row = x;
		this.col = y;
	}
	@Override
	public int hashCode(){
		return ((Integer)row).hashCode() + ((Integer)col).hashCode();
	}
	@Override
	public boolean equals(Object o){
		return	(o instanceof Coord) &&
				(this.row == ((Coord)o).row) &&
				(this.col == ((Coord)o).col);
	}
}