/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

public class Ex3 {

	public static void main(String[] args) {
		String strDevFileName = args[0];
		String strTestFileName = args[1];
		String strWord1 = args[2];
		String strWord2 = args[3];
		String strOutputFileName = args[4];
		
		Simulation sim = new Simulation(strDevFileName, strTestFileName, strWord1, strWord2, strOutputFileName);
		sim.simulate();
	}

}
