//VERİ TABANI İLE YAZMIŞ OLDUĞUM KODUN ENTEGRE EDİLMESİ
//BU SEFER VERİ TABANINDAN BANKNOTE MİKTARLARI OKUNUYOR VE GÜNCELLENİYOR
package com.project.bank.banknote;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class VeriTabanı {
	private String satir;
	private static BufferedReader reader;
	int a;
	int genelToplam;
	TreeMap<Integer, Integer> verilecekBanknotMap;

	public VeriTabanı() {

	}

	private SortedMap<Integer, Integer> banknotMap = new TreeMap<Integer, Integer>(
			new Comparator<Integer>() {
				public int compare(Integer o1, Integer o2) {
					return o2.compareTo(o1);
				}
			});

	public void initBanknote() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conTest = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost/banknotes", "root", "");

			Statement komut = (Statement) conTest.createStatement();

			int son = 0;
			ResultSet rs = (ResultSet) komut
					.executeQuery("SELECT * from banknote");

			while (rs.next()) {
				banknotMap.put(Integer.parseInt(rs.getString("banknote")),
						Integer.parseInt(rs.getString("miktar")));

			}
		} catch (Exception hata) {
			System.out.println("olmadı");
		}

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
			System.out.println("Yeterli bakiyemiz yok.");
		} else {
			
			System.out.println(banknotMap.values());

			System.out.println("lalalal");
			DosyaSil(banknotMap);
			
		}

		
	}

	public void DosyaSil(SortedMap<Integer, Integer> banknotMap2) {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conTest = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost/banknotes", "root", "");
			Statement st = (Statement) conTest.createStatement();

			String query = "UPDATE banknote SET banknote = ?, miktar = ? WHERE id=?";

			PreparedStatement statement = (PreparedStatement) conTest
					.prepareStatement(query);
			int i = 1;
			for (Integer banknot : banknotMap2.keySet()) {

				statement.setInt(1, banknot);

				statement.setInt(2, banknotMap2.get(banknot));
				statement.setInt(3, i++);
				statement.executeUpdate();

			}
		} catch (Exception e) {
			System.out.println("hata" + e);
		}
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
	
		
		return genelToplam;

	}
}
