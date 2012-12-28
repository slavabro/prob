/* Name: Moshe Hazoom. ID: 201337904
 * Name: Slava Bronfman. ID:305917601 */

/**
 * Class represents a tuple of two objects
 * @author Moshe Hazoom and Slava Bronfman
 *
 * @param <F>
 * @param <S>
 */
public class Tuple<F,S> {

	private F first;
	private S second;
	
	public Tuple(F first, S second) {
		this.first = first;
		this.second = second;
	}
	
	public F getFirst() {
		return first;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}
	
	public int hashCode() {
		int hash = 1;
		
		hash = hash * 37 + this.first.hashCode();
		hash = hash * 37 + this.second.hashCode();
		
		return (hash);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Tuple<?,?>) {
			Tuple<?, ?> oTuple = (Tuple<?, ?>)o;
			return (this.first.equals(oTuple.getFirst()) && this.second.equals(oTuple.getSecond()));
		}
		return (false);
	}
}
