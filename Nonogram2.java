import java.util.ArrayList;

public class Nonogram2{

    private Puzzle puz;            //2d array where values true or false are insert
    private Legend upper;          //upper legend with the number and values of blocks
    private Legend left;           //left legend with the number and values of blocks

    public Nonogram2(Puzzle p, Legend u, Legend l)
	{
        puz = p;
        left=l;
        upper=u;
    }

    public Puzzle getPuzzle()
	{
        return puz;
    }

    public Legend getLeft()
	{
        return left;
    }

    public Legend getUpper()
	{
        return upper;
    }


	/*
    * Solve is calling the methods horizontal(), vertical() and Completed(). When these methods don't change any value then is the puzzle solved.
    * Or there are no other possibilities.
    */
    public void solve()
	{
        boolean change_hor = horizontal();
        boolean change_ver = vertical();
        boolean change_comp = Completed();

	    if(change_hor || change_ver || change_comp)
		{
			solve();
        }

        else
		{
            System.out.println("End script, no more changes");
        }
    }

    /*
     * This method checks for every row if it's possible to fill in the value true or false in 'puz'.
     * A compare array is made for a most left and most right solution. Also the place for a block will be checked
     */
    private boolean horizontal()
	{
        boolean change = false;

		//search through every row. left.size() is the number of rows
        for(int i = 0; i<left.size(); i++)
		{
        	int[][] compare = new int[2][puz.width()];
            //first make the most left solution so starting at 0
            int place = 0;

            for(int j = 0; j < left.get(i).size(); j++)
			{
				// lengte from block j in row i
                int length = left.get(i).get(j);

				//checks if it is possible to place the block with length on the place
                place = PlaceHorizontal(i,length,place,0,-1);

				//If j is the last block PlaceHorizontalEnd must be checked
                if(j == left.get(i).size()-1)
				{
                    place = PlaceHorizontalEnd(i,length,place,0);
                }

				// if place is not -1, the block can be placed
                if(place != -1)
				{
                	//j+1 because you want to place the rang of a block
                    compare = CompareArray(compare,0,place,length,j+1);
                    // new place is place + length block + white block between it
                    place = place + left.get(i).get(j) + 1;
                }

				//if place is -1 following has to be printed
                else
				{
                    System.err.println("Block with length " + length +" in row " + i +" can not be placed in compareArray in direction 0");
                }

            }

            //Making the most right array, so starting with the last block
            place = puz.width()-1;

            for(int k = left.get(i).size()-1; k>=0; k--)
			{
             	int length = left.get(i).get(k);

                place = PlaceHorizontal(i,length,place,1,-1);

                if(k == 0)
				   place = PlaceHorizontalEnd(i,length,place,1);


                if(place != -1)
				{
                	compare = CompareArray(compare,1,place,length,k+1);
                    place = place - left.get(i).get(k) - 1;
                }

                else
				   	System.err.println("Block with length " + length +" in row " + i + " can not be placed in compareArray in direction 1");
            }

			// if something has changed, the change boolean needs to be true
            if(ConcludeHorizontal(compare,i))
                change = true;
        }
        return change;
    }

    //same idea as with horizontal
    private boolean vertical()
	{
        boolean change = false;

        for(int i = 0; i<upper.size(); i++)
		{
            int[][] compare = new int[2][puz.height()];
            int place = 0;

            for(int j = 0; j < upper.get(i).size(); j++)
			{
                int length = upper.get(i).get(j);

                place = PlaceVertical(i,length,place,0,-1);

                if(j == upper.get(i).size()-1)
                    place = PlaceVerticalEnd(i,length,place,0);

                if(place != -1)
				{
                    compare = CompareArray(compare,0,place,length,j+1);
                    place = place + upper.get(i).get(j) + 1;
                }

                else
                    System.err.println("Block with length " + length +" in column " + i + " can not be placed in compareArray in direction 1");
            }

            place = puz.height()-1;

            for(int k = upper.get(i).size()-1; k>=0; k--)
			{
                int length = upper.get(i).get(k);

                place = PlaceVertical(i,length,place,1,-1);

                if(k == 0)
                    place = PlaceVerticalEnd(i,length,place,1);

                if(place != -1)
                {
                    compare = CompareArray(compare,1,place,length,k+1);
                    place = place - upper.get(i).get(k) - 1;
                }

                else
                    System.err.println("Block with length " + length +" in column " + i + " can not be placed in compareArray in direction 1");
            }

            if(ConcludeVertical(compare,i))
                change = true;
        }

        return change;

    }

