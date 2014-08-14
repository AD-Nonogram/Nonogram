import java.util.ArrayList;

//this class makes the row with numbers on the side of the puzzle
public class Row
{
    private ArrayList<Integer> row;

    public Row()
	{
        row = new ArrayList<Integer>();
    }
    
    public void add(int i) 
	{
      	row.add(i);
    }

    public void add(int i, int j) 
	{
        row.add(i, j);
    }

	//gives the first block number of every row
    public int getFirst()
	{
		return row.get(0);
    }

	//gives the last block number of every row
    public int getLast()
	{
		return row.get(size()-1);
    }

    public int size()
	{
        return row.size();
    }

    public int get(int i)
	{
        if(i<row.size())
            return row.get(i);

        else
            throw new IndexOutOfBoundsException("test");
    }

    //sums the size of all blocks
    public int sum()
	{
        int sum = 0;

        for(int i = 0; i<row.size(); i++)
		{
            sum = sum + row.get(i);
        }

        return sum;
    }
}
