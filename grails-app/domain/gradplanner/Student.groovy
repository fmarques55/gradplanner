package gradplanner

class Student {

	String enrollment
	String passwordMW
	static hasOne = [person: Person]

    static constraints = {
    }
}
