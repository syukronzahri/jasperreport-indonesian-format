package com.syukronzahri.jasper;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class IndonesianFormatHelper extends JRDefaultScriptlet
{
    private String[] bilangan = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
    private String[] longBulan = {"", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] shortBulan = {"", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"};
    private String[] longHari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
    private String[] shortHari = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};
        
    public static enum TipeTanggal {SEMUA, TANGGAL, WAKTU};
    public static enum LetterCase {UPPERCASE, LOWERCASE, NORMALCASE};
    
    private String getKata(long bilangan)
    {
        return this.bilangan[(int) bilangan];
    }
    
    private String getRecursiveTerbilang(long x)
    {
        x = Math.abs(x);
        String retVal = "";
        
        if (x < 12) {
            retVal += " " + this.getKata(x);
        } else if (x < 20) {
            retVal += this.getRecursiveTerbilang(x - 10) + " Belas";
        } else if (x < 100) {
            retVal += this.getRecursiveTerbilang(x / 10) + " Puluh" + this.getRecursiveTerbilang(x % 10);
        } else if (x < 200) {
            retVal += " Seratus" + this.getRecursiveTerbilang(x - 100);
        } else if (x < 1000) {
            retVal += this.getRecursiveTerbilang(x / 100) + " Ratus" + this.getRecursiveTerbilang(x % 100);
        } else if (x < 2000) {
            retVal += " Seribu" + this.getRecursiveTerbilang(x - 1000);
        } else if (x < 1000000) {
            retVal += this.getRecursiveTerbilang(x / 1000) + " Ribu" + this.getRecursiveTerbilang(x % 1000);
        } else if (x < 1000000000) {
            retVal += this.getRecursiveTerbilang(x / 1000000) + " Juta" + this.getRecursiveTerbilang(x % 1000000);
        } else if (x < 1000000000000L) {
            retVal += this.getRecursiveTerbilang(x / 1000000000) + " Milyar" + this.getRecursiveTerbilang(x % 1000000000);
        } else if (x < 1000000000000000L) {
        	retVal += this.getRecursiveTerbilang(x / 1000000000000L) + " Triliyun" + this.getRecursiveTerbilang(x % 1000000000000L);
        }
        
        return retVal;
    }
    
    
    public <T extends Number> String getTerbilang(T bilangan)
    {
    	return this.getTerbilang(bilangan, IndonesianFormatHelper.LetterCase.NORMALCASE);
    }
    
    public <T extends Number> String getTerbilang(T bilangan, IndonesianFormatHelper.LetterCase letterCase)
    {
    	String retVal;
    	long x = bilangan.longValue();
    	
    	if (letterCase == IndonesianFormatHelper.LetterCase.LOWERCASE) {
    		retVal = this.getRecursiveTerbilang(x).toLowerCase();
    	} else if (letterCase == IndonesianFormatHelper.LetterCase.UPPERCASE) {
    		retVal = this.getRecursiveTerbilang(x).toUpperCase();
    	} else {
    		retVal = this.getRecursiveTerbilang(x);
    	}
    	
    	return retVal.trim().replaceAll(" {2,}", " ");
    }
    
    
    public <T extends Number> String formatDecimal(T angka)
    {
    	return this.formatDecimal("#,##0.00", angka);
    }
    
    public <T extends Number> String formatDecimal(String numberMask, T angka)
    {
    	String retVal;

    	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    	dfs.setGroupingSeparator('.');
    	dfs.setDecimalSeparator(',');
    	
    	DecimalFormat x = new DecimalFormat(numberMask, dfs);
    	
		retVal = x.format(angka.doubleValue()).trim();
    	
    	return retVal;
    }
    
    
    public String getLongBulan(int bln) throws Exception
    {
    	if (bln < 1 || bln > 12) {
    		throw new Exception("Invalid bulan range (should be 1-12)");
    	} else {
    		return this.longBulan[bln];
    	}
    }

    public String getShortBulan(int bln) throws Exception
    {
    	if (bln < 1 || bln > 12) {
    		throw new Exception("Invalid bulan range (should be 1-12)");
    	} else {
    		return this.shortBulan[bln];
    	}
    }
    
    
    public String getTanggal(Date inputDate)
    {
    	return this.getTanggal("d-m-Y H:i:s", inputDate);
    }
    
    public String getTanggal(TipeTanggal tipe, Date inputDate)
    {
    	String retVal = "";
    	switch (tipe) {
    	case SEMUA:
    		retVal = this.getTanggal(inputDate);
    		break;
    	case TANGGAL:
    		retVal = this.getTanggal("d-m-Y", inputDate);
    		break;
    	case WAKTU:
    		retVal = this.getTanggal("H:i:s", inputDate);
    		break;
    	}
    	
    	return retVal;
    }
    
    public String getTanggal(String formatTanggal, Date inputDate)
    {
    	int hari = inputDate.getDate();
    	int bulan = inputDate.getMonth() + 1;
    	int tahun = inputDate.getYear() + 1900;
    	
    	int jam = inputDate.getHours();
    	int menit = inputDate.getMinutes();
    	int detik = inputDate.getSeconds();
    	
    	Map <Character, String> konversi = new HashMap<Character, String>();
    	
    	konversi.put('Y', Integer.toString(tahun));
    	konversi.put('y', Integer.toString(tahun).substring(1));
    	
    	konversi.put('F', this.longBulan[bulan]);
    	konversi.put('M', this.shortBulan[bulan]);
    	konversi.put('m', String.format("%02d", bulan));
    	konversi.put('n', Integer.toString(bulan));
    	    	
    	konversi.put('d', String.format("%02d", hari));
    	konversi.put('j', Integer.toString(hari));
    	
    	konversi.put('H', String.format("%02d", jam));
    	konversi.put('i', String.format("%02d", menit));
    	konversi.put('s', String.format("%02d", detik));
    	
    	StringBuilder retVal = new StringBuilder();
    	
    	for (char i : formatTanggal.toCharArray()) {
			if (konversi.get(i) != null) {
				retVal.append(konversi.get(i));
			} else {
				retVal.append(i);
			}
    	}
    	
    	return retVal.toString();
    }
}
