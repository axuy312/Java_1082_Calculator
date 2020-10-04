import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame implements ActionListener {
	Label display;
	Label trace_display;
	Button[] btn;
	String[] btn_value = {"7","8","9","+","4","5","6","-","1","2","3","×","±","0",".","÷","C","Del","="};
	String input;
	String[] trace = new String[5000];
	int size_trace;
	boolean point, clear;
	void Init(){
		for(int i = 0;i<5000;i++){
			trace[i] = "";
		}
		size_trace = 0;
		clear = false;
		point = false;
		input = "0";
		display.setText("0");
	}
	Calculator(){
		setBounds(200,200, 500,500);
		setTitle("s1073302_Calculator");
		setResizable(false);
		display = new Label("0");
		trace_display = new Label("");
		trace_display.setAlignment(2);
		//trace_display.setBackground(Color.BLUE);
		trace_display.setFont(new Font("SERIF", Font.PLAIN, 15));
		display.setAlignment(2);
		//display.setBackground(Color.RED);
		display.setFont(new Font("SERIF", Font.BOLD, 40));
		btn = new Button[19];
		setLayout(new GridBagLayout());
		addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent event) { System.exit(0); }});
		
		Init();
		
		GridBagConstraints bag = new GridBagConstraints();

		bag.fill = GridBagConstraints.BOTH;
		bag.anchor = GridBagConstraints.EAST;
		
		bag.gridx = 0;
		bag.gridy = 0;
		bag.weightx = 1;
		bag.weighty = 0;
		bag.gridwidth = 4;
		bag.gridheight = 1;
		
		add(trace_display, bag);
		
		bag.gridx = 0;
		bag.gridy = 1;
		bag.weighty = 1;
		add(display, bag);
		
		//region
		bag.gridwidth = 1;
		bag.gridheight = 1;
		for	(int i = 0;i < 19; i++){
			btn[i] = new Button(btn_value[i]);
			btn[i].setFont(new Font("Arial", Font.PLAIN, 28));
			btn[i].setPreferredSize(new Dimension(150, 100));
            btn[i].setMinimumSize(new Dimension(150, 60));
			btn[i].addActionListener(this);
		}
		for	(int i = 0;i < 16; i++){
			bag.gridx = i%4;
			bag.gridy = i/4 + 2;
			add(btn[i], bag);
		}
		bag.gridx = 0;
		bag.gridy = 6;
		bag.gridwidth = 2;
		bag.gridheight = 1;
		add(btn[16],bag);
		bag.gridx = 2;
		bag.gridwidth = 1;
		add(btn[17],bag);
		bag.gridx = 3;
		add(btn[18],bag);
		//endregion
		
		setVisible(true);
	}
	
	public String cal(){
		if(size_trace == 0){
			return "0";
		}
		else{//trace_display
			double tmpV = Double.parseDouble(trace[0]);
			if(tmpV % 1 == 0){
				trace[0] = Long.toString((long)tmpV);
			}
			String traceStr = trace[0];
			for(int i = 1; i < size_trace; i++){
				if(!(trace[i]=="+"||trace[i]=="-"||trace[i]=="×"||trace[i]=="÷")){
					tmpV = Double.parseDouble(trace[i]);
					if(tmpV % 1 == 0){
						trace[i] = Long.toString((long)tmpV);
					}
				}
				traceStr += ("  " + trace[i]);
			}
			if(!(trace[size_trace - 1]=="+"||trace[size_trace - 1]=="-"||trace[size_trace - 1]=="×"||trace[size_trace - 1]=="÷")){
				traceStr += ("  =");
			}
			trace_display.setText(traceStr);
		}
		//String[] tmp = trace;
		/*for(int i = 0; i < size_trace; i++){
			System.out.print(trace[i] + "  ");
		}
		System.out.print("\n");*/
		String[] tmp = new String[size_trace];
		System.arraycopy(trace, 0, tmp, 0, size_trace);
		int range = size_trace;
		if (tmp[size_trace - 1] == "+" || tmp[size_trace - 1] == "-" || tmp[size_trace - 1] == "×" || tmp[size_trace - 1] == "÷"){
			range = size_trace - 1;
		}
		for(int i = 0,j=0; i < range; i++){
			if(tmp[i] == "×"){
				double t = Double.parseDouble(tmp[j]);
				t *= Double.parseDouble(tmp[i + 1]);
				tmp[j] = Double.toString(t);
				tmp[i] = tmp[i + 1] = "";
			}
			else if(tmp[i] == "÷"){
				double t = Double.parseDouble(tmp[j]);
				if(Double.parseDouble(tmp[i + 1]) == 0){
					Init();
					//display.setText("無法除以零");
					return "Cannot divide by zero";
				}
				t /= Double.parseDouble(tmp[i + 1]);
				tmp[j] = Double.toString(t);
				tmp[i] = tmp[i + 1] = "";
			}
			else if(tmp[i] != ""){
				j = i;
			}
		}
		
		if (tmp[size_trace - 1] == "×" || tmp[size_trace - 1] == "÷"){
			for(int i = range - 1; i >= 0; i--){
				if(tmp[i] != "" && tmp[i] != "+" && tmp[i] != "-"){
					//System.out.print(Double.parseDouble(tmp[i])+"\n");
					if(Double.parseDouble(tmp[i]) % 1 == 0){
						//System.out.print((int)(Double.parseDouble(tmp[i]))+"\n");
						return Long.toString((long)(Double.parseDouble(tmp[i])));
					}
					else{
						return Double.toString(Double.parseDouble(String.format("%.12f", Double.parseDouble(tmp[i]))));
						//return Double.toString(((double)((int)(Double.parseDouble(tmp[i]) * 10000))) / 10000);
					}
				}
			}
			return "0";
		}
		
		for(int i = 0,j=0; i < range; i++){
			if(tmp[i] == "+"){
				double t = Double.parseDouble(tmp[j]);
				t += Double.parseDouble(tmp[i + 1]);
				tmp[j] = Double.toString(t);
				tmp[i] = tmp[i + 1] = "";
			}
			else if(tmp[i] == "-"){
				double t = Double.parseDouble(tmp[j]);
				t -= Double.parseDouble(tmp[i + 1]);
				tmp[j] = Double.toString(t);
				tmp[i] = tmp[i + 1] = "";
			}
			else if(tmp[i] != ""){
				j = i;
			}
		}
		//System.out.print(Double.parseDouble(tmp[0])+"\n");
		if(Double.parseDouble(tmp[0]) % 1 == 0){
			//System.out.print((int)(Double.parseDouble(tmp[0]))+"\n");
			return Long.toString((long)(Double.parseDouble(tmp[0])));
		}
		else{
			return Double.toString(Double.parseDouble(String.format("%.12f", Double.parseDouble(tmp[0]))));
			//return Double.toString(((double)((int)(Double.parseDouble(tmp[0]) * 10000))) / 10000);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		//System.out.print(((Button)e.getSource()).getLabel()+"\n");
		String op = ((Button)e.getSource()).getLabel();
		double in = Double.parseDouble(input);
		if(op == "+" || op == "-" || op == "×" || op == "÷"){
			if(clear){
				clear = false;
				//System.out.print("Clear->false\n");
			}
			if(size_trace == 0){
				return;
			}
			if(trace[size_trace - 1]=="+"||trace[size_trace - 1]=="-"||trace[size_trace - 1]=="×"||trace[size_trace - 1]=="÷"){
				trace[size_trace - 1] = op;
			}
			else{
				trace[size_trace++] = op;
			}
			point = false;
			display.setText(cal());
		}
		else if(op == "."){
			if( !point){
				point = true;
				if(size_trace != 0 && !( trace[size_trace - 1] == "+" || trace[size_trace - 1] == "-" || trace[size_trace - 1] == "×" || trace[size_trace - 1] == "÷" )){
					trace[size_trace - 1] += ".";
				}
				else{
					trace[size_trace++] += "0.";	
				}
				display.setText(trace[size_trace - 1]);
			}
		}
		else if(op == "±"){
			if(size_trace != 0 && !(trace[size_trace - 1] == "+" || trace[size_trace - 1] == "-" || trace[size_trace - 1] == "×" || trace[size_trace - 1] == "÷")){
				double tmp = Double.parseDouble(trace[size_trace - 1]);
				tmp *= -1;
				trace[size_trace - 1] = Double.toString(tmp);
				display.setText(trace[size_trace - 1]);
				if(clear){
					clear = false;
				}
			}
		}
		else if(op == "C"){
			Init();
		}
		else if(op == "="){
			if(size_trace != 0 && (trace[size_trace - 1] == "+" || trace[size_trace - 1] == "-" || trace[size_trace - 1] == "×" || trace[size_trace - 1] == "÷")){
				size_trace--;
			}
			String tmp = cal();
			Init();
			clear = true;
			trace[size_trace++] = tmp;
			display.setText(tmp);
		}
		else if(op == "Del"){
				//System.out.print("f\n");
			if(size_trace != 0 && !(trace[size_trace - 1]=="+"||trace[size_trace - 1]=="-"||trace[size_trace - 1]=="×"||trace[size_trace - 1]=="÷")){
				//System.out.print("s\n");
				if(trace[size_trace - 1].length() > 0){
					if(trace[size_trace - 1].charAt(trace[size_trace - 1].length() - 1) == '.'){
						point = false;
					}
					trace[size_trace - 1] = trace[size_trace - 1].substring(0, trace[size_trace - 1].length() - 1);
				}
				if(trace[size_trace - 1].length() == 0){
				//System.out.print("t\n");
					//trace[size_trace - 1] = "0";
					size_trace--;
					display.setText(cal());
					return;
				}
				
				//System.out.print(size_trace + "\n");
				display.setText(trace[size_trace - 1]);
			}
		}
		else {
			//if(size_trace>0)
			//System.out.print(size_trace + " "+trace[size_trace - 1]+"\n");
			if(size_trace!= 0 && trace[size_trace - 1] == "0" && op == "0"){
				return;
			}
			if(clear){
				clear = false;
				trace[0] = op;
			}
			else if(size_trace == 0 || trace[size_trace - 1]=="+"||trace[size_trace - 1]=="-"||trace[size_trace - 1]=="×"||trace[size_trace - 1]=="÷"){
				
				trace[size_trace++] = op;
				//System.out.print("add: "+op+"   size: "+size_trace+"\n");
			}
			else{
				//System.out.print("tail: "+op+"   size: "+size_trace+"\n");
				if(trace[size_trace - 1].length() == 1 && trace[size_trace - 1].charAt(0) == '0'){
					trace[size_trace - 1] = op;
				}
				else{
					trace[size_trace - 1] += op;
				}
			}
			display.setText(trace[size_trace - 1]);
		}
	}
	
	public static void main(String[] args){
		Calculator calculator = new Calculator();
		//System.out.print(cal()+"\n");
	}
}