public class AsciiStack{
	private AsciiImage[] stack;
	private int increment = 0;
	private int size = 0;

	public AsciiStack(int increment){
		this.increment = increment;
		stack = new AsciiImage[increment];
	}
	public boolean empty(){
		return size == 0;
	}
	public int capacity(){
		return stack.length;	
	}
	public int size(){
		return size;
	}
	public AsciiImage peek(){
		if(this.empty()){
			return null;	
		} else {
			return stack[size-1];
		}
	}
	public void push(AsciiImage img){
		//überprüfen ob stack bereits voll
		if(size == capacity()){
			//neuen stack erstellen, um increment größer als alter
			this.copy(stack.length+increment);
		}
		stack[size++] = img;		
	}
	public AsciiImage pop(){
		if(this.empty()){
			return null;	
		} else {
			AsciiImage pop = stack[--size];
			stack[size] = null;
			if(size < (capacity()-increment)){
				this.copy(stack.length-increment);	
			}
			return pop;
		}
	}
	//neues array mit übergebener länge erstellen und alle alten elemente kopieren
	public void copy(int length){
		AsciiImage [] temp = new AsciiImage[length];
		for(int i = 0; i < stack.length; i++){
			temp[i] = stack[i];	
		}
		stack = temp;
	}
}
