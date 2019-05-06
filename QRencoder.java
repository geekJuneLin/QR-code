import java.util.*;

//QR Version 1-M 16 codewords
//Error correction code: 196  35  39  119  235  215  231  226  93  23
class QRencoder{
    private static HashMap<Integer, String> table = new HashMap<Integer, String>();
    
    /* Constructor */
    public QRencoder(){
        table.put(0, "0");
        table.put(1, "1");
        table.put(2, "2");
        table.put(3, "3");
        table.put(4, "4");
        table.put(5, "5");
        table.put(6, "6");
        table.put(7, "7");
        table.put(8, "8");
        table.put(9, "9");
        table.put(10, "A");
        table.put(11, "B");
        table.put(12, "C");
        table.put(13, "D");
        table.put(14, "E");
        table.put(15, "F");
        table.put(16, "G");
        table.put(17, "H");
        table.put(18, "I");
        table.put(19, "J");
        table.put(20, "K");
        table.put(21, "L");
        table.put(22, "M");
        table.put(23, "N");
        table.put(24, "O");
        table.put(25, "P");
        table.put(26, "Q");
        table.put(27, "R");
        table.put(28, "S");
        table.put(29, "T");
        table.put(30, "U");
        table.put(31, "V");
        table.put(32, "W");
        table.put(33, "X");
        table.put(34, "Y");
        table.put(35, "Z");
        table.put(36, " ");
        table.put(37, "$");
        table.put(38, "%");
        table.put(39, "*");
        table.put(40, "+");
        table.put(41, "-");
        table.put(42, ".");
        table.put(43, "/");
        table.put(44, ":");
    }
    
    
    /* Convert binary to int and return it as int[]*/
    private static int[] toInt(String num){
        String[] s = num.split("");
        int len = num.length() / 8;
        int[] result = new int[len];
        System.out.println(num + " has " + len + " blocks.");
        for(int i=0; i<len; i++){
            ///////////CONTINUE
        }
        
        return result;
    }
    
    
    /* Convert number to binary format and return it as string */
    //convert decimal to binary string
    private String toBinary(int key, int l){
        String binary = "";
        String tmp = "";
        while(key != 0){
            tmp += String.valueOf(key % 2);
            key /= 2;
        }
        int len = tmp.length();
        int num = 0;
        String[] sa = tmp.split("");
        if(len != l){
            while(num < (l - len)){
                binary += 0;
                num++;
            }
            for(int i=(len-1); i>=0; i--){
                binary += sa[i];
            }
        }else{
            for(int i=(len-1); i>=0; i--){
                num++;
                binary += sa[i];
            }
        }
        return binary;
    }
    
    
    /* Encoding Message */
    //Only for Alphanumeric mode encoding
    public String encode(String s){
        int len = s.length();
        String [] stringArray = s.split("");
        System.out.println("Length of String is: " + len);
        int key = 0, returnKey = 0;
        
        //add the mode indicator
        String rk = "0010" + toBinary(len, 9);
        //end of mode indicator
        
        //checking whether the length of the string is odd or even
        if((len % 2) == 0){  //if is even
            //split the string into groups that contains 2 chars
            for(int i=0; i<len; i++){
                //rk += " ";
                for(Integer num : table.keySet()){   //loop through the table to find the corresponding key of that char
                    if(table.get(num).equals(stringArray[i])){
                        key = num;
                    }
                }
                if((i % 2) == 0){   //if i is oddth char, then multiply by 45
                    returnKey = key * 45;
                }else{
                    returnKey += key;   //if i is eventh char, then add the key with the oddth char
                    //System.out.println("Key: " + toBinary(returnKey));
                    rk += toBinary(returnKey, 11);
                }
            }
        }else{   //if is odd
            for(int i=0; i<len; i++){
                //rk += " ";
                for(Integer num : table.keySet()){   //loop through the table to find the corresponding key of the char
                    if(table.get(num).equals(stringArray[i])){
                        key = num;
                    }
                }
                if(i == (len - 1)){   //if the char is the last one, need to encode to 6-bits binary
                    rk += toBinary(key, 6);
                    break;
                }
                if((i % 2) == 0){   //if i is oddth char, then multiply by 45
                    returnKey = key * 45;
                }else{    //if i is eventh char, then add the key with the oddth char
                    returnKey += key;
                    //System.out.println("Key: " + toBinary(returnKey));
                    rk += toBinary(returnKey, 11);
                }
            }
        } //Encoding end
        
        //adding terminator
        int bit = (16 * 8 - len);
        if(bit <= 4){
            while(bit < 4){
                bit++;
                rk += "0";
            }
        }else{
            rk += "0000";
        } //Terminator added
        
        //calculating how many bits in encoded string
        stringArray = rk.split("");
        len = 0;
        for(int i=0; i<rk.length(); i++){
            if(!stringArray[i].equals(" ")){
                len++;
            }
        }//calculating end
        System.out.println("Length: " + len);
        
        //checking whether the number of bits is multiple of 8
        while(len < (16 * 8)){
            bit = len % 8;
            if(bit != 0){
                bit = len / 8;
                bit = (bit + 1) * 8;
                while(len < bit){
                    rk += "0";
                    len++;
                }
            }else{   //adding !PAD BYTES AT THE END!
                bit = ((16 * 8) - len) / 8;
                while(bit > 0){
                    bit--;
                    if((bit % 2) != 0){
                        rk += "11101100";
                        len += 8;
                    }else{
                        rk += "00010001";
                        len += 8;
                    }
                }
            }
        }  //rounded the bits to multiple of 8 and filled up to the maximum capacity
        return rk;
    }
    
    
    /* Reed-solomon error codewords generation */
    /*                                         */
    /* Message Polynomial generator */
    private void msgPoly(){
        
    }
    
    
    /* Generator Polynomial */
    private void genPoly(){
        
    }
    
    /* Tesing */
    public static void main(String args []){
        QRencoder qr = new QRencoder();
        String s = qr.encode("HELLO WORLD");
        System.out.println("Final encoded: " + s);
        toInt(s);
    }
}
