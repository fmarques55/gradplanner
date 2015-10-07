package gradplanner

class Result {

	static belongsTo= [student: Student, discipline: Discipline, transcriptOfRecords: TranscriptOfRecords]
	static hasOne = [mention: Mention, period: Period]


    static constraints = {
    }
}
