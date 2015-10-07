package gradplanner

class Period {

	int semester
	int year
	static belongsTo = [className: ClassName, result: Result]

    static constraints = {
    }
}
