package com.syukronzahri.jasper;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class IndonesianFormat extends JRDefaultScriptlet
{
    private String[] bilangan = {"", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sebelas"};

    private String getKata(long bilangan)
    {
        return this.bilangan[(int) bilangan];
    }
    
    public String getTerbilang(long x)
    {
        x = Math.abs(x);
        String retVal = "";
        
        if (x < 12) {
            retVal += " " + this.getKata(x);
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
    
    public String formatDecimal(String decFormat, double angka)
    {
    	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    	dfs.setGroupingSeparator('.');
    	dfs.setDecimalSeparator(',');
    	DecimalFormat x = new DecimalFormat(decFormat, dfs);
    	
    	return x.format(angka).trim();
    }
}
