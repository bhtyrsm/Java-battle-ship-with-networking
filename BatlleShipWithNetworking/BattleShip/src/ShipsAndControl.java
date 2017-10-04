import java.util.Scanner;
import java.util.StringTokenizer;

public class ShipsAndControl{
	java.util.Random generator =new java.util.Random(System.currentTimeMillis()); //Random gemi �retilmesi i�in
    Scanner scan=new Scanner(System.in);  
  //gemilerin yerle�tirildi�i martis
  public  int[][] board ;
    //oyuncunun hamlelerinin tutuldu�u matris
  public  int[][] player ;
  
    //gemilerin adedini tutan dizi    
    //0 nolu eleman kullan�lm�yor   
   public int ship[]={0, 4, 3, 2, 1};
	//constructor
    public ShipsAndControl()
    {
    	board=new int [10][10];
    	player=new int [10][10];
    	createArea();
    }//end of Area
    
    //gemilerin olu�turulmas� ve yerle�tirilmesi
    public void createArea()
    {
    	 int i, j, row, col, size;
    	    int num, dir, shipSize, count, r, c, rl, cl, flag;
    	    int right, down, hit;

    	    row = 10;
    	    col = 10;
    	    size = 5;

    	    count = 0;
    	    shipSize = 1;
    	    
    	    
    	  while (shipSize <= 4) {
    		   //yon �retiliyor (0 sa�a, 1 a�a��ya)
    	         dir = generator.nextInt(2);
    	 
    	         //y�ne g�re sat�r s�tun s�n�rlar� ve 
    	         //yerle�tirilecek geminin s�n�rlar� belirleniyor
    	         if (dir == 0) {
    	            rl = row;
    	            cl = col - shipSize + 1;
    	            right = 2 + shipSize;
    	            down = 3;
    	         }
    	         else {
    	            rl = row - shipSize + 1;
    	            cl = col;
    	            right = 3;
    	            down = 2 + shipSize;
    	         }
    	 
    	         //geminin nereye yerle�tirilece�i �retiliyor
    	         r = generator.nextInt(rl);
    	         c = generator.nextInt(cl);
    	         
    	         
    	       //geminin yerle�tirilece�i yerin
    	         //bo� olup olmad���na bak�l�yor
    	         flag = 0;
    	         for (i = r-1; i < (r-1) + down; i = i + 1)
    	            for (j = c-1; j < (c-1) + right; j = j + 1)
    	               if (i >=0 && i < row && j >= 0 && j < col)
    	                  if (board[i][j] != 0)
    	                     flag = 1;
    	 
    	         //flag'in 0'a e�it olmas� geminin yerle�tirilece�i yerin
    	         //bo� oldu�unu g�steriyor.
    	         if (flag == 0) {
    	            //gemi yerleştiriliyor
    	            for (i = r; i < r + down - 2; i = i + 1)
    	               for (j = c; j < c + right - 2; j = j + 1)
    	                  board[i][j] = shipSize;
    	 
    	            //adet bir artt�r�l�yor
    	            count = count + 1;
    	 
    	          //gemiden istenen adet yerle�tirilmi�se
    	            //bir sonraki gemiye ge�iliyor
    	            if (ship[shipSize] == count) {
    	               shipSize = shipSize + 1;
    	               count = 0;
    	 
    	            }
    	         }
    	      }
    	
    }//end of createarea
    
    //gemileri ekrana g�sterme
    public void printArea()
    {
    	int i,j,row,col;
    	row=10;
    	col=10;
    	
    	// gemileri ekrana yazd�rma
    	 for (i = 0; i < row; i = i + 1) {
             for (j = 0; j < col; j = j + 1) 
                System.out.print(board[i][j] + " ");
             System.out.println();
          }
     
    }// end of printArea
    
    //oyuncu hamlelerini g�sterme
    public void printPlayer()

    {
    	for (int i = 0; i < 10; i = i + 1) {
            for (int j = 0; j < 10; j = j + 1) 
               System.out.print(player[i][j] + " ");
            System.out.println();
         }
    }

  //Gemilerin olu�turuldu�u matris her client taraf�nda ayn� olmal�.
  //Bu y�zden server taraf�nda gemiler bir defa olu�turulmal� ve t�m clientlere g�nderilmeli
  //Gemilerin oldu�u diziyi clientlere g�nderebilmek i�in  , diziyi string haline �eviriyorum.
    //Daha sonra olu�an stringi , server taraf�ndan clientlere g�nderiyorum.
    public String AreaArrayToString()
    {
    	String myarray="";
    	
    	for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				myarray+=board[i][j]+" ";
			} //end of first for
		myarray+="\n";
		}//end of secon for
    	
    	return myarray;
    }//end of AreaToString
    
   public String PlayerArrayToString()
   {
	   String myarray="";
   	
   	for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				myarray+=player[i][j]+" ";
			} //end of first for
		myarray+="\n";
		}//end of secon for
   	
   	return myarray;
	   
	   
	   
	   
   }
    
   //client taraf�ndan �rne�in string olarak ald���m 0-0 at���n�  0. sat�r ve 0. sutun olarak ay�r�p
   // at�� kontrol�n� sa�l�yorum
   public boolean ParseAndhit(String RowCol)
    {
    	//toplam 20 isabetli at�� yap�ld���nda oyun bitiyor
        int count = 0;
        int hit = 0;
       
        //at�� say�s� bir artt�r�l�yor
        count = count + 1;
        
  StringTokenizer st=new StringTokenizer(RowCol);
		
		String Row=st.nextToken("-");
		String Col=st.nextToken();
		
		int r=Integer.parseInt(Row);
		int c=Integer.parseInt(Col);
        
        
        //at���n isabetli olup olmad���na bak�l�yor 
        // at�� isabetli ise ,vurulan gemi yerine 9 yaz�l�yor.
        if (board[r][c] != 0)
        {
            hit = hit + 1;
            board[r][c]=9;
            return true ;
         }
        return false;
        
    }//end of hit;

} //end of area class