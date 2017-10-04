import java.util.Scanner;
import java.util.StringTokenizer;

public class ShipsAndControl{
	java.util.Random generator =new java.util.Random(System.currentTimeMillis()); //Random gemi üretilmesi için
    Scanner scan=new Scanner(System.in);  
  //gemilerin yerleþtirildiði martis
  public  int[][] board ;
    //oyuncunun hamlelerinin tutulduðu matris
  public  int[][] player ;
  
    //gemilerin adedini tutan dizi    
    //0 nolu eleman kullanýlmýyor   
   public int ship[]={0, 4, 3, 2, 1};
	//constructor
    public ShipsAndControl()
    {
    	board=new int [10][10];
    	player=new int [10][10];
    	createArea();
    }//end of Area
    
    //gemilerin oluþturulmasý ve yerleþtirilmesi
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
    		   //yon üretiliyor (0 saða, 1 aþaðýya)
    	         dir = generator.nextInt(2);
    	 
    	         //yöne göre satýr sütun sýnýrlarý ve 
    	         //yerleþtirilecek geminin sýnýrlarý belirleniyor
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
    	 
    	         //geminin nereye yerleþtirileceði üretiliyor
    	         r = generator.nextInt(rl);
    	         c = generator.nextInt(cl);
    	         
    	         
    	       //geminin yerleþtirileceði yerin
    	         //boþ olup olmadýðýna bakýlýyor
    	         flag = 0;
    	         for (i = r-1; i < (r-1) + down; i = i + 1)
    	            for (j = c-1; j < (c-1) + right; j = j + 1)
    	               if (i >=0 && i < row && j >= 0 && j < col)
    	                  if (board[i][j] != 0)
    	                     flag = 1;
    	 
    	         //flag'in 0'a eþit olmasý geminin yerleþtirileceði yerin
    	         //boþ olduðunu gösteriyor.
    	         if (flag == 0) {
    	            //gemi yerleÅŸtiriliyor
    	            for (i = r; i < r + down - 2; i = i + 1)
    	               for (j = c; j < c + right - 2; j = j + 1)
    	                  board[i][j] = shipSize;
    	 
    	            //adet bir arttýrýlýyor
    	            count = count + 1;
    	 
    	          //gemiden istenen adet yerleþtirilmiþse
    	            //bir sonraki gemiye geçiliyor
    	            if (ship[shipSize] == count) {
    	               shipSize = shipSize + 1;
    	               count = 0;
    	 
    	            }
    	         }
    	      }
    	
    }//end of createarea
    
    //gemileri ekrana gösterme
    public void printArea()
    {
    	int i,j,row,col;
    	row=10;
    	col=10;
    	
    	// gemileri ekrana yazdýrma
    	 for (i = 0; i < row; i = i + 1) {
             for (j = 0; j < col; j = j + 1) 
                System.out.print(board[i][j] + " ");
             System.out.println();
          }
     
    }// end of printArea
    
    //oyuncu hamlelerini gösterme
    public void printPlayer()

    {
    	for (int i = 0; i < 10; i = i + 1) {
            for (int j = 0; j < 10; j = j + 1) 
               System.out.print(player[i][j] + " ");
            System.out.println();
         }
    }

  //Gemilerin oluþturulduðu matris her client tarafýnda ayný olmalý.
  //Bu yüzden server tarafýnda gemiler bir defa oluþturulmalý ve tüm clientlere gönderilmeli
  //Gemilerin olduðu diziyi clientlere gönderebilmek için  , diziyi string haline çeviriyorum.
    //Daha sonra oluþan stringi , server tarafýndan clientlere gönderiyorum.
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
    
   //client tarafýndan örneðin string olarak aldýðým 0-0 atýþýný  0. satýr ve 0. sutun olarak ayýrýp
   // atýþ kontrolünü saðlýyorum
   public boolean ParseAndhit(String RowCol)
    {
    	//toplam 20 isabetli atýþ yapýldýðýnda oyun bitiyor
        int count = 0;
        int hit = 0;
       
        //atýþ sayýsý bir arttýrýlýyor
        count = count + 1;
        
  StringTokenizer st=new StringTokenizer(RowCol);
		
		String Row=st.nextToken("-");
		String Col=st.nextToken();
		
		int r=Integer.parseInt(Row);
		int c=Integer.parseInt(Col);
        
        
        //atýþýn isabetli olup olmadýðýna bakýlýyor 
        // atýþ isabetli ise ,vurulan gemi yerine 9 yazýlýyor.
        if (board[r][c] != 0)
        {
            hit = hit + 1;
            board[r][c]=9;
            return true ;
         }
        return false;
        
    }//end of hit;

} //end of area class