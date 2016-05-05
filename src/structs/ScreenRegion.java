package structs;

public class ScreenRegion {

	private int upperLeftX, upperLeftY;
	private int width, height;

	public ScreenRegion() {
	}

	public ScreenRegion(int x, int y, int width, int height) {
		this.upperLeftX = x;
		this.upperLeftY = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return this.upperLeftX;
	}

	public int getY() {
		return this.upperLeftY;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setX(int x) {
		this.upperLeftX = x;
	}

	public void setY(int y) {
		this.upperLeftY = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
