package gradplanner

class ClassName {

	String description
	static hasOne = [teacher: Teacher, period: Period]
	static hasMany = [classTimes: ClassTime]
	static belongsTo = [discipline: Discipline, feedback: Feedback]

    static constraints = {
    }
}
