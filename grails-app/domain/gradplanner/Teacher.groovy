package gradplanner

class Teacher {

	static hasOne = [person: Person]
	static belongsTo = [className: ClassName]

    static constraints = {
    }
}
