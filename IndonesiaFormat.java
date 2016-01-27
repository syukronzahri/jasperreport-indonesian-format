package com.syukronzahri.jasper;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class IndonesianFormat extends JRDefaultScriptlet
{
    private String[] bilangan = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
    private String[] longBulan = {"", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] shortBulan = {"", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"};
    
    public static final int UPPERCASE = 1;
    public static final int LOWERCASE = 2;
    
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
    
    public String getTerbilang(long x)
    {
    	return this.getTerbilang(x, 0);
    }
    
    public String getTerbilang(long x, int charCase)
    {
    	String retVal;
    	
    	if (charCase == IndonesianFormat.LOWERCASE) {
    		retVal = this.getRecursiveTerbilang(x).toLowerCase();
    	} else if (charCase == IndonesianFormat.UPPERCASE) {
    		retVal = this.getRecursiveTerbilang(x).toUpperCase();
    	} else {
    		retVal = this.getRecursiveTerbilang(x);
    	}
    	
    	return retVal.trim().replaceAll(" {2,}", " ");
    }
    
    public String formatDecimal(String decFormat, double angka)
    {
    	String retVal;
    	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    	dfs.setGroupingSeparator('.');
    	dfs.setDecimalSeparator(',');
    	DecimalFormat x = new DecimalFormat(decFormat, dfs);
    	
		retVal = x.format(angka).trim();
    	
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
}
