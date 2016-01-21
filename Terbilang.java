public class Terbilang
{
	private static String[] bilangan = {"", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sebelas"};

	private static String getKata(long bilangan)
	{
		return Terbilang.bilangan[(int) bilangan];
	}
	
	public static String getTerbilang(long x)
	{
        x = Math.abs(x);
		String retVal = "";
		
		if (x < 12) {
			retVal += " " + Terbilang.getKata(x);
		} else if (x < 20) {
			retVal += Terbilang.getTerbilang(x - 10) + " belas";
		} else if (x < 100) {
			retVal += Terbilang.getTerbilang(x / 10) + " puluh" + Terbilang.getTerbilang(x % 10);
		} else if (x < 200) {
			retVal += " seratus" + Terbilang.getTerbilang(x - 100);
		} else if (x < 1000) {
			retVal += Terbilang.getTerbilang(x / 100) + " ratus" + Terbilang.getTerbilang(x % 100);
		} else if (x < 2000) {
			retVal += " seribu" + Terbilang.getTerbilang(x - 1000);
		} else if (x < 1000000) {
			retVal += Terbilang.getTerbilang(x / 1000) + " ribu" + Terbilang.getTerbilang(x % 1000);
		} else if (x < 1000000000) {
			retVal += Terbilang.getTerbilang(x / 1000000) + " juta" + Terbilang.getTerbilang(x % 1000000);
		} else if (x < 1000000000000L) {
			retVal += Terbilang.getTerbilang(x / 1000000000) + " milyar" + Terbilang.getTerbilang(x % 1000000000);
		}
		
		return retVal;
	}
}
