import java.util.Scanner;

public class AsciiShop{
	
	private static AsciiImage asImage;
	private static AsciiStack stack;
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		stack = new AsciiStack(3);
		
		if(sc.next().equals("create") && sc.hasNextInt()){
			//zuerst Breite einlesen
			int width = sc.nextInt();
			if(width > 0 && sc.hasNextInt()){
				//dann Höhe
				int height = sc.nextInt();
				if(height <= 0){
					error();
					return;
				}
				else {
					asImage = new AsciiImage(width, height);
				}
			}
			else {
				error();
				return;
			}
			while(sc.hasNext()){
				String command = sc.next();	
				
				if(command.equals("load")){
					String eof = sc.next();
					
					// height == anzahl eingelesener zeilen, überprüfung durch schleife
					for(int i = 0; i < asImage.getHeight(); i++){
						String line = sc.next();
						if(line.length() == asImage.getWidth()){
							pushImg();
							asImage.load(line, i);	
						}
						else{
							error();
							return;
						}
					}
					if(!(sc.next().equals(eof))){
						error();
						return;
					}
				}
				else if(command.equals("print")){
					System.out.print(asImage.toString());
					System.out.print('\n');
				}
				else if(command.equals("grow")){
					String newChar = sc.next();
					if(newChar.length() != 1){
						error();
						return;
					}
					else {
						pushImg();
						asImage.growRegion(newChar.charAt(0));	
					}
				}
				else if(command.equals("centroid")){
					String newChar = sc.next();
					if(newChar.length() != 1){
						error();
						return;
					}
					else {
						System.out.println(asImage.getCentroid(newChar.charAt(0)).toString());	
					}
				}
				else if(command.equals("clear")){
					pushImg();
					asImage.clear();
				}
				else if(command.equals("replace")){
					String oldChar = sc.next();
					if(oldChar.length() == 1){
						String newChar = sc.next();
						if(newChar.length() != 1){
							error();
							return;
						}
						else {
							pushImg();
							asImage.replace(oldChar.charAt(0), newChar.charAt(0));
						}
					}
					else {
						error();
						return;              
					}
				}
				else if(command.equals("transpose")){
					pushImg();
					asImage.transpose();	
				}
				else if(command.equals("undo")){
					if(stack.empty()){
						System.out.println("STACK EMPTY");
					}
					else {
						stack.pop();
						System.out.println("STACK USAGE "+stack.size()+"/"+stack.capacity());	
					}
				}
				else if(command.equals("fill") && sc.hasNextInt()){
					int x = sc.nextInt();
					if(sc.hasNextInt()){
						int y = sc.nextInt();	
						if(sc.hasNext()){
							String temp = sc.next();
							if(temp.length() != 1){
								error();
								return;
							}
							else {
								char ch = temp.charAt(0);
								if(!(x < 0 || x >= asImage.getWidth() || y < 0 || y >= asImage.getHeight())){
									pushImg();
									asImage.fill(x, y, ch);	
								}
								else {
									posError();
									return;
								}
							}
						}
						else {
							error();
							return;
						}
					}
					else {
						error();
						return;
					}
				}
				else if(command.equals("line") && sc.hasNextInt()){
					int x0 = sc.nextInt();
					if(sc.hasNextInt()){
						int y0 = sc.nextInt();
						if(sc.hasNextInt()){
							int x1 = sc.nextInt();
							if(sc.hasNextInt()){
								int y1 = sc.nextInt();
								if(sc.hasNext()){
									String temp = sc.next();
									if(temp.length() != 1){
										error();
										return;
									}
									else {
										pushImg();
										asImage.drawLine(x0, y0, x1, y1, temp.charAt(0));
									}
								}
								else {
									comError();
									return;
								}
							}
							else {
								comError();
								return;
							}
						}
						else {
							comError();
							return;
						}
					}
					else {
						comError();
						return;
					}
				}
				else {
					comError();
					return;
				}
			}
		}	
		else {
			error();
			return;
		}	
		
		
		
	}
	private static void pushImg(){
		AsciiImage copy = new AsciiImage(asImage);
		stack.push(asImage);
		asImage = copy;
	}
	public static void error(){
		System.out.println("INPUT MISMATCH");
	}
	public static void posError(){
		System.out.println("OPERATION FAILED");	
	}
	public static void comError(){
		System.out.println("UNKNOWN COMMAND");	
	}
}
