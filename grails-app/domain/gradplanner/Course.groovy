package gradplanner

class Course {

	String name
	int numberOfCredits
	int numberOfCreditsRequiredDisciplines
	int numberOfCreditsOptativeDisciplines
	int numberOfCreditsFreeModule

	static hasOne = [matrix: Matrix]

    static constraints = {
    }
}
