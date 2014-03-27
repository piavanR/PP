import java.lang.Math;
import java.util.ArrayList;

public class AsciiImage{
	private char[][] image;
	
	public AsciiImage(int width, int height){
		image = new char[width][height];
		this.clear();
	}
	public AsciiImage(AsciiImage img){
		image = new char[img.getWidth()][img.getHeight()];
		for(int y = 0; y < img.getHeight(); y++){
			for(int x = 0; x < img.getWidth(); x++){
				this.setPixel(x, y, img.getPixel(x, y));
			}
		}
	}
	public void clear(){
		for(int y = 0; y < getHeight(); y++){
			for(int x = 0; x < getWidth(); x++){
				this.setPixel(x, y, '.');
			}
		}
	}
	public int getHeight(){
		return image[0].length;
	}
	public int getWidth(){
		return image.length;
	}
	public char getPixel(int x, int y){
		return image[x][y];	
	}
	public char getPixel(AsciiPoint p){
		return getPixel(p.getX(), p.getY());	
	}
	public void setPixel(int x, int y, char c){
		image[x][y] = c;	
	}
	public void setPixel(AsciiPoint p, char c){
		setPixel(p.getX(), p.getY(), c);
	}
	public ArrayList<AsciiPoint> getPointList(char c){
		ArrayList<AsciiPoint> pointList = new ArrayList<AsciiPoint>(0);
		
		for (int y=0; y < getHeight(); y++) {
			for (int x=0; x < getWidth(); x++){
				if(this.getPixel(x, y) == c){
					pointList.add(new AsciiPoint(x, y));	
				}
			}
		}
		return pointList;
	}
	public String toString(){
		String s = "";
		for (int y=0; y < getHeight(); y++) {
			for (int x=0; x < getWidth(); x++){
				s += image[x][y];
			}
			s += '\n';
		}
		return s;
	}
	public void load(String line, int counter){
		assert line.length() == getWidth();
		for (int x=0; x < getWidth(); x++){
			image[x][counter] = line.charAt(x);
		}
	}
	public void replace(char oldChar, char newChar){
		ArrayList<AsciiPoint> list = getPointList(oldChar);
		for(int i = 0; i < list.size(); i++){
			this.setPixel((list.get(i)).getX(), (list.get(i)).getY(), newChar);
		}
	}
	public void transpose(){
		char[][] temp = new char[getHeight()][getWidth()];
		int store = getWidth();
		
		for (int y=0; y < getHeight(); y++) {
			for (int x=0; x < getWidth(); x++){
				temp[y][x] = this.getPixel(x, y);
			}
		}
		image = temp;
	}
	public void fill(int x, int y, char ch){
		char c = getPixel(x, y);
		setPixel(x, y, ch);
		
		
		if(x+1 < getWidth() && getPixel(x+1, y) == c){		
			fill(x+1, y, ch);
		}
		
		if(x-1 >= 0 && getPixel(x-1, y) == c){
			fill(x-1, y, ch);	
		}
		
		if(y+1 < getHeight() && getPixel(x, y+1) == c){
			fill(x, y+1, ch);	
		}
		
		if(y-1 >= 0 && getPixel(x, y-1) == c){
			fill(x, y-1, ch);
		}
	}
	public void drawLine(int x0, int y0, int x1, int y1, char c){
		double dx = x1-x0;
		double dy = y1-y0;
		double x = x0;
		double y = y0;
		
		//1. Fall, -45° < Steigung < 45° --> |dy|<=|dx|, dx>=0
		if(Math.abs(dy) <= Math.abs(dx) && dx >= 0){			
			for(int i = x0; i <= x1; i++){
				this.setPixel(i, (int)Math.round(y), c);
				y += (dy/dx);
			}
		}
		
		//2. Fall --> |dy|<=|dx|, dx<0
		else if(Math.abs(dy) <= Math.abs(dx) && dx < 0){	
			for(int i = x0; i >= x1; i--){
				this.setPixel(i, (int)Math.round(y), c);
				y -= (dy/dx);
			}
		}
		
		//3. Fall --> |dy|>|dx|, dy>=0
		else if(Math.abs(dy) > Math.abs(dx) && dy >= 0){
			for(int i = y0; i <= y1; i++){
				this.setPixel((int)Math.round(x), i, c);
				x += (dx/dy);
			}
		}
		
		//4. Fall --> |dy|>|dx|, dy<0
		else {
			for(int i = y0; i >= y1; i--){
				this.setPixel((int)Math.round(x), i, c);
				x -= (dx/dy);
			}
		}
	}
	public void growRegion(char c){
		ArrayList<AsciiPoint> list = getPointList(c);
		for(int i = 0; i < list.size(); i++){
			
			int x = (list.get(i)).getX();
			int y = (list.get(i)).getY();
			
			if(x+1 < getWidth() && getPixel(x+1, y) == '.'){		
				setPixel(x+1, y, c);
			}
			
			if(x-1 >= 0 && getPixel(x-1, y) == '.'){
				setPixel(x-1, y, c);	
			}
			
			if(y+1 < getHeight() && getPixel(x, y+1) == '.'){
				setPixel(x, y+1, c);	
			}
			
			if(y-1 >= 0 && getPixel(x, y-1) == '.'){
				setPixel(x, y-1, c);
			}
		}	
	}
	public AsciiPoint getCentroid(char c){
		ArrayList<AsciiPoint> pointList = getPointList(c);	
		double sumX = 0;
		double sumY = 0;
		
		for (AsciiPoint item: pointList) {
			sumX += item.getX();
			sumY += item.getY();
   	}
   	
   	sumX = Math.round(sumX/pointList.size());
   	sumY = Math.round(sumY/pointList.size());
   	
   	AsciiPoint centroid = new AsciiPoint((int)sumX, (int)sumY);
   	return centroid;
	}
}
