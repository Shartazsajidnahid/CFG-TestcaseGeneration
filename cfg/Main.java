/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfg;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author sikde
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
       
       String fileName = "/home/nahid/Documents/CFG-TestcaseGeneration/cfg/test.txt";
       SourceCodeReader Sc;                
       Sc = new SourceCodeReader(fileName);
    }  
}
