package s4.B193317; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
import java.lang.*;
import s4.specification.*;

/* What is imported from s4.specification
package s4.specification;
public interface InformationEstimatorInterface{
    void setTarget(byte target[]); // set the data for computing the information quantities
    void setSpace(byte space[]); // set data for sample space to computer probability
    double estimation(); // It returns 0.0 when the target is not set or Target's length is zero;
// It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
// The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
// Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
// Otherwise, estimation of information quantity, 
}                        
*/

public class InformationEstimator implements InformationEstimatorInterface{
    // Code to tet, *warning: This code condtains intentional problem*
    byte [] myTarget; // data to compute its information quantity
    byte [] mySpace;  // Sample space to compute the probability
    FrequencerInterface myFrequencer;  // Object for counting frequency

    byte [] subBytes(byte [] x, int start, int end) {
	// corresponding to substring of String for  byte[] ,
	// It is not implement in class library because internal structure of byte[] requires copy.
	byte [] result = new byte[end - start];
	for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
	return result;
    }

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    double iq(int freq) {
	return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
    }

    public void setTarget(byte [] target) { myTarget = target;}
    public void setSpace(byte []space) { 
	myFrequencer = new Frequencer();
	mySpace = space; myFrequencer.setSpace(space); 
    }

    public double estimation(){
	boolean [] partition = new boolean[myTarget.length+1];
	int np;
	np = 1<<(myTarget.length-1);
        // System.out.println("np="+np+" length="+myTarget.length);
	double value = Double.MAX_VALUE; // value = mininimum of each "value1".

	/*
	for(int p=0; p<np; p++) { // There are 2^(n-1) kinds of partitions.
	    // binary representation of p forms partition.
	    // for partition {"ab" "cde" "fg"}
	    // a b c d e f g   : myTarget
	    // T F T F F T F T : partition:
	    partition[0] = true; // I know that this is not needed, but..
	    for(int i=0; i<myTarget.length -1;i++) {
		partition[i+1] = (0 !=((1<<i) & p));
	    }
	    partition[myTarget.length] = true;

	    // Compute Information Quantity for the partition, in "value1"
	    // value1 = IQ(#"ab")+IQ(#"cde")+IQ(#"fg") for the above example
            double value1 = (double) 0.0;
	    int end = 0;
	    int start = end;
	    while(start<myTarget.length) {
	        // System.out.write(myTarget[end]);
		end++;
		while(partition[end] == false) { 
		    // System.out.write(myTarget[end]);
		    end++;
		}
		 System.out.print("("+start+","+end+")");
		myFrequencer.setTarget(subBytes(myTarget, start, end));
		value1 = value1 + iq(myFrequencer.frequency());
		start = end;
	    }
	     System.out.println(" "+ value1);
	*/
	    // Get the minimal value in "value"	
	//if(value1 < value) value = value1;
	int n = myTarget.length*(myTarget.length+1)/2;
	double[]  infoQuant = new double[n]; //記憶用配列
	//0-myTerget.length-1まで、最小構成単位の情報量
	for(int i = 0; i < n; i++){
	    if(0 <= i && i <myTarget.length){
		myFrequencer.setTarget(subBytes(myTarget,i,i+1));
		infoQuant[i] = iq(myFrequencer.frequency());
	    }else{
		infoQuant[i] = -1;
	    }
	}
      
	//以降の計算は保存しながら。データ構造上、infoQuant[myTarget.length-1](最後の要素)に答えが入るようにする。
	    //新たに情報量を計算
	    //最小構成単位の情報量の要素の和を計算
	    //比較：最小値を入れる
	/*
	for(int k = 2; k < myTarget.length-1; k++){
	    for(int j = 0; j < myTarget.length-1; j++){
		
		myFrequencer.setTarget(subBytes(myTarget,j,j+k)); //0 2,  1 3,  2 4
		int inf = iq(myFrequencer.frequency());
		
		infoQuant[4] = Math.min( )
	
    
		}
	}
	*/
	return infoQuant[n-1];
    }

    public static void main(String[] args) {
	InformationEstimator myObject;
	double value;
	myObject = new InformationEstimator();
	myObject.setSpace("3210321001230123".getBytes());
	myObject.setTarget("0".getBytes());
	value = myObject.estimation();
	System.out.println(">0 "+value);
	myObject.setTarget("01".getBytes());
	value = myObject.estimation();
	System.out.println(">01 "+value);
	myObject.setTarget("0123".getBytes());
	value = myObject.estimation();
	System.out.println(">0123 "+value);
	myObject.setTarget("00".getBytes());
	value = myObject.estimation();
	System.out.println(">00 "+value);
    }
}
				  
			       

	
    
