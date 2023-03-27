package swobodnydostepdoplikow;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Towar
{
    public Towar()
    {
        this.cena = 0;
        this.nazwa = " ";
        this.dataWydania = new GregorianCalendar().getTime();
    }
    public Towar(double cena, String nazwa)
    {
        this();
        this.cena = cena;
        this.nazwa = nazwa;
    }
    public Towar(double cena, String nazwa, int rok, int m, int dz)
    {
        this(cena, nazwa);
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        this.dataWydania = kalendarz.getTime();
    }
    public double pobierzCene()
    {
        return this.cena;
    }
    public String pobierzNazwe()
    {
        return this.nazwa;
    }
    public Date pobierzDate()
    {
        return dataWydania;
    }
    public void ustawCene(double cena)
    {
        this.cena = cena;
    }
    public void ustawNazwe(String nazwa)
    {
        this.nazwa = nazwa;
    }
    public void ustawDate(int rok, int m, int dz)
    {
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        this.dataWydania = kalendarz.getTime();
    }
    public void ustawDate(Date data)
    {
        this.dataWydania = data;
    }
    @Override
    public String toString()
    {
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.dataWydania);
        return this.cena+" zł; nazwa: "+this.nazwa+" "+kalendarz.get(Calendar.YEAR)+" rok "+(kalendarz.get(Calendar.MONTH)+1)+" miesiąc "+kalendarz.get(Calendar.DAY_OF_MONTH)+" dzień ";
    }
    
    public static void zapiszDoPliku(Towar[] towar, DataOutput outS) throws IOException
    {
        for(int i = 0; i < towar.length; i++)
        {
            towar[i].zapiszDane(outS);
        }
    }
    
    public static Towar[] odczytajZPliku(RandomAccessFile RAF) throws IOException
    {
        int iloscRekordow = (int)(RAF.length()/Towar.dlugoscRekordu);
        Towar[] towar = new Towar[iloscRekordow];
        
        for(int i = 0; i < iloscRekordow; i++)
        {
            towar[i] = new Towar();
            towar[i].czytajDane(RAF);
            
        }
        return towar;
    }
    public void zapiszDane(DataOutput outS) throws IOException
    {
        outS.writeDouble(this.cena);
        
        StringBuffer stringB = new StringBuffer(Towar.dlugoscNazwy);
        stringB.append(this.nazwa);
        stringB.setLength(Towar.dlugoscNazwy);
        outS.writeChars(stringB.toString());
        
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.dataWydania);
        outS.writeInt(kalendarz.get(Calendar.YEAR));
        outS.writeInt((kalendarz.get(Calendar.MONTH)+1));
        outS.writeInt(kalendarz.get(Calendar.DAY_OF_MONTH));
    }
    public void czytajDane(DataInput inS) throws IOException
    {
        this.cena = inS.readDouble();
        
        StringBuffer tString = new StringBuffer(Towar.dlugoscNazwy);
        for(int i = 0; i < Towar.dlugoscNazwy; i++)
        {
            char tCh = inS.readChar();
            
            if(tCh != '\0')
                tString.append(tCh);
        }
        
        this.nazwa = tString.toString();
        
        int rok = inS.readInt();
        int m = inS.readInt();
        int dz = inS.readInt();
        
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1,dz);
        this.dataWydania = kalendarz.getTime();
    }
    public void czytajRekord(RandomAccessFile RAF, int n) throws IOException, BrakRekordu
    {
        if(n <= RAF.length() / Towar.dlugoscRekordu)
        {
            RAF.seek((n-1)*Towar.dlugoscRekordu);
            this.czytajDane(RAF);
        }
        else
            throw new BrakRekordu("Niestety nie ma takiego rekordu");
    }
    
    private static final int dlugoscNazwy = 30;
    private static final int dlugoscRekordu = (Character.SIZE * dlugoscNazwy + Double.SIZE + 3 * Integer.SIZE)/8;//80
    private double cena;//8 bajtow
    private String nazwa;//dlugosc nazwy * 2 = 60 bajtow
    private Date dataWydania;//4 + 4 + 4 bajty + 12
}