	/*
    * this method checks if the block with length fits on the place depending on the direction.
    * if this is not possible, another place will be searched
    */
    private int PlaceHorizontal(int row, int length, int place, int direction, int fault)
	{
		int res =place;

        if(direction == 0)
		{
         ////checks for all places if its possible
            for(int i = place; i<place+length; i++)
			{
            	//i can't be bigger then the width of the puzzle
                if(i<puz.width())
				{
					fault=0;
					//if a block has already the value false, the block need to be moved
                    if(puz.get(row, i) != null)
					{
                        Object o = puz.get(row,i);
                        boolean bo = ((Boolean) o).booleanValue();

                        if(bo==false)
                        {
							fault=-1;
                            res = PlaceHorizontal(row,length,i+1,direction,-1);
                        }
                    }
                }
            }

            /*
             * If the space after the block is already true, the block needs to move one space
             */
            if(place+length<puz.width())
			{
				fault=0;
                if(puz.get(row, place+length) != null)
                {
                    Object o = puz.get(row,place+length);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                    {
						fault=-1;
                        res = PlaceHorizontal(row,length,place+1,direction,-1);
                    }
	            }
            }

            if(fault == 0)
                res = place;

            if(fault == -1)
            	res= -1;
        }

        else if(direction == 1)
		{
            for(int i = place; i>place-length; i--)
			{
				if(i>0)
				{
					fault=0;

	                if(puz.get(row, i) != null)
					{
                    	Object o = puz.get(row,i);
	                    boolean bo = ((Boolean) o).booleanValue();

    	                if(bo==false)
        	            {
							fault=-1;
                	        res = PlaceHorizontal(row,length,i-1,direction,-1);
                    	}
	                }
    	        }
   			}

            if(place-length>=0)
			{
				fault=0;
                if(puz.get(row, place-length) != null)
				{
                    Object o = puz.get(row,place-length);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                    {
                        res = PlaceHorizontal(row,length,place-1,direction,-1);
                    }
                }
            }

            if(fault == 0)
                res = place;

            if(fault == -1)
            	res= -1;
        }
        return res;
    }

    //same as with PlaceHorizontal
    private int PlaceVertical(int col, int length, int place, int direction, int fault)
	{
        int res = place;

        if(direction == 0)
		{
            for(int i = place; i<place+length; i++)
			{
                if(i<puz.height())
                {
					fault=0;
                    if(puz.get(i, col) != null)
					{
    	                Object o = puz.get(i,col);
        	            boolean bo = ((Boolean) o).booleanValue();

            	        if(bo==false)
            	        {
							fault=-1;
                	        res = PlaceVertical(col,length,i+1,direction,fault);
                	    }
                	}
	            }
	  		}

            if(place+length<puz.height())
			{
				fault=0;
                if(puz.get(place+length,col) != null)
				{
                    Object o = puz.get(place+length,col);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                    {
						fault=-1;
                        res = PlaceVertical(col,length,place+1,direction,fault);
                    }
                }
            }

            if(fault == 0)
                res = place;

            if(fault == -1)
            	res= -1;
  	}

        else if(direction == 1)
		{
            for(int i = place; i>place-length; i--)
			{
				if(i>0)
				{
					fault=0;
	                if(puz.get(i,col) != null)
					{
                    	Object o = puz.get(i,col);
	                    boolean bo = ((Boolean) o).booleanValue();

    	                if(bo==false)
    	                {
							fault=-1;
        	                res = PlaceVertical(col,length,i-1,direction,fault);
        	            }
     				}
                }
            }

            if(place-length>=0)
			{
				fault=0;
                if(puz.get(place-length,col) != null)
				{
                    Object o = puz.get(place-length,col);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                    {
						fault=-1;
                        res = PlaceVertical(col,length,place-1,direction,fault);
                    }
                }
            }

            if(fault == 0)
                res = place;

            if(fault == -1)
            	res= -1;
        }
        return res;
    }

    /*
     * If there are cells marked 'true' after or before the current cell, there's checked which of these cells is the most far away to the right or left (depends on the direction).
     * The most left or right block will be placed to this cell.
     */
    private int PlaceHorizontalEnd(int row,int length,int place,int direction)
	{
        int place_res = place;

        if(direction == 0)
		{
        	//Checks all the cells after the last block if there's a value true
            for(int i = place+length; i<puz.width(); i++)
			{
                if(puz.get(row, i) != null)
				{
                    Object o = puz.get(row,i);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                    	//If there's another value marked true, move the block so that this cell is the last marked cell of the block
						place_res = i - length + 1;
                }
            }
        }

        else if(direction == 1)
		{
            for(int i = place-length; i>=0; i--)
			{
                if(puz.get(row, i) != null)
				{
                    Object o = puz.get(row,i);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                        place_res = i + length - 1;
                }
            }
        }
        return place_res;
    }


