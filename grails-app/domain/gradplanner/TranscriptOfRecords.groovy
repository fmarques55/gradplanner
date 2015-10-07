package gradplanner

class TranscriptOfRecords {

	double ira
	static belongsTo = [student: Student]
	static hasMany = [results: Result]

    static constraints = {
    }
}
