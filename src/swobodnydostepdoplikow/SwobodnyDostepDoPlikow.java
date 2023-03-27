package swobodnydostepdoplikow;

import java.io.*;

public class SwobodnyDostepDoPlikow
{
    public static void main(String[] args) 
    {
        Towar[] towar = new Towar[3];
        
        towar[0] = new Towar();
        towar[1] = new Towar(29.99, "Videokurs java");
        towar[2] = new Towar(39.99, "Videokurs C++", 2018, 9, 5);
        
        try
        {
            /*DataOutputStream outS =new DataOutputStream(new FileOutputStream("baza.txt"));
            
            outS.writeDouble(2121213.313);
            
            outS.close();
            
            DataInputStream inS =new DataInputStream(new FileInputStream("baza.txt"));
            
            System.out.println(inS.readDouble());
            
            inS.close();*/
            
            RandomAccessFile RAF = new RandomAccessFile("baza.txt", "rw");
            
            Towar.zapiszDoPliku(towar, RAF);
            RAF.seek(0);
            
            Towar[] towary = Towar.odczytajZPliku(RAF);
            for(int i = 0; i < towary.length; i++)
            {
                System.out.println(towary[i].pobierzCene());
                System.out.println(towary[i].pobierzNazwe());
                System.out.println(towary[i].pobierzDate());
                System.out.println("-------------------------");
            }
            
            try
            {
                Towar a = new Towar();

                a.czytajRekord(RAF, 6);
                System.out.println(a);

                System.out.println("lala");
            }
            catch(BrakRekordu err)
            {
                System.out.println(err.getMessage());
            }
            RAF.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
