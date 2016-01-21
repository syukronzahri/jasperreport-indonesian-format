package com.syukron.jasper;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class Terbilang extends JRDefaultScriptlet
{
	private static long angka;
	private String[] bilangan = {"", "satu", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sebelas"};
	
	public Terbilang()
	{
		this.setAngka(0);
	}
	
	public Terbilang(long angka)
	{
		this.angka = angka;
	}
	
	private long cekAngka(long angka)
	{
		if (angka >= 0) {
			return angka;
		} else {
			return 0;
		}
	}
	
	private String getKata(long bilangan)
	{
		return this.bilangan[(int) bilangan];
	}
	
	public void setAngka(long angka)
	{
		this.angka = angka;
	}
	
	public long getAngka()
	{
		return this.angka;
	}
	
	public String getTerbilang(long angka)
	{
		long x = this.cekAngka(angka);
		String retVal = "";
		
		if (x < 12) {
			retVal += this.getKata(x);
		} else if (x < 20) {
			retVal += this.getTerbilang(x - 10) + " belas";
		} else if (x < 100) {
			retVal += this.getTerbilang(x / 10) + " puluh" + this.getTerbilang(x % 10);
		} else if (x < 200) {
			retVal += " seratus" + this.getTerbilang(x - 100);
		} else if (x < 1000) {
			retVal += this.getTerbilang(x / 100) + " ratus" + this.getTerbilang(x % 100);
		} else if (x < 2000) {
			retVal += " seribu" + this.getTerbilang(x - 1000);
		} else if (x < 1000000) {
			retVal += this.getTerbilang(x / 1000) + " ribu" + this.getTerbilang(x % 1000);
		} else if (x < 1000000000) {
			retVal += this.getTerbilang(x / 1000000) + " juta" + this.getTerbilang(x % 1000000);
		} else if (x < 1000000000000L) {
			retVal += this.getTerbilang(x / 1000000000) + " milyar" + this.getTerbilang(x % 1000000000);
		}
		
		return retVal;
	}
	
	public String toString()
	{
		return this.getTerbilang(this.getAngka());
	}
}