    /*
     * Same function as checkPlaceHorEnd, but checks the columns
     */
    private int PlaceVerticalEnd(int col,int length,int place,int direction)
	{
        int place_res = place;

        if(direction == 0)
		{
            for(int i = place+length; i<puz.height(); i++)
			{
                if(puz.get(i,col) != null)
				{
                    Object o = puz.get(i,col);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                        place_res = i - length + 1;
                }
            }
        }

        else if(direction == 1)
		{
            for(int i = place-length; i>=0; i--)
			{
                if(puz.get(i,col) != null)
				{
                    Object o = puz.get(i,col);
                    boolean bo = ((Boolean) o).booleanValue();

                    if(bo == true)
                        place_res = i + length - 1;
                }
            }
        }
        return place_res;
    }

    /*
     * Fills the rows from compareArray to create the blocks. Every block gets the number
     * corresponding with the place of the block in the legend. i.e. the first block gets
     * value 1, the second block gets value 2, etc. The cells adjacent to each block get value
     * -1, to mark a potential uncolored cell.
     */

    private int[][] CompareArray(int[][] compareArray, int direction, int place, int length, int rang)
	{
        if(direction == 0)
		{
            for(int i = place; i<place+length; i++)
			{
                compareArray[0][i] = rang;
            }

            if(place+length<compareArray[0].length)
                compareArray[0][place+length] = -1;

            if(place>0)
                compareArray[0][place-1] = -1;
        }

        else if(direction == 1)
		{
            for(int i = place; i>place-length; i--)
			{
                compareArray[1][i] = rang;
            }

            if(place-length>=0)
                compareArray[1][place-length] = -1;

            if(place<compareArray[0].length-1)
                compareArray[1][place+1] = -1;
        }

		//fill the empty fields with -1
        for(int i = 0; i<compareArray[0].length; i++)
		{
            if(compareArray[0][i] == 0)
                compareArray[0][i] = -1;

            if(compareArray[1][i] == 0)
                compareArray[1][i] = -1;
        }
        return compareArray;
    }

    /*
     * Compares the rows from compareArray and checks if there are cells which can be marked with
     * true or false
     */
    private boolean ConcludeHorizontal(int[][] compareArray, int row)
	{
		// res keeps track of made changes
        boolean res = false;

        for(int i = 0; i<compareArray[0].length; i++)
		{
			boolean res_temp = false;

			//if spaces has same positive rang, the space in puzzle will be set as true
            if(compareArray[0][i] == compareArray[1][i] && compareArray[0][i] > 0)
                res_temp = setTrue(row,i);

            if(compareArray[0][i] == -1 && compareArray[0][i] == compareArray[1][i])
			{
                if(i==0)
				{
                    if(compareArray[0][1] == compareArray[1][1])
                        res_temp = setFalse(row,0);
                }

                //If the last square has value -1 and equals the before-last square, the last square gets value false
                else if(i==compareArray[0].length-1)
				{
                    if(compareArray[0][compareArray[0].length-1] == compareArray[1][compareArray[0].length-1])
                        res_temp = setFalse(row,compareArray[0].length-1);
                }

                //If the amount of colored cells in a row is greater than half the length, the different arrays will intersect
                //If there's a cell which touches two cells with the same value, this cell has to be false
				else if(left.get(row).sum() > puz.width() / 2 && (compareArray[0][i-1] == compareArray[1][i-1] || compareArray[0][i+1] == compareArray[1][i+1]))
                    res_temp = setFalse(row,i);

                //If a row only has 1 block, checkplace has already colored this square in both arrays
                //Every value -1 will be marked false
                if(left.get(row).size() == 1 && rowTrue(row))
                    res_temp = setFalse(row,i);
            }

			//if the temporary res is true, nothing has changed
            if(res_temp == true)
                res = true;
        }
        return res;
    }

    /*
     * Works the same as compareArrayHor, but checks the columns
     */
    private boolean ConcludeVertical(int[][] compareArray, int col)
	{
        boolean res = false;

        for(int i = 0; i<compareArray[0].length; i++)
		{
            boolean res_temp = false;

            if(compareArray[0][i] == compareArray[1][i] && compareArray[0][i] > 0)
                res_temp = setTrue(i,col);

            if(compareArray[0][i] == -1 && compareArray[0][i] == compareArray[1][i])
			{

                if(i==0)
				{
                    if(compareArray[0][1] == compareArray[1][1])
                        res_temp = setFalse(0,col);
                }

                else if(i==compareArray[0].length-1)
				{
                    if(compareArray[0][compareArray[0].length-1] == compareArray[1][compareArray[0].length-1])
					   res_temp = setFalse(compareArray[0].length-1,col);
                }

                else if(upper.get(col).sum() > puz.height() / 2 && (compareArray[0][i-1] == compareArray[1][i-1] || compareArray[0][i+1] == compareArray[1][i+1]))
                    res_temp = setFalse(i,col);

                if(upper.get(col).size() == 1 && columnTrue(col))
                    res_temp = setFalse(i,col);
            }

            if(res_temp == true)
                res = true;
        }
        return res;
    }

