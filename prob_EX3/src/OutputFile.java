/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class handles output file.
 * @author Moshe Hazoom & Slava Bronfman
 *
 */
public class OutputFile {

	private BufferedWriter br = null;
	
	/**
	 * Ctor
	 * @param filename
	 */
	public OutputFile(String filename) {
		try {
			this.br = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write new line to the output file
	 * @param line
	 */
	public void writeLine(String line) {
		try {
			this.br.write(line);
			this.br.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a new line
	 */
	public void writeNewLine() {
		try {
			this.br.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write to the output file
	 * @param line
	 */
	public void write(String line) {
		try {
			this.br.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close file
	 */
	public void close() {
		try {
			this.br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
