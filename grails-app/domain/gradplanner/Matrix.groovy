package gradplanner

class Matrix {

	static belongsTo = [course: Course]
	static hasMany = [optativeDisciplines: Discipline, requiredDisciplines: Discipline]


    static constraints = {
    }
}