    /*
     * Helpmethod for processCompareArrayVer, checks if column 'col' has values true.
     */
    private boolean columnTrue(int col)
	{
        boolean res = false;

        for(int i = 0; i<puz.height();i++)
		{
            if(puz.get(i,col) != null)
			{
                Object o = puz.get(i,col);
                boolean bo = ((Boolean) o).booleanValue();

                if(bo == true)
                    res = true;
            }
        }
        return res;
    }


    /*
     * Helpmethod for processCompareArrayHor, same as columnContainsTrue
     */
    private boolean rowTrue(int row)
	{
        boolean res = false;

        for(int i = 0; i<puz.width();i++)
		{
            if(puz.get(row,i) != null)
			{
                Object o = puz.get(row,i);
                boolean bo = ((Boolean) o).booleanValue();

                if(bo == true)
                    res = true;
            }
        }
        return res;
    }


    /*
     * Checks if there are row or columns which already have got all blocks completed.
     * If so, the rest of the cells get value false
     */
    private boolean Completed()
	{
        boolean change = false;

        //First the rows are checked
        for(int i = 0; i<puz.height(); i++)
		{
            int j = 0;
            int no = 0;
            int cum = 0;

            //Checks all the values of the row, no has to be smaller than the amount of blocks in row i
            while(j<puz.width() && no<left.get(i).size())
			{
				//The value of block no in row i
                int number = left.get(i).get(no);

                //The value of cell (i,j)
                Object ob = puz.get(i,j);

                if(ob == null)
				{
                    cum = 0;
                    j++;
                }

                else
				{
                    boolean bl = ((Boolean) ob).booleanValue();

                    //If true, cum++ untill cum == no, afterwards, cum=0 and we continue to the next block
                    if(bl == true)
					{
                        cum++;

                        if(number == cum)
						{
                            no++;
                            cum = 0;
                        }
                        j++;
                    }

                    else
					{
						//???? zou toch stap ervoor al goed moeten zijn gegaan ????
                        if(cum == number)
						{
                            j++;
                            no++;
                            cum = 0;
                        }

                        else
						{
                            cum = 0;
                            j++;
                        }
                    }
                }
            }

            if(no == left.get(i).size() && (cum == left.get(i).get(left.get(i).size()-1) || cum == 0))
			{
                for(int k = 0; k<puz.width(); k++)
				{
                    if(puz.get(i, k) == null)
					{
                        if(setFalse(i,k))
                            change = true;
                    }
                }
            }
        }

        //columns are checked
        for(int i = 0; i<puz.width(); i++)
		{
            int j = 0;
            int no = 0;
            int cum = 0;

            while(j<puz.height() && no<upper.get(i).size())
			{
                int number = upper.get(i).get(no);

                Object ob = puz.get(j,i);

                if(ob == null)
				{
                    cum = 0;
                    j++;
                }

                else
				{
                    boolean bl = ((Boolean) ob).booleanValue();

                    if(bl == true)
					{
                        cum++;

                        if(cum == number)
						{
                            no++;
                            cum = 0;
                        }

                        j++;
                    }

                    else
					{
                        if(cum == number)
						{
                            j++;
                            no++;
                            cum = 0;
                        }

                        else
						{
                            cum = 0;
                            j++;
                        }
                    }
                }
            }

            if(no == upper.get(i).size() && (cum == upper.get(i).get(upper.get(i).size()-1) || cum == 0))
			{
                for(int k = 0; k<puz.height(); k++)
				{
                    if(puz.get(k,i) == null)
					{
                        if(setFalse(k,i))
                            change = true;
                    }
                }
            }
        }
        return change;
    }


    /*
     * Gives cell (i,j) value true, first checks if this has been done before
     */
    private boolean setTrue(int i, int j)
	{
        boolean res = false;

        Object o = puz.get(i, j);

        if(o == null)
		{
            puz.setTrue(i,j);
            res = true;
        }
        return res;
    }

    /*
     * Gives cell (i,j) value false, first checks if this has been done before
     */
    private boolean setFalse(int i, int j)
	{
        boolean res = false;

        Object o = puz.get(i, j);
        if(o == null)
		{
           puz.setFalse(i, j);
           res = true;
        }
        return res;
    }


    /*
     * Gives a consolidated view of the puzzle, n is the amount of empty space between the columns
     */
    public String toString(int n)
	{
		String res = "";
        int left_index_size = left.LongestRow()*3;

        for(int i = 0; i<left.size();i++)
		{
            res += puz.toString(n,i) + "\n";
        }

        return res;
    }
}