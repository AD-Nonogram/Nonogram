import java.util.ArrayList;

/*
 * verantwoordelijkheid: klasse Puzzle beschrijft het vlak van de daadwerkelijke puzzel.
 * dit is in het begin een lege 2d array, die keer bij keer wordt gevuld. Dit is een 2d array
 * van objecten zodat als er nog niks is ingevuld, de waarde 'null' is, is er wel wat ingevuld
 * dan is dit true dan wel false.
 */
public class Puzzle
{
    protected Object[][] puzzel;

    public Puzzle(int m, int n)
	{
        puzzel = new Object[m][n];
    }
    
    public int height()
	{
        return puzzel.length;
    }

    public int width() 
	{
        return puzzel[0].length;
    }

    public Object get(int m, int n)
	{
		if(m<height() && n < width() && m>=0 && n>=0)
	        return puzzel[m][n];

	    else
	    	return null;
    }

    public void setTrue(int m, int n)
	{
        puzzel[m][n] = true;
    }

    public void setFalse(int m, int n)
	{
        puzzel[m][n] = false;
    }

    public String toString(int a, int m)
	{
        String res = "";

        for(int j = 0; j<width(); j++)
		{
			Object o = puzzel[m][j];

            if(o == null)
              res = res + "null";


            else
			{
               boolean bo = ((Boolean) o).booleanValue();

               if(bo == true)
                  res = res + "true";

               else
			   		res = res + "false";
            }

             for(int k = 0; k < a; k++)
			 {
                res = res + " ";
             }
        }
        return res;
    }
}