package items.items_tree;

public abstract class GameItem {

	protected float x, y;
	protected int id = -1;
	protected String type = "GameItem";

	abstract public void move(float x, float y);
	abstract public void draw();

	public String toString() {
		return String.format("[(%f,%f) %d %s\n", x, y, id, type);
	}
}
