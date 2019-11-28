/**
 * 
 */

/**
 * @author Ananta Bhatt
 *Description: Code for Vigenere Cipher
 */
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.lang.NumberFormatException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class VigenereCipher {
	
  public static void main ( String args[] ) {
	  
	  //Object of the class
	  VigenereCipher obj = new VigenereCipher();

    int cipher_len = 0;
    
    //Initalise the cipher text
    
    String ciphertext = "cjnpkgrlilqwawbnuptgkerwxuzviaiiysxckwdntjawhqcutttvp"
    		+"tewtrpgvcwlkkkgczafsihrimixukrwxrfmgfgkfxgukpjvvzmcmj"+
    		"vawbnuptgcicvxvkgczkekgcqbchvnrqhhwiadfrcyxgvzqqtuvbd"+
    		"guvttkccdpvvfphftamzxqwrtgukcelqlrxgvycwtncbjkkeerecj"+
    		"qihvrjzpkkfexqgjtpjfupemswwxcjqxzpjtxkvlyaeaemwhovudk"+
    		"mnfxegfrwxtdyiaecyhlgjfpogymbxyfpzxxvpngkxfitnkfdniyr"+
    		"wxukssxpkqabmvkgcqbciagpadfrcyxgvyyimjvwpkgscwbpurwxq"+
    		"kftkorrwvnrqhxurlslgvjxmvccraceathhtfpmeygczwgutttvtt"+
    		"katmcvgiltwcsmjmvyghitfzaxodkbf"; String key = "CRYPT";
    for (int i = 0; i < ciphertext.length(); i++) {
      if (Character.isLetter(ciphertext.charAt(i)))
    	  cipher_len ++;
    }
   
    System.out.println(cipher_len  + " Cipher text length of the letters\n"
    		+ "");

    //Suppose key length=2 or 3 or 4 or 5 or 6
    List<Integer> list = new ArrayList<Integer>(); 
    list.add(2); 
    list.add(3); 
    list.add(4); 
    list.add(5); 
    list.add(6); 

    //Mapping of the index of coincidence for each  key length
    LinkedHashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
    
    for (Integer key_len : list) {
      map.put(key_len, obj.calc_expected_ic(cipher_len, key_len));
    }

    obj.printTable(cipher_len, map);
    double finalIc= calculate_ioc(ciphertext);
    
//Roundoff theIndex of coincidence value
    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.CEILING);
   String indexofCoincidence= df.format(finalIc);
    System.out.println("The rounded Index of Coincidence(IOC) is---- "+ indexofCoincidence);
    
    //Converting in double
    double round_ioc = Double.parseDouble(indexofCoincidence);
    int keylen = 0;
    
    //Roundoff assumed keys-2,3,4,5,6
    for (Map.Entry<Integer, Double> entry : map.entrySet()){
    	
    	DecimalFormat df1 = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        String rn_Key= df.format(entry.getValue());
        double round_key = Double.parseDouble(rn_Key);
    	if(round_key== round_ioc)
    	{
    		 keylen= entry.getKey();
        System.out.printf("The Key Length---- "+ entry.getKey());
      }
    	
    	}
    System.out.println("\nThe Decrypted message for this program------\n" + decrypt(ciphertext, key)); 
    
  }//end of main

 //Cacullating the expecetd Index of Coincidence
  public Double calc_expected_ic(Integer int_N, Integer int_d){
    double N = int_N *1.0;
    double d = int_d *1.0;
    
    return ((1.0/d)*((N-d)/(N-1.0))*(0.066)) + (((d-1.0)/d)*(N/(N-1))*(0.038));
  }

  
  public void printTable(Integer message_len, 
                         LinkedHashMap<Integer, Double> ics ){
    System.out.printf("The Key Expected Index of Coincidence--- (for text length N=%d)\n", message_len);
  
    for (Map.Entry<Integer, Double> entry : ics.entrySet()){
      System.out.printf("For key size m=%4d   %-5.4f\n", entry.getKey(), entry.getValue());
    }
  }

  public static Double calculate_ioc(String ciphertext){
	  int[] charfreq = new int[26];
      int i = 0;
      int j =  0;
      int textlen = 0;
      while(i < ciphertext.length()) {
          char c  = ciphertext.charAt(i);
          j = (int) c - (int) 'a';
          if(j >= 0 && j <= 25) {
              charfreq[j]++;
              textlen++;
          }
          i++;
          j = 0;
      }
      double ic = 0;
      for(int k = 0; k < 26; k++) 
          ic += charfreq[k] * (charfreq[k] - 1);
      ic /= textlen * (textlen - 1);
      System.out.println("The index of coincidence(IOC) is.... " + ic + ".");
      return ic;
}

//Calculating the letter frequency
  public static int[] letterFrequency(String text) {
		int[] frequencies = new int[26];

		text = format(text);

		for (int i = 0; i < text.length(); i++) {
			frequencies[text.charAt(i) - 'A']++;
		}

		return frequencies;
	}
  public static String format(String text) {
		return text.toUpperCase().replaceAll("[^\\p{L}]", "");
	}


// decryption code

	 public static String decrypt(String text, final String key)
	    {
	        String res = "";
	        text = text.toUpperCase();
	        for (int i = 0, j = 0; i < text.length(); i++)
	        {
	            char c = text.charAt(i);
	            if (c < 'A' || c > 'Z')
	                continue;
	            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
	            j = ++j % key.length();
	        }
	        return res;
	    }
}//end of class