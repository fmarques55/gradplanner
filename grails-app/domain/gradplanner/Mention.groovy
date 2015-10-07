package gradplanner

class Mention {

	String description
	String abreviation
	int heaviness
	static belongsTo = [result: Result]

    static constraints = {
    }
}
