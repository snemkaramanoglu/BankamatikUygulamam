package com.project.bank.banknote;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    Bankamatik bankamatik=new Bankamatik();
    Scanner input = new Scanner(System.in);
	System.out.println("Lütfen istediginiz banknotu giriniz:");
    int inputbanknote = input.nextInt();
    bankamatik.paraVer(inputbanknote);

	//BURADA UYGUN ŞEKİLDE VERİTABANI BAŞLIKLI SINIFDA ÇAĞRILABİLİR
     
   
    

       
    }
}
