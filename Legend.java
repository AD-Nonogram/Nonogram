import java.util.ArrayList;

//this class makes the legend of the puzzle
public class Legend
{
	//legend exists from all rows
    Row[] legend;

	//legend with length m
    public Legend(int m)
	{
        legend = new Row[m];
    }

    public Row get(int i)
	{
        return legend[i];
    }

    public void set(int i, Row d)
	{
        legend[i] = d;
    }

    public int size()
	{
        return legend.length;
    }

    //this method gives the longest row in the legend. This is use in the toString method
    public int LongestRow()
	{
        int res = 0;

        for(int i = 0; i<size();i++)
		{
            if(legend[i].size() > res)
			   res = legend[i].size();
        }

        return res;
    }
}