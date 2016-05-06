package structs;

public class ScreenRegion {

	private int upperLeftX, upperLeftY;
	private int width, height;
	private int area;
	
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

	public int getArea(){
		return this.area;
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
	
	public void calculateArea(){
		this.area = this.width * this.height;
		//System.out.println(area);
	}
}
