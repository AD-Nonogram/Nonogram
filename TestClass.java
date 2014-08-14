public class TestClass
{
    private static void test1()
	{
		Row ver0 = new Row();
        Row ver1 = new Row();
        Row ver2 = new Row();
        Row ver3 = new Row();
        Row ver4 = new Row();
        Row ver5 = new Row();
        Row ver6 = new Row();
        Row ver7 = new Row();
        Row ver8 = new Row();
        Row ver9 = new Row();
        
        ver0.add(4);
        ver0.add(2);

		ver1.add(1);
        ver1.add(3);

        ver2.add(3);
        ver2.add(1);

        ver3.add(1);
        ver3.add(6);

        ver4.add(8);

        ver5.add(2);
        ver5.add(5);

        ver6.add(1);
        ver6.add(1);
        ver6.add(1);

        ver7.add(2);

        ver8.add(5);

        ver9.add(4);

        Row hor0 = new Row();
        Row hor1 = new Row();
        Row hor2 = new Row();
        Row hor3 = new Row();
        Row hor4 = new Row();
        Row hor5 = new Row();
        Row hor6 = new Row();
        Row hor7 = new Row();
        Row hor8 = new Row();
        Row hor9 = new Row();

        hor0.add(1);
        hor0.add(3);

        hor1.add(1);
        hor1.add(4);

        hor2.add(1);
        hor2.add(3);
        hor2.add(2);

        hor3.add(3);
        hor3.add(3);
        hor3.add(2);

        hor4.add(3);
        hor4.add(1);

        hor5.add(5);

        hor6.add(3);

        hor7.add(1);
        hor7.add(3);

        hor8.add(2);
        hor8.add(3);

        hor9.add(6);

        Legend hor = new Legend(10);
        hor.set(0,hor0);
        hor.set(1,hor1);
        hor.set(2,hor2);
        hor.set(3,hor3);
        hor.set(4,hor4);
        hor.set(5,hor5);
        hor.set(6,hor6);
        hor.set(7,hor7);
        hor.set(8,hor8);
        hor.set(9,hor9);

        Legend ver = new Legend(10);
        ver.set(0,ver0);
        ver.set(1,ver1);
        ver.set(2,ver2);
        ver.set(3,ver3);
        ver.set(4,ver4);
        ver.set(5,ver5);
        ver.set(6,ver6);
        ver.set(7,ver7);
        ver.set(8,ver8);
        ver.set(9,ver9);

        Puzzle puz = new Puzzle(10,10);

        Nonogram2 nono = new Nonogram2(puz,hor,ver);

        nono.solve();
        System.out.println(nono.toString(4));


	}

	private static void test2()
	{
	    Row ver20 = new Row();
	    Row ver21 = new Row();
	    Row ver22 = new Row();
     	Row ver23 = new Row();
     	Row ver24 = new Row();

        ver20.add(2);

        ver21.add(2);
        ver21.add(2);

        ver22.add(2);

        ver23.add(2);

        ver24.add(3);

        Row hor20 = new Row();
        Row hor21 = new Row();
        Row hor22 = new Row();
        Row hor23 = new Row();
        Row hor24 = new Row();

        hor20.add(4);

        hor21.add(4);

        hor22.add(1);

        hor23.add(2);

        hor24.add(2);

        Legend hori = new Legend(5);
        hori.set(0,hor20);
        hori.set(1,hor21);
        hori.set(2,hor22);
        hori.set(3,hor23);
        hori.set(4,hor24);

        Legend vert = new Legend(5);
        vert.set(0,ver20);
        vert.set(1,ver21);
        vert.set(2,ver22);
        vert.set(3,ver23);
        vert.set(4,ver24);

        Puzzle puz2 = new Puzzle(5,5);

        Nonogram2 nono2 = new Nonogram2(puz2,hori,vert);

        nono2.solve();
        System.out.println(nono2.toString(4));
	}

    public static void main(String[] args)
	{
		test1();
		test2();
    }
}
