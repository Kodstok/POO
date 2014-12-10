public class Couple {
	
	Coord a;
	Coord b;

	public Couple(Coord _a, Coord _b) {
		this.a = _a;
		this.b = _b;
	}
	
	public String toString() {
		return a.toString() + "=" + b.toString();
	}
}
