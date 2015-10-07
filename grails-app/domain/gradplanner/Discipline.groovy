package gradplanner

class Discipline {

	String disciplineCode
	String name
	int quantityOfCredits
	static hasMany = [classNames: ClassName]
	static hasOne = [result: Result]
	static belongsTo = [matrix: Matrix]

    static constraints = {
    }
}
