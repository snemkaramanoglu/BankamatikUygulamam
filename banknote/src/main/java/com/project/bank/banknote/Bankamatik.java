package com.project.bank.banknote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Bankamatik {
	private String satir;
	private static BufferedReader reader;
	int a;
	int genelToplam;
	TreeMap<Integer, Integer> verilecekBanknotMap;
	
	private SortedMap<Integer, Integer> banknotMap = new TreeMap<Integer, Integer>(
			new Comparator<Integer>() {
				public int compare(Integer o1, Integer o2) {
					return o2.compareTo(o1);	
				}
			});

	public Bankamatik() throws FileNotFoundException {

		File file = new File("C:\\Users\\user\\Desktop\\banknotes.txt");
		if (file.exists()) {
			initBanknot(file);

		} else {
			System.out.println("File " + file + " does not exist");
		}
			}					

	private void initBanknot(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(file);
		setReader(null);
		setReader(new BufferedReader(new FileReader(file)));
		// System.out.println( "Loading file: " + file.getName());
		ArrayList<String> lineOfWords = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			StringTokenizer tk = new StringTokenizer(scanner.nextLine()
					.toLowerCase(), "[ \t\r:;|,.\\-><!?'()@/$]");

			while (tk.hasMoreTokens()) {

				lineOfWords.add(tk.nextToken());
			}
			
		}

		for (int i = 0; i < lineOfWords.size(); ++i) {
			banknotMap.put(Integer.parseInt(lineOfWords.get(i)),
					Integer.parseInt(lineOfWords.get(++i)));

		}
		//System.out.println(banknotMap);
	}

	public void paraVer(Integer miktar) throws FileNotFoundException,
			UnsupportedEncodingException {
	
		a = miktar;
		System.out.println("Miktar: " + miktar);
		System.out.println("------------------------------------");

		
		if (miktar % 5 != 0) {
			System.out.println("5 ve 5'in katlarini girmelisiniz.");
			return;
		}

		
		
		verilecekBanknotMap = new TreeMap<Integer, Integer>();

		for (Integer banknot : banknotMap.keySet()) {
			
			if (miktar < banknot) {
				
				continue;
			}

			int banknotSayisi = banknotMap.get(banknot);
					int verilecekBanknotSayisi = 0;									
               int verilmesiGerekenBanknotSayisi = miktar / banknot;
				if (verilmesiGerekenBanknotSayisi > banknotSayisi) {
					verilecekBanknotSayisi = banknotSayisi;
				} else {
					verilecekBanknotSayisi = verilmesiGerekenBanknotSayisi;
				}
         verilecekBanknotMap.put(banknot, verilecekBanknotSayisi);
			
			banknotSayisi = banknotSayisi - verilecekBanknotSayisi;
			if (banknotSayisi >= 0) {

		banknotMap.put(banknot, banknotSayisi);// KALANI GONDERDI
		} else {
			System.out.println("YETERLİ BAKİYE YOK");
		}
		miktar = miktar - (verilecekBanknotSayisi * banknot);

	}
if (Deneme() < miktar) {
			System.out.println("Yeterli bakiyeniz yok.");
		} else {
			ekranaYaz(verilecekBanknotMap);
			//System.out.println(banknotMap);
			DosyaSil(banknotMap);
			

		}

		
	}

	public void DosyaSil(SortedMap<Integer, Integer> banknotMap2)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(
				"C:\\Users\\user\\Desktop\\banknotes.txt", "UTF-8");
		for (Integer banknot : banknotMap2.keySet()) {
			writer.println(banknot + "," + banknotMap2.get(banknot));

		}
		writer.close();
	}
		
	private void ekranaYaz(TreeMap<Integer, Integer> map) {
		int deger;
		int toplam;

		genelToplam = 0;
		for (Integer banknot : map.keySet()) {
			deger = map.get(banknot);

			toplam = deger * banknot;
			genelToplam = genelToplam + toplam;

			
			if (toplam != 0) {
				System.out.println(banknot + "\t* " + deger + "\t=\t" + toplam);
			}

		}

		System.out.println("------------------------------------");

	}
	public BufferedReader getReader() {
		return reader;
	}

	public int Deneme() {
		int deger;
		int toplam;		
		genelToplam = 0;
		for (Integer banknot : verilecekBanknotMap.keySet()) {
			deger = verilecekBanknotMap.get(banknot);

			toplam = deger * banknot;
			genelToplam = genelToplam + toplam;
			return genelToplam;
								
		}
		System.out.println("GENEL TOPLAM" + genelToplam);
	
		return genelToplam;

}						

	public void setReader(BufferedReader reader) {
		Bankamatik.reader = reader;
	}

}		
			

		

			
		

		
	
	
	

	
